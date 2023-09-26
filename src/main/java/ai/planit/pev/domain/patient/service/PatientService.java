package ai.planit.pev.domain.patient.service;

import ai.planit.pev.domain.patient.dto.IdentifiedPatient;
import ai.planit.pev.domain.patient.dto.Patient;

import javax.servlet.http.HttpSession;

public interface PatientService {
    Patient getPatient(HttpSession session, IdentifiedPatient.Request request);
}
