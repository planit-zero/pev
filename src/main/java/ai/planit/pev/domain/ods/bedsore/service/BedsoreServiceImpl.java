package ai.planit.pev.domain.ods.bedsore.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.ods.bedsore.dao.BedsoreDAO;
import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.object.bedsore.BedsoreContent;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BedsoreServiceImpl implements BedsoreService {
    private final BedsoreDAO bedsoreDAO;

    @Override
    public List<ChartElement> getBedsoreFormat(String keyId) {
        List<ChartElement> format = new ArrayList<>();

        Map<String, String> map = new HashMap<>();

        map.put("region", "부위명(상세부위명)");
        map.put("writingDateTime", "작성일시");
        map.put("stage", "욕창단계");
        map.put("size", "가로 / 세로 / 깊이");
        map.put("dressingAction", "Dressing 시행");
        map.put("dressingMethod", "Dressing 방법");
        map.put("dressingKind", "Dressing 재료");
        map.put("writingDeptNm", "작성부서");
        map.put("writerNm", "작성/수정자");

        List<BedsoreContent> contents = bedsoreDAO.getBedsoreContents(keyId);

        for (int i = 0; i < contents.size(); i++) {
            ChartElement entity = new ChartElement();

            entity.setSectionId(1);
            entity.setId(String.format("%s-%d", "nr-bedsore", i));
            entity.setParentId("-1000");
            entity.setMdfmCpemNo(String.format("%s-%d", "nr-bedsore", i));
            entity.setClassType(ChartClassType.ENTITY);
            entity.setControlType(ChartControlType.LABEL);
            entity.setMaskingType(null);
            entity.setContent(String.format("%s-%d", "욕창일자별기록", i + 1));
            entity.setDesc(null);
            entity.setStyle(null);

            format.add(entity);

            Object obj = contents.get(i);

            for (int j = 0; j < obj.getClass().getDeclaredFields().length; j++) {
                Field field = obj.getClass().getDeclaredFields()[j];
                field.setAccessible(true);

                ChartElement attribute = new ChartElement();

                attribute.setSectionId(1);
                attribute.setId(String.format("%s-%d-%d", "nr-bedsore", i, j));
                attribute.setParentId(String.format("%s-%d", "nr-bedsore", i));
                attribute.setMdfmCpemNo(String.format("%s-%d-%d", "nr-bedsore", i, j));
                attribute.setClassType(ChartClassType.ATTRIBUTE);
                attribute.setControlType(ChartControlType.LABEL);
                attribute.setMaskingType(null);
                attribute.setContent(map.get(field.getName()));
                attribute.setDesc(null);
                attribute.setStyle(null);

                format.add(attribute);

                ChartElement value = new ChartElement();

                value.setSectionId(1);
                value.setId(String.format("%s-%d-%d-%d", "nr-bedsore", i, j, 1));
                value.setParentId(String.format("%s-%d-%d", "nr-bedsore", i, j));
                value.setMdfmCpemNo(String.format("%s-%d-%d-%d", "nr-bedsore", i, j, 1));
                value.setClassType(ChartClassType.VALUE);
                value.setControlType(ChartControlType.TEXT_BOX);
                value.setMaskingType(null);

                try {
                    value.setContent((String) field.get(obj));
                } catch (Exception e) {
                    throw new BaseException(ErrorType.FAILED_GET_FORM_STYLE);
                }

                value.setDesc(null);
                value.setStyle(null);

                format.add(value);
            }

        }

        return format;
    }
}
