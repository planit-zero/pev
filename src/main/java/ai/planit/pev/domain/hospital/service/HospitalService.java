package ai.planit.pev.domain.hospital.service;

import ai.planit.pev.domain.hospital.dto.Department;

import java.util.List;

public interface HospitalService {
    List<Department> getDepartmentList();
}
