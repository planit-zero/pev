package ai.planit.pev.domain.ods.patient.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.core.webclient.PevWebClient;
import ai.planit.pev.core.webclient.PevWebClientUtil;
import ai.planit.pev.domain.ods.patient.dao.PatientDAO;
import ai.planit.pev.domain.ods.patient.dto.IdentifiedPatient;
import ai.planit.pev.domain.ods.patient.dto.Patient;
import ai.planit.pev.domain.ods.patient.dto.PatientByIrb;
import ai.planit.pev.domain.ods.patient.dto.RidByGid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@org.springframework.context.annotation.Profile("prod")
public class PatientServiceImpl implements PatientService {
    private final PatientDAO patientDAO;

    private final static String RID_URL = "http://172.26.33.23:28092";
    private final static String RID_DECRYPT_API_URI = "/api/ann/identification";
    private final static String RID_IRB_RID_API_URI = "/api/ann/irb-rid";
    private final static String RID_GID_RID_API_URI = "/api/ann/rex";


    public Patient getPatient(HttpSession session, IdentifiedPatient.Request request) {
        String pid = convertRidToPid(request);

        Patient patient = patientDAO.getPatient(pid);
        if (patient == null) throw new BaseException(ErrorType.PATIENT_NOT_FOUND);

        session.setAttribute("pev-pid", pid);
        session.setAttribute("pev-irb", request.getIrb());
        session.setAttribute("pev-rid", request.getRidList().get(0));

        return patient;
    }

    private String convertRidToPid(IdentifiedPatient.Request request) {
        WebClient webClient = PevWebClient.getWebClient(RID_URL, ErrorType.RID_CONNECTION_TIMEOUT);

        String[] identifiedPatient = webClient.post()
                .uri(RID_DECRYPT_API_URI)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> PevWebClientUtil.throwServerError(response, ErrorType.CONVERT_RID_TO_PID_FAILED))
                .bodyToMono(String[].class)
                .block();

        if (identifiedPatient == null || identifiedPatient.length < 1) {
            throw new BaseException(ErrorType.CONVERT_RID_TO_PID_FAILED);
        }

        return identifiedPatient[0];
    }

    public RidByGid.Response getRidByGid(RidByGid.Request request) {
        return convertRidByGid(request);
    }

    private RidByGid.Response convertRidByGid(RidByGid.Request request) {
        WebClient webClient = PevWebClient.getWebClient(RID_URL, ErrorType.RID_CONNECTION_TIMEOUT);

        return webClient.post()
                .uri(RID_GID_RID_API_URI)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> PevWebClientUtil.throwServerError(response, ErrorType.CONVERT_GID_TO_RID_FAILED))
                .bodyToMono(RidByGid.Response.class)
                .block();
    }

    public List<PatientByIrb.Response> getPatientList(PatientByIrb.Request request) {
        List<PatientByIrb.Response> patientList = getPatientListByIrb(request);

        patientList = patientList.stream()
                .sorted(Comparator.comparing(PatientByIrb.Response::getId))
                .collect(Collectors.toList());

        return patientList;
    }

    private List<PatientByIrb.Response> getPatientListByIrb(PatientByIrb.Request request) {
        WebClient webClient = PevWebClient.getWebClient(RID_URL, ErrorType.RID_CONNECTION_TIMEOUT);

        return webClient.post()
                .uri(RID_IRB_RID_API_URI)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> PevWebClientUtil.throwServerError(response, ErrorType.CONVERT_RID_TO_PID_FAILED))
                .bodyToMono(new ParameterizedTypeReference<List<PatientByIrb.Response>>(){})
                .block();
    }
}
