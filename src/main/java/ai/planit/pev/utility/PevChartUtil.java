package ai.planit.pev.utility;


import ai.planit.pev.domain.ods.record.constant.RecordTarget;
import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PevChartUtil {
    public static List<ChartSection> getChartSections(List<ChartElement> elements, List<ChartStyleSection> style, boolean applyStyle) {
        List<ChartSection> sections = new ArrayList<>();

        List<Integer> sectionIds = elements
                .stream()
                .map(ChartElement::getSectionId)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        for (Integer sectionId : sectionIds) {
            List<ChartElement> sectionElements = elements
                    .stream()
                    .filter(se -> se.getSectionId() == sectionId)
                    .collect(Collectors.toList());

            Optional<ChartStyleSection> chartStyleSection = style.stream().filter(s -> s.getMdfmSctnSeq() == sectionId).findAny();

            sections.add(getChartSection(sectionId, sectionElements, chartStyleSection.orElse(null), applyStyle));
        }

        return sections;
    }

    private static ChartSection getChartSection(Integer sectionId, List<ChartElement> sectionElements, ChartStyleSection chartStyleSection, boolean applyStyle) {
        ChartSection section = new ChartSection();

        section.setSectionId(sectionId);
        section.setEntities(getChartEntities(sectionElements, chartStyleSection, applyStyle));
        section.setStyle(chartStyleSection);

        return section;
    }

    private static List<ChartEntity> getChartEntities(List<ChartElement> sectionElements, ChartStyleSection chartStyleSection, boolean applyStyle) {
        List<ChartEntity> entities = new ArrayList<>();

        List<ChartElement> entityElements = sectionElements
                .stream()
                .filter(se -> se.getClassType().equals(ChartClassType.ENTITY))
                .collect(Collectors.toList());

        for (ChartElement entityElement : entityElements) {
            ChartEntity entity = getChartEntity(sectionElements, entityElement, chartStyleSection, applyStyle);
            if (entity != null) entities.add(entity);
        }

        return entities;
    }

    private static ChartEntity getChartEntity(List<ChartElement> sectionElements, ChartElement entityElement, ChartStyleSection chartStyleSection, boolean applyStyle) {
        ChartEntity entity = new ChartEntity(entityElement, getChartStyleItem(chartStyleSection, entityElement.getMdfmCpemNo()));

        List<ChartAttribute> chartAttributes = getChartAttributes(sectionElements, chartStyleSection, entityElement.getId(), applyStyle);
        List<ChartValue> chartValues = getChartValues(sectionElements, chartStyleSection, entityElement.getId(), applyStyle);

        if (!applyStyle && chartAttributes.isEmpty() && chartValues.isEmpty()) return null;

        entity.setAttributes(chartAttributes);
        entity.setValues(chartValues);

        return entity;
    }

    private static List<ChartAttribute> getChartAttributes(List<ChartElement> sectionElements, ChartStyleSection chartStyleSection, String parentId, boolean applyStyle) {
        List<ChartAttribute> attributes = new ArrayList<>();

        List<ChartElement> attributeElements = sectionElements
                .stream()
                .filter(se -> se.getClassType().equals(ChartClassType.ATTRIBUTE) && se.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (ChartElement attributeElement : attributeElements) {
            ChartAttribute chartAttribute = getChartAttribute(sectionElements, attributeElement, chartStyleSection, applyStyle);
            if (chartAttribute != null) attributes.add(chartAttribute);
        }

        return attributes;
    }

    private static ChartAttribute getChartAttribute(List<ChartElement> sectionElements, ChartElement attributeElement, ChartStyleSection chartStyleSection, boolean applyStyle) {
        ChartAttribute attribute = new ChartAttribute(attributeElement, getChartStyleItem(chartStyleSection, attributeElement.getMdfmCpemNo()));

        List<ChartAttribute> chartAttributes = getChartAttributes(sectionElements, chartStyleSection, attributeElement.getId(), applyStyle);
        List<ChartValue> chartValues = getChartValues(sectionElements, chartStyleSection, attributeElement.getId(), applyStyle);

        if (!applyStyle && chartAttributes.isEmpty() && chartValues.isEmpty()) return null;

        attribute.setAttributes(chartAttributes);
        attribute.setValues(chartValues);

        return attribute;
    }

    private static List<ChartValue> getChartValues(List<ChartElement> sectionElements, ChartStyleSection chartStyleSection, String parentId, boolean applyStyle) {
        List<ChartValue> values = new ArrayList<>();

        List<ChartElement> valueElements = sectionElements
                .stream()
                .filter(se -> se.getClassType().equals(ChartClassType.VALUE) && se.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (ChartElement valueElement : valueElements) {
            if (applyStyle || (valueElement.getContent() != null && !valueElement.getContent().isEmpty())) {
                values.add(getChartValue(valueElement, chartStyleSection));
            }
        }

        return values;
    }

    private static ChartValue getChartValue(ChartElement valueElement, ChartStyleSection chartStyleSection) {
        return new ChartValue(valueElement, getChartStyleItem(chartStyleSection, valueElement.getMdfmCpemNo()));
    }

    private static ChartStyleItem getChartStyleItem(ChartStyleSection chartStyleSection, String id) {
        if (chartStyleSection == null) return null;
        Optional<ChartStyleItem> chartStyleItem = chartStyleSection.getItems().stream().filter(i -> i.getId().equals(id)).findAny();
        return chartStyleItem.orElse(null);
    }

    public static boolean applyStyle(String recordDetailType) {
        List<String> targetList = new ArrayList<>();

        targetList.add(RecordTarget.CERTIFICATE_REQUEST.getType());
        targetList.add(RecordTarget.CERTIFICATE_ACCIDENT.getType());
        targetList.add(RecordTarget.MEDICAL_DEPARTMENT.getType());

        return targetList.contains(recordDetailType);
    }
}
