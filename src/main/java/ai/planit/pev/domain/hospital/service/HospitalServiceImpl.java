package ai.planit.pev.domain.hospital.service;

import ai.planit.pev.domain.hospital.dao.HospitalDAO;
import ai.planit.pev.domain.hospital.dto.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalDAO hospitalDAO;

    @Override
    public List<Department> getDepartmentList() {
        return hospitalDAO.getDepartmentList();
    }
}
