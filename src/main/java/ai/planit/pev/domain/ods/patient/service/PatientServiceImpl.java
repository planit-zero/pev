package ai.planit.pev.domain.ods.patient.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.core.webclient.PevWebClient;
import ai.planit.pev.domain.ods.patient.dao.PatientDAO;
import ai.planit.pev.domain.ods.patient.dto.IdentifiedPatient;
import ai.planit.pev.domain.ods.patient.dto.Patient;
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
    private final static String RID_DECRYPT_API_URI = "/api/ann/identification";


    public Patient getPatient(HttpSession session, IdentifiedPatient.Request request) {
//        if (PevStringUtil.isStringEmpty(request.getGid())) {
//            throw new BaseException(ErrorType.GID_NOT_FOUND);
//        }

        String pid = convertRidToPid(request);
        session.setAttribute("pev-pid", pid);

        Patient patient = patientDAO.getPatient(pid);
        if (patient == null) throw new BaseException(ErrorType.PATIENT_NOT_FOUND);

        return patient;
    }

    private String convertRidToPid(IdentifiedPatient.Request request) {
        WebClient webClient = PevWebClient.getWebClient(RID_URL, ErrorType.RID_CONNECTION_TIMEOUT);

        String[] identifiedPatient = webClient.post()
                .uri(RID_DECRYPT_API_URI)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, this::throwRidServerError)
                .onStatus(HttpStatus::is4xxClientError, this::throwRidServerError)
                .bodyToMono(String[].class)
                .block();

        if (identifiedPatient == null || identifiedPatient.length < 1) {
            throw new BaseException(ErrorType.CONVERT_RID_TO_PID_FAILED);
        }

        return identifiedPatient[0];
    }

    private Mono<? extends Throwable> throwRidServerError(ClientResponse response) {
        return response.createException()
                .flatMap(error -> {
                    String body = error.getResponseBodyAsString(StandardCharsets.UTF_8);
                    Map<String, String> errorData = new Gson().fromJson(body, HashMap.class);
                    System.out.println(errorData.get("message"));
                    return Mono.error(new BaseException(ErrorType.CONVERT_RID_TO_PID_FAILED, ErrorType.CONVERT_RID_TO_PID_FAILED.getMessage(), errorData.get("message")));
                });
    }
}
