package ai.planit.pev.domain.specimen.dao;

import ai.planit.pev.domain.specimen.dto.SpecimenData;
import ai.planit.pev.domain.specimen.dto.SpecimenHeaderData;
import ai.planit.pev.domain.specimen.dto.SpecimenInfo;

import java.util.List;

public interface SpecimenDAO {
    /**
     * 검체검사 기록 정보 (검사명, 검체명, 보고자) 를 조회한다.
     * @param request 환자병록번호, 검체번호, 검사분류코드
     * @return 조회한 검체검사의 기록 정보
     */
    SpecimenInfo.Response getSpecimenInfo(SpecimenInfo.Request request);

    /**
     * 검체검사 기록의 데이터 (항목명, 검사결과, 참고치) 를 조회한다.
     * @param request 환자병록번호, 검체번호, 검사분류코드
     * @return 조회한 검체검사 기록의 데이터
     */
    List<SpecimenData.Response> getSpecimenData(SpecimenData.Request request);

    /**
     * 검체검사 기록의 헤더 섹션 생성에 필요한 데이터 (처방분류명, 의뢰처, 진료과, 의뢰의사, 의뢰일시, 접수일시, 보고일시) 를 조회한다.
     *
     * @param spcmNo 검체번호
     * @return 조회한 검체검사 기록의 헤더 섹션 데이터
     */
    SpecimenHeaderData getSpecimenHeaderData(String spcmNo);
}
