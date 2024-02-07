package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.dialysis.blood.BloodDialysisData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BloodDialysisChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        BloodDialysisData bloodDialysisData = (BloodDialysisData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("nr-blood-dialysis-1-0-1")) {

                StringBuilder sb = new StringBuilder();

                sb.append(String.format("%s : %s\r\n", "HD No.", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getHdNo(), "")));
                sb.append(String.format("%s : %s\r\n", "Machine No.", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getMachineNo(), "")));
                sb.append(String.format("%s : %s\r\n", "Duration", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getDuration(), "")));
                sb.append(String.format("%s : %s\r\n", "초/재진", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getPatientType(), "")));
                sb.append(String.format("%s : %s\r\n", "현재 pre wt.", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getCurrentPreWeight(), "")));
                sb.append(String.format("%s : %s\r\n", "현재 post wt.", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getCurrentPostWeight(), "")));
                sb.append(String.format("%s : %s\r\n", "현재 wt. lost", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getCurrentWeightLost(), "")));
                sb.append(String.format("%s : %s\r\n", "현재 UF", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getUf(), "")));
                sb.append(String.format("%s : %s\r\n", "이전 post wt.", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getBeforePostWeight(), "")));
                sb.append(String.format("%s : %s\r\n", "이전 dry wt.", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getDryWeight(), "")));
                sb.append(String.format("%s : %s\r\n", "이전 wt. gain", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getBeforeWeightGain(), "")));
                sb.append(String.format("%s : %s\r\n", "이전 TUF", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getTuf(), "")));
                sb.append(String.format("%s : %s\r\n", "Priming Fluid", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getPrimingFluid(), "")));
                sb.append(String.format("%s : %s\r\n", "투석액", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getFluidType(), "")));
                sb.append(String.format("%s : %s\r\n", "투석기종류", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getMachineType(), "")));
                sb.append(String.format("%s : %s\r\n", "혈관종류", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getBloodVesselType(), "")));
                sb.append(String.format("%s : %s\r\n", "Priming", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getPrimingStfNm(), "")));
                sb.append(String.format("%s : %s\r\n", "Start", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getStartStfNm(), "")));
                sb.append(String.format("%s : %s\r\n", "Assign", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getAssignStfNm(), "")));
                sb.append(String.format("%s : %s\r\n", "Finish", StringUtils.defaultIfBlank(bloodDialysisData.getInfo().getFinishStfNm(), "")));

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }
        }

        return data;
    }
}
