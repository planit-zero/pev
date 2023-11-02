package ai.planit.pev.domain.ods.hospital.service;

import ai.planit.pev.domain.ods.hospital.dto.Department;

import java.util.List;

public interface HospitalService {
    List<Department> getDepartmentList();
}
