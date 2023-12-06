package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.constant.ChartMaskingType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.pathology.PathologyContent;
import ai.planit.pev.strategy.chart.object.pathology.PathologyData;
import ai.planit.pev.strategy.chart.object.pathology.PathologyProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PathologyChartStrategy implements ChartStrategy {
    @Override
    public <T> List<ChartElement> getFormat(T source) {
        List<ChartElement> format = new ArrayList<>();

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1")
                .parentId("-1000")
                .classType(ChartClassType.ENTITY)
                .controlType(ChartControlType.LABEL)
                .content("")
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-1")
                .parentId("pathology-1")
                .classType(ChartClassType.ATTRIBUTE)
                .controlType(ChartControlType.LABEL)
                .content("판독결과")
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-1-1")
                .parentId("pathology-1-1")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.RICH_TEXT_BOX)
                .maskingType(ChartMaskingType.ALL)
                .content(null)
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-2")
                .parentId("pathology-1")
                .classType(ChartClassType.ATTRIBUTE)
                .controlType(ChartControlType.LABEL)
                .content("접수일")
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-2-1")
                .parentId("pathology-1-2")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.DATE)
                .content(null)
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-3")
                .parentId("pathology-1")
                .classType(ChartClassType.ATTRIBUTE)
                .controlType(ChartControlType.LABEL)
                .content("판독일")
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-3-1")
                .parentId("pathology-1-3")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.DATE)
                .content(null)
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-4")
                .parentId("pathology-1")
                .classType(ChartClassType.ATTRIBUTE)
                .controlType(ChartControlType.LABEL)
                .content("제작")
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-4-1")
                .parentId("pathology-1-4")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.NAME)
                .content(null)
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-5")
                .parentId("pathology-1")
                .classType(ChartClassType.ATTRIBUTE)
                .controlType(ChartControlType.LABEL)
                .content("육안")
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-5-1")
                .parentId("pathology-1-5")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.NAME)
                .content(null)
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-6")
                .parentId("pathology-1")
                .classType(ChartClassType.ATTRIBUTE)
                .controlType(ChartControlType.LABEL)
                .content("판독준비")
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-6-1")
                .parentId("pathology-1-6")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.NAME)
                .content(null)
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-7")
                .parentId("pathology-1")
                .classType(ChartClassType.ATTRIBUTE)
                .controlType(ChartControlType.LABEL)
                .content("결과입력")
                .desc(null)
                .build());

        format.add(ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-7-1")
                .parentId("pathology-1-7")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.NAME)
                .content(null)
                .desc(null)
                .build());

        return format;
    }

    @Override
    public <T> List<ChartElement> getData(T source) {
        PathologyData.Response pathologyData = (PathologyData.Response) source;

        PathologyContent pathologyContent = pathologyData.getPathologyContent();
        List<PathologyProcess> pathologyProcessList = pathologyData.getPathologyProcessList();

        List<ChartElement> data = new ArrayList<>();

        ChartElement freeTextElement = ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-1-1")
                .parentId("pathology-1-1")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.RICH_TEXT_BOX)
                .maskingType(ChartMaskingType.ALL)
                .content(pathologyContent.getPlrtLdat())
                .desc(null)
                .build();

        ChartElement acceptDateElement = ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-2-1")
                .parentId("pathology-1-2")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.DATE)
                .content(pathologyContent.getAcptDt())
                .desc(null)
                .build();

        ChartElement decodeDateElement = ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-3-1")
                .parentId("pathology-1-3")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.DATE)
                .content(pathologyContent.getLshDt())
                .desc(null)
                .build();

        // 제작
        String createProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("B") || process.getPthlProTpCd().equals("H") || process.getPthlProTpCd().equals("L"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        ChartElement createProcessElement = ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-4-1")
                .parentId("pathology-1-4")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.NAME)
                .content(createProcess)
                .desc(null)
                .build();

        // 육안
        String microscopicProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("F") || process.getPthlProTpCd().equals("G"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        ChartElement microscopicProcessElement = ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-5-1")
                .parentId("pathology-1-5")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.NAME)
                .content(microscopicProcess)
                .desc(null)
                .build();

        // 판독준비
        String decodeProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("M"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        ChartElement decodeProcessElement = ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-6-1")
                .parentId("pathology-1-6")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.NAME)
                .content(decodeProcess)
                .desc(null)
                .build();

        // 결과입력
        String inputProcess = pathologyProcessList
                .stream()
                .filter(process -> process.getPthlProTpCd().equals("P"))
                .filter(process -> process.getWkFmtStfNm() != null && !process.getWkFmtStfNm().isEmpty())
                .map(PathologyProcess::getWkFmtStfNm)
                .collect(Collectors.joining("/"));

        ChartElement inputProcessElement = ChartElement
                .builder()
                .sectionId(1)
                .id("pathology-1-7-1")
                .parentId("pathology-1-7")
                .classType(ChartClassType.VALUE)
                .controlType(ChartControlType.TEXT_BOX)
                .maskingType(ChartMaskingType.NAME)
                .content(inputProcess)
                .desc(null)
                .build();

        data.add(freeTextElement);
        data.add(acceptDateElement);
        data.add(decodeDateElement);
        data.add(createProcessElement);
        data.add(microscopicProcessElement);
        data.add(decodeProcessElement);
        data.add(inputProcessElement);

        return data;
    }
}
