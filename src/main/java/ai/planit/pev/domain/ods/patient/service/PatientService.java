package ai.planit.pev.domain.ods.patient.service;

import ai.planit.pev.domain.ods.patient.dto.IdentifiedPatient;
import ai.planit.pev.domain.ods.patient.dto.Patient;

import javax.servlet.http.HttpSession;

public interface PatientService {
    Patient getPatient(HttpSession session, IdentifiedPatient.Request request);
}
