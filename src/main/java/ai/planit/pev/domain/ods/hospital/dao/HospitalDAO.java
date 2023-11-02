package ai.planit.pev.domain.ods.hospital.dao;

import ai.planit.pev.domain.ods.hospital.dto.Department;

import java.util.List;

public interface HospitalDAO {
    List<Department> getDepartmentList();
}
