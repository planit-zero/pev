package ai.planit.pev.domain.ods.record.dao;

import ai.planit.pev.domain.ods.record.dto.Record;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordListDAOImpl implements RecordListDAO {

    private final SqlSessionTemplate sqlSessionTemplate;

    /** {@inheritDoc} */
    public List<Record.Response> getMedicalRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getMedicalRecordList", request);
    }

    /** {@inheritDoc} */
    public List<Record.Response> getSurgeryRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getSurgeryRecordList", request);
    }

    /** {@inheritDoc} */
    public List<Record.Response> getDischargeRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getDischargeRecordList", request);
    }

    /** {@inheritDoc} */
    public List<Record.Response> getRequestRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getRequestRecordList", request);
    }

    /** {@inheritDoc} */
    public List<Record.Response> getAnesthesiaRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getAnesthesiaRecordList", request);
    }

    /** {@inheritDoc} */
    public List<Record.Response> getOrderRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getOrderRecordList", request);
    }

    /** {@inheritDoc} */
    public List<Record.Response> getExamPictureRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getExamPictureRecordList", request);
    }

    /** {@inheritDoc} */
    public List<Record.Response> getExamPathologyRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getExamPathologyRecordList", request);
    }

    /** {@inheritDoc} */
    public List<Record.Response> getExamSpecimenRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getExamSpecimenRecordList", request);
    }

    /** {@inheritDoc} */
    public List<Record.Response> getExamFunctionRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getExamFunctionRecordList", request);
    }

    /** {@inheritDoc} */
    public List<Record.Response> getScanRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getScanRecordList", request);
    }

    @Override
    public List<Record.Response> getNrObservationRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getNrObservationRecordList", request);
    }

    @Override
    public List<Record.Response> getNrInpatientRecordList(Record.Request request) {
        return sqlSessionTemplate.selectList("getNrInpatientRecordList", request);
    }
}
