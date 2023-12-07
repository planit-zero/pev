package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.pathology.PathologyContent;
import ai.planit.pev.strategy.chart.object.pathology.PathologyData;
import ai.planit.pev.strategy.chart.object.pathology.PathologyProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PathologyChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        PathologyData.Response pathologyData = (PathologyData.Response) source;

        PathologyContent pathologyContent = pathologyData.getPathologyContent();
        List<PathologyProcess> pathologyProcessList = pathologyData.getPathologyProcessList();

        // 제작
        String createProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("B") || process.getPthlProTpCd().equals("H") || process.getPthlProTpCd().equals("L"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        // 육안
        String microscopicProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("F") || process.getPthlProTpCd().equals("G"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        // 판독준비
        String decodeProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("M"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        // 결과입력
        String inputProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("P"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("pathology-1-1-1")) {
                valueFormat.setContent(pathologyContent.getPlrtLdat());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("pathology-1-2-1")) {
                valueFormat.setContent(pathologyContent.getAcptDt());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("pathology-1-3-1")) {
                valueFormat.setContent(pathologyContent.getLshDt());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("pathology-1-4-1")) {
                valueFormat.setContent(createProcess);
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("pathology-1-5-1")) {
                valueFormat.setContent(microscopicProcess);
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("pathology-1-6-1")) {
                valueFormat.setContent(decodeProcess);
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("pathology-1-7-1")) {
                valueFormat.setContent(inputProcess);
                data.add(valueFormat);
            }
        }

        return data;
    }
}
