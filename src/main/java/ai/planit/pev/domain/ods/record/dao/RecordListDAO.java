package ai.planit.pev.domain.ods.record.dao;

import ai.planit.pev.domain.ods.record.dto.Record;

import java.util.List;

public interface RecordListDAO {

    /**
     * 진료기록 - 일반 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 진료기록 - 일반 목록
     */
    List<Record.Response> getMedicalRecordList(Record.Request request);

    /**
     * 진료기록 - 수술기록 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 진료기록 - 수술기록 목록
     */
    List<Record.Response> getSurgeryRecordList(Record.Request request);

    /**
     * 진료기록 - 퇴원기록 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 진료기록 - 퇴원기록 목록
     */
    List<Record.Response> getDischargeRecordList(Record.Request request);

    /**
     * 진료기록 - 타과의뢰 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 진료기록 - 타과의뢰 목록
     */
    List<Record.Response> getRequestRecordList(Record.Request request);

    /**
     * 진료기록 - 마취기록, 마취전평가 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 진료기록 - 마취기록, 마취전평가 목록
     */
    List<Record.Response> getAnesthesiaRecordList(Record.Request request);

    /**
     * 처방 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 처방 목록
     */
    List<Record.Response> getOrderRecordList(Record.Request request);

    /**
     * 영상검사 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 영상검사 목록
     */
    List<Record.Response> getExamPictureRecordList(Record.Request request);

    /**
     * 병리검사 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 병리검사 목록
     */
    List<Record.Response> getExamPathologyRecordList(Record.Request request);

    /**
     * 검체검사 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 검체검사 목록
     */
    List<Record.Response> getExamSpecimenRecordList(Record.Request request);

    /**
     * 기능검사 목록 조회
     *
     * @param request 조회할 기록 목록의 상세 조건
     * @return 기능검사 목록
     */
    List<Record.Response> getExamFunctionRecordList(Record.Request request);

    /**
     * 스캔자료 목록 조회
     *
     * @param request - 조회할 기록 목록의 상세 조건
     * @return 스캔자료 목록
     */
    List<Record.Response> getScanRecordList(Record.Request request);

    List<Record.Response> getNrObservationRecordList(Record.Request request);
    List<Record.Response> getNrInpatientRecordList(Record.Request request);
    List<Record.Response> getNrExecuteRecordList(Record.Request request);
}
