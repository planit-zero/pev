package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.pathology.PathologyContent;
import ai.planit.pev.strategy.chart.object.pathology.PathologyData;
import ai.planit.pev.strategy.chart.object.pathology.PathologyProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PathologyChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        PathologyData.Response pathologyData = (PathologyData.Response) source;

        List<PathologyContent> pathologyContentList = pathologyData.getPathologyContentList();
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
            String[] categoryList = {"M", "A", "R", "C", "F", "E"};

            for (int i = 0; i < categoryList.length; i++) {
                data.addAll(getPathologyDataByCategory(
                        pathologyContentList,
                        valueFormat,
                        createProcess,
                        microscopicProcess,
                        decodeProcess,
                        inputProcess,
                        categoryList[i],
                        String.valueOf((i + 1))));
            }
        }

        return data;
    }

    private List<ChartElement> getPathologyDataByCategory(
            List<PathologyContent> pathologyContentList,
            ChartElement valueFormat,
            String createProcess,
            String microscopicProcess,
            String decodeProcess,
            String inputProcess,
            String category,
            String seq) {
        List<ChartElement> data = new ArrayList<>();

        Optional<PathologyContent> mainContent = pathologyContentList
                .stream()
                .filter(c -> c.getPlrtTpCd().equals(category))
                .findFirst();

        mainContent.ifPresent((c) -> {
            if (valueFormat.getId().equals(String.format("pathology-%s-1-1", seq))) {
                valueFormat.setContent(c.getPlrtLdat());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals(String.format("pathology-%s-2-1", seq))) {
                valueFormat.setContent(c.getAcptDt());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals(String.format("pathology-%s-3-1", seq))) {
                valueFormat.setContent(c.getLshDt());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals(String.format("pathology-%s-4-1", seq))) {
                valueFormat.setContent(createProcess);
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals(String.format("pathology-%s-5-1", seq))) {
                valueFormat.setContent(microscopicProcess);
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals(String.format("pathology-%s-6-1", seq))) {
                valueFormat.setContent(decodeProcess);
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals(String.format("pathology-%s-7-1", seq))) {
                valueFormat.setContent(inputProcess);
                data.add(valueFormat);
            }
        });

        return data;
    }
}
