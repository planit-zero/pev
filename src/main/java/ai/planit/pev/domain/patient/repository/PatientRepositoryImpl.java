package ai.planit.pev.domain.patient.repository;

import ai.planit.pev.domain.patient.entity.Patient;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static ai.planit.pev.domain.patient.entity.QPatient.patient;

@Repository
public class PatientRepositoryImpl implements PatientRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PatientRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Patient findPatientByPtNo(String ptNo) {
        return jpaQueryFactory.selectFrom(patient)
                .where(patient.ptNo.eq(ptNo))
                .fetchOne();
    }
}
