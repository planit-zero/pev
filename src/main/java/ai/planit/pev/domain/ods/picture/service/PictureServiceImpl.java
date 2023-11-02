package ai.planit.pev.domain.ods.picture.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.ods.picture.dao.PictureDAO;
import ai.planit.pev.domain.ods.picture.dto.PictureData;
import ai.planit.pev.domain.ods.record.constant.RecordElementAlignment;
import ai.planit.pev.domain.ods.record.constant.RecordElementDisplay;
import ai.planit.pev.domain.ods.record.constant.RecordElementTextDecoration;
import ai.planit.pev.domain.ods.record.dto.*;
import ai.planit.pev.utility.PevEntityUtil;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final PictureDAO pictureDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public RecordSheet getRecordSheet(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        PictureData.Request request = new PictureData.Request();
        request.setIptnNo(record.getExamKey());

        PictureData.Response pictureData = pictureDAO.getPictureData(request);

        RecordSheet recordSheet = new RecordSheet();
        recordSheet.setHeaderSection(getRecordHeaderSection(pictureData));
        recordSheet.setSections(getRecordSections(pictureData));

        return recordSheet;
    }

    /**
     * 영상검사 기록의 섹션 목록 생성
     *
     * @param pictureData 영상검사 기록 정보
     * @return 영상검사 섹션 목록
     */
    private List<RecordSection> getRecordSections(PictureData.Response pictureData) {
        List<RecordSection> sections = new ArrayList<>();

        // 영상검사는 단일 섹션으로 구성
        sections.add(getRecordSection(pictureData));

        return sections;
    }

    /**
     * 영상검사 기록의 헤더 섹션 생성
     *
     * @param pictureData 영상검사 기록 정보
     * @return 영상검사 기록의 헤더 섹션
     */
    private RecordSection getRecordHeaderSection(PictureData.Response pictureData) {
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        RecordEntity entity = new RecordEntity();
        entity.setText("영상검사결과");

        List<RecordAttribute> attributes = new ArrayList<>();

        RecordElement element = new RecordElement();
        element.setDisplay(RecordElementDisplay.INLINE.getValue());

        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "검사일 :", pictureData.getExmDt()));
        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "판독일 :", pictureData.getIptnDtm()));
        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "검사명 :", pictureData.getOrdNm()));

        entity.setAttributes(attributes);
        entities.add(entity);

        section.setEntities(entities);

        return section;
    }

    /**
     * 영상검사 섹션 생성
     *
     * @param pictureData 영상검사 기록 정보
     * @return 영상검사 기록 섹션
     */
    private RecordSection getRecordSection(PictureData.Response pictureData) {
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        RecordElement element = new RecordElement();
        element.setTextDecoration(RecordElementTextDecoration.UNDERLINE.getValue());

        entities.add(PevEntityUtil.getSimpleTextEntity(element, "[Conclusion]", pictureData.getIptnCncsCnte()));
        entities.add(PevEntityUtil.getSimpleTextEntity(element, "[Finding]", pictureData.getTh1IptnExpl()));

        if (pictureData.getCopnCnte() != null) {
            entities.add(PevEntityUtil.getSimpleTextEntity(element, "[Clinical Information]", pictureData.getCopnCnte()));
        }

        entities.add(getDecoderEntity(pictureData));

        section.setEntities(entities);
        return section;
    }

    /**
     * 1~6 번째 판독의 값을 토대로 판독의 Entity 생성
     *
     * @param pictureData 영상검사 기록 정보
     * @return 판독의 Entity
     */
    private RecordEntity getDecoderEntity(PictureData.Response pictureData) {
        String[] decoders = {
                pictureData.getTh1IpdrStfNm(),
                pictureData.getTh2IpdrStfNm(),
                pictureData.getTh3IpdrStfNm(),
                pictureData.getTh4IpdrStfNm(),
                pictureData.getTh5IpdrStfNm(),
                pictureData.getTh6IpdrStfNm()
        };

        String decoderStr = Stream.of(decoders)
                .filter(d -> d != null && !d.isEmpty())
                .collect(Collectors.joining(", "));

        RecordElement element = new RecordElement();
        element.setDisplay(RecordElementDisplay.INLINE.getValue());
        element.setAlignment(RecordElementAlignment.RIGHT.getValue());

        return PevEntityUtil.getSimpleTextEntity(element, "판독의 :", decoderStr);
    }
}
