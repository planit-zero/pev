package ai.planit.pev.domain.patient.repository;

import ai.planit.pev.domain.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    Patient findPatientByPtNo(String ptNo);
}
