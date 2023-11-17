package ai.planit.pev.domain.ods.patient.service;

import ai.planit.pev.domain.ods.patient.dto.IdentifiedPatient;
import ai.planit.pev.domain.ods.patient.dto.Patient;
import ai.planit.pev.domain.ods.patient.dto.PatientByIrb;
import ai.planit.pev.domain.ods.patient.dto.RidByGid;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PatientService {
    Patient getPatient(HttpSession session, IdentifiedPatient.Request request);

    RidByGid.Response getRidByGid(RidByGid.Request request);

    List<PatientByIrb.Response> getPatientList(PatientByIrb.Request request);
}
