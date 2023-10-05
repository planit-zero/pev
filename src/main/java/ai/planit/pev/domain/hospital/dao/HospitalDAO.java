package ai.planit.pev.domain.hospital.dao;

import ai.planit.pev.domain.hospital.dto.Department;

import java.util.List;

public interface HospitalDAO {
    List<Department> getDepartmentList();
}
