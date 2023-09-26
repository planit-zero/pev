package ai.planit.pev.domain.patient.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.core.webclient.PevWebClient;
import ai.planit.pev.domain.patient.dao.PatientDAO;
import ai.planit.pev.domain.patient.dto.IdentifiedPatient;
import ai.planit.pev.domain.patient.dto.Patient;
import ai.planit.pev.utility.PevStringUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientDAO patientDAO;

    private final static String RID_URL = "http://172.26.33.23:28092";
    private final static String RID_DECRYPT_API_URI = "/api/psd/decrypt";


    public Patient getPatient(HttpSession session, IdentifiedPatient.Request request) {
        if (PevStringUtil.isStringEmpty(request.getGid())) {
            throw new BaseException(ErrorType.GID_NOT_FOUND);
        }

        String pid = convertGidToPid(request.getGid());
        session.setAttribute("pev-pid", pid);

        Patient patient = patientDAO.getPatient(pid);
        if (patient == null) throw new BaseException(ErrorType.PATIENT_NOT_FOUND);

        return patient;
    }

    private String convertGidToPid(String gid) {
        WebClient webClient = PevWebClient.getWebClient(RID_URL, ErrorType.RID_CONNECTION_TIMEOUT);

        IdentifiedPatient.Request identifiedPatientRequest = new IdentifiedPatient.Request();
        identifiedPatientRequest.setGid(gid);
        identifiedPatientRequest.setIrbNo("PEV-CONVERT-GID-TO-PID");
        identifiedPatientRequest.setStfNo("CHUCK");
        identifiedPatientRequest.setStfNm("김창호");
        identifiedPatientRequest.setDeptCd("PHC");
        identifiedPatientRequest.setDeptNm("플랜잇");

        IdentifiedPatient.Response identifiedPatient = webClient.post()
                .uri(RID_DECRYPT_API_URI)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(identifiedPatientRequest))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, this::throwRidServerError)
                .onStatus(HttpStatus::is4xxClientError, this::throwRidServerError)
                .bodyToMono(IdentifiedPatient.Response.class)
                .block();

        if (identifiedPatient == null || identifiedPatient.getPtNo() == null) {
            throw new BaseException(ErrorType.CONVERT_GID_TO_PID_FAILED);
        }

        return identifiedPatient.getPtNo();
    }

    private Mono<? extends Throwable> throwRidServerError(ClientResponse response) {
        return response.createException()
                .flatMap(error -> {
                    String body = error.getResponseBodyAsString(StandardCharsets.UTF_8);
                    Map<String, String> errorData = new Gson().fromJson(body, HashMap.class);
                    System.out.println(errorData.get("message"));
                    return Mono.error(new BaseException(ErrorType.CONVERT_GID_TO_PID_FAILED, ErrorType.CONVERT_GID_TO_PID_FAILED.getMessage(), errorData.get("message")));
                });
    }
}
