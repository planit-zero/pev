package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.cpr.CprData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CprChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        List<CprData> cprDataList = (List<CprData>) source;
        Map<Integer, String> cprDataMap = getCprDataMap(cprDataList);

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            StringBuilder sb = new StringBuilder();

            String formId = valueFormat.getId();

            // 환자정보
            if (formId.equals("cpr-1-0-1")) {
                sb.append("항목|||값;");
                sb.append(getRow(cprDataMap, "환자명", 7010050));
                sb.append(getRow(cprDataMap, "등록번호", 7010070));
                sb.append(getRow(cprDataMap, "나이", 7010090));
                sb.append(getRow(cprDataMap, "성별", 7010110));
                sb.append(getRow(cprDataMap, "진료과", 7010060));
                sb.append(getRow(cprDataMap, "병동", 7010080));
                sb.append(getRow(cprDataMap, "주진단명", 7010100));
                sb.append(getRow(cprDataMap, "입원일자", 7010120));
            }

            // CPR 요약
            if (formId.equals("cpr-2-0-1")) {
                sb.append("항목|||값;");
                sb.append(getRow(cprDataMap, "CPR 발생일시", 7010030));
                sb.append(getRow(cprDataMap, "CPR팀 현장도착시간", 7010170));
                sb.append(getRow(cprDataMap, "첫 Epinephrine 투약시간", 7010240));
                sb.append(getRow(cprDataMap, "심폐정지 목격", 7010150));
                sb.append(getRow(cprDataMap, "흉부압박 시작", 7010150));
                sb.append(getRow(cprDataMap, "기관내 삽관", 7010260));
                sb.append(getRow(cprDataMap, "CPR 방송", 7010170));
                sb.append(getRow(cprDataMap, "첫 제새동", 7010210));
                sb.append(getRow(cprDataMap, "CPR 중단", 7010660));
            }

            // 발생장소
            if (formId.equals("cpr-3-0-1")) {
                sb.append("항목|||값;");
                sb.append(getRow(cprDataMap, "발생장소", 7010270));
            }

            // 심정지 발생 전 마지막 활력징후
            if (formId.equals("cpr-4-0-1")) {
                sb.append("측정된 시간|||SBP|||DBP|||HR/PR|||PR|||BT|||SpO2;");
                if (!StringUtils.isEmpty(cprDataMap.get(7010290))) {
                    sb.append(cprDataMap.get(7010290)).append("|||").append(cprDataMap.getOrDefault(7010300, "")).append("|||").append(cprDataMap.getOrDefault(7010310, "")).append("|||").append(cprDataMap.getOrDefault(7010320, "")).append("|||").append(cprDataMap.getOrDefault(7010330, "")).append("|||").append(cprDataMap.getOrDefault(7010690, "")).append("|||").append(cprDataMap.getOrDefault(7010340, "")).append(";");
                }
                if (!StringUtils.isEmpty(cprDataMap.get(7010350))) {
                    sb.append(cprDataMap.get(7010350)).append("|||").append(cprDataMap.getOrDefault(7010360, "")).append("|||").append(cprDataMap.getOrDefault(7010370, "")).append("|||").append(cprDataMap.getOrDefault(7010380, "")).append("|||").append(cprDataMap.getOrDefault(7010390, "")).append("|||").append(cprDataMap.getOrDefault(7010700, "")).append("|||").append(cprDataMap.getOrDefault(7010400, "")).append(";");
                }
                if (!StringUtils.isEmpty(cprDataMap.get(7010410))) {
                    sb.append(cprDataMap.get(7010410)).append("|||").append(cprDataMap.getOrDefault(7010420, "")).append("|||").append(cprDataMap.getOrDefault(7010430, "")).append("|||").append(cprDataMap.getOrDefault(7010440, "")).append("|||").append(cprDataMap.getOrDefault(7010450, "")).append("|||").append(cprDataMap.getOrDefault(7010710, "")).append("|||").append(cprDataMap.getOrDefault(7010460, "")).append(";");
                }
            }

            // 심폐정지
            if (formId.equals("cpr-5-0-1")) {
                sb.append("항목|||값;");
                sb.append(getRow(cprDataMap, "심폐정지 시 환자상태", 7010470));
                sb.append(getRow(cprDataMap, "초기 심폐정지 원인", 7010490));
                sb.append(getRow(cprDataMap, "맥박유무", 7010500));
                sb.append(getRow(cprDataMap, "호흡유무", 7010510));
            }

            // 초기 심전도 소견
            if (formId.equals("cpr-6-0-1")) {
                sb.append("항목|||값;");
                sb.append(getRow(cprDataMap, "소견", 7010520));
                sb.append(getRow(cprDataMap, "기타상세", 7010530));
            }

            // CPR 팀
            if (formId.equals("cpr-7-0-1")) {
                sb.append("항목|||값;");
                sb.append(getRow(cprDataMap, "CPR팀 도착 전 시행된 소생술기", 7010540));
                sb.append(getRow(cprDataMap, "CPR팀에 의한 소생술 시도", 7010550));
                sb.append(getRow(cprDataMap, "CPR팀에 의한 소생술 시도 안함 이유", 7010560));
                sb.append(getRow(cprDataMap, "CPR팀에 의해 관찰된 첫 리듬", 7010570));
                sb.append(getRow(cprDataMap, "제세동 여부", 7010580));
                sb.append(getRow(cprDataMap, "제세동 횟수", 7010590));
                sb.append(getRow(cprDataMap, "제세동 종류", 7010600));
                sb.append(getRow(cprDataMap, "제세동 최초", 7010610));
                sb.append(getRow(cprDataMap, "성공여부", 7010620));
                sb.append(getRow(cprDataMap, "기관내 삽관", 7010630));
                sb.append(getRow(cprDataMap, "CPR 중단사유", 7010640));
                sb.append(getRow(cprDataMap, "CPR 중단사유 기타상세", 7010650));
                sb.append(getRow(cprDataMap, "CPR 중단 시간", 7010660));
            }

            // 작성자 정보
            if (formId.equals("cpr-8-0-1")) {
                sb.append("항목|||값;");
                sb.append(getRow(cprDataMap, "담당간호사", 7010020));
                sb.append(getRow(cprDataMap, "주치의/당직의", 7010660));
            }

            sb.deleteCharAt(sb.length() - 1);
            valueFormat.setContent(sb.toString());
            data.add(valueFormat);
        }

        return data;
    }

    private Map<Integer, String> getCprDataMap(List<CprData> cprDataList) {
        Map<Integer, String> map = new HashMap<>();

        for (CprData cprData : cprDataList) {
            int cprDataId = Integer.parseInt(cprData.getId());
            map.merge(cprDataId, cprData.getContent(), (a, b) -> a + "," + b);
        }

        return map;
    }

    private String getRow(Map<Integer, String> cprDataMap, String entity, int value) {
        return entity + "|||" + cprDataMap.getOrDefault(value, "") + ";";
    }

}
