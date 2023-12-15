package ai.planit.pev.utility;


import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PevChartUtil {
    public static List<ChartSection> getChartSections(List<ChartElement> elements) {
        List<ChartSection> sections = new ArrayList<>();

        List<Integer> sectionIds = elements
                .stream()
                .map(ChartElement::getSectionId)
                .distinct()
                .collect(Collectors.toList());

        for (Integer sectionId : sectionIds) {
            List<ChartElement> sectionElements = elements
                    .stream()
                    .filter(se -> se.getSectionId() == sectionId)
                    .collect(Collectors.toList());

            sections.add(getChartSection(sectionId, sectionElements));
        }

        return sections;
    }

    private static ChartSection getChartSection(Integer sectionId, List<ChartElement> sectionElements) {
        return ChartSection
                .builder()
                .sectionId(sectionId)
                .entities(getChartEntities(sectionElements))
                .build();
    }

    private static List<ChartEntity> getChartEntities(List<ChartElement> sectionElements) {
        List<ChartEntity> entities = new ArrayList<>();

        List<ChartElement> entityElements = sectionElements
                .stream()
                .filter(se -> se.getClassType().equals(ChartClassType.ENTITY))
                .collect(Collectors.toList());

        for (ChartElement entityElement : entityElements) {
            ChartEntity entity = getChartEntity(sectionElements, entityElement);
            if (entity != null) entities.add(entity);
        }

        return entities;
    }

    private static ChartEntity getChartEntity(List<ChartElement> sectionElements, ChartElement entityElement) {
        ChartEntity entity = new ChartEntity(entityElement);

        List<ChartAttribute> chartAttributes = getChartAttributes(sectionElements, entityElement.getId());
        List<ChartValue> chartValues = getChartValues(sectionElements, entityElement.getId());

        if (chartAttributes.size() == 0 && chartValues.size() == 0) return null;

        entity.setAttributes(chartAttributes);
        entity.setValues(chartValues);

        return entity;
    }

    private static List<ChartAttribute> getChartAttributes(List<ChartElement> sectionElements, String parentId) {
        List<ChartAttribute> attributes = new ArrayList<>();

        List<ChartElement> attributeElements = sectionElements
                .stream()
                .filter(se -> se.getClassType().equals(ChartClassType.ATTRIBUTE) && se.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (ChartElement attributeElement : attributeElements) {
            ChartAttribute chartAttribute = getChartAttribute(sectionElements, attributeElement);
            if (chartAttribute != null) attributes.add(chartAttribute);
        }

        return attributes;
    }

    private static ChartAttribute getChartAttribute(List<ChartElement> sectionElements, ChartElement attributeElement) {
        ChartAttribute attribute = new ChartAttribute(attributeElement);

        List<ChartAttribute> chartAttributes = getChartAttributes(sectionElements, attributeElement.getId());
        List<ChartValue> chartValues = getChartValues(sectionElements, attributeElement.getId());

        if (chartAttributes.size() == 0 && chartValues.size() == 0) return null;

        attribute.setAttributes(chartAttributes);
        attribute.setValues(chartValues);

        return attribute;
    }

    private static List<ChartValue> getChartValues(List<ChartElement> sectionElements, String parentId) {
        List<ChartValue> values = new ArrayList<>();

        List<ChartElement> valueElements = sectionElements
                .stream()
                .filter(se -> se.getClassType().equals(ChartClassType.VALUE) && se.getParentId().equals(parentId))
                .collect(Collectors.toList());

        for (ChartElement valueElement : valueElements) {
            if (valueElement.getContent() != null && !valueElement.getContent().equals("")) {
                values.add(getChartValue(valueElement));
            }
        }

        return values;
    }

    private static ChartValue getChartValue(ChartElement valueElement) {
        return new ChartValue(valueElement);
    }
}
