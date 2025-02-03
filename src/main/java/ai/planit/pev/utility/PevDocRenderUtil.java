package ai.planit.pev.utility;

import ai.planit.pev.strategy.chart.object.common.ChartStyleItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class PevDocRenderUtil {
    public static String render(ChartStyleItem chartStyleItem) {
        StringBuilder sb = new StringBuilder();

        for(Item item : chartStyleItem.getChildren()) {
            if (isServiceControl(item.Type)) {
                item.Visibility = "Collapsed";
            }
        }

        List<RowGroup> rowGroups = getRowGroup(documentFormat.items.stream()
                .filter(item -> !isRootId(item.ID))
                .collect(Collectors.toList()));

        if (!rowGroups.isEmpty()) {
            sb.append(String.format("<div style=\"height:%spx\"></div>\n", rowGroups.get(0).y1));
        }

        renderTable(rowGroups, sb);


        return sb.toString();
    }

    private static void renderTable(List<RowGroup> rowGroups, StringBuilder sb) {
        int prevBottom = 0;

        for(RowGroup rowGroup : rowGroups) {
            List<Item> collapseList = rowGroup.items.stream()
                    .filter(item -> item.Visibility.equals("Collapsed")).collect(Collectors.toList());
            if (!collapseList.isEmpty()) {
                List<Item> visibleList = rowGroup.items.stream()
                        .filter(item -> !item.Visibility.equals("Collapsed"))
                        .collect(Collectors.toList());
                List<RowGroup> subRowGroups = getRowGroup(visibleList);
                if (!subRowGroups.isEmpty()) {
                    renderTable(subRowGroups, sb);
                }
                prevBottom = rowGroup.getYPoints().stream().max(Comparator.comparingInt(a -> a)).get();
            } else {
                List<Integer> sortedX = rowGroup.getXPoints().stream()
                        .sorted(Comparator.comparingInt(a -> a))
                        .collect(Collectors.toList());

                List<Integer> sortedY = rowGroup.getYPoints().stream()
                        .sorted(Comparator.comparingInt(a -> a))
                        .collect(Collectors.toList());

                int currentTop = sortedY.get(0);
                int topMargin = 0;
                Integer prevX = null;


                if (prevBottom != 0) {
                    topMargin = currentTop - prevBottom;
                }
                prevBottom = sortedY.get(sortedY.size() - 1);

                boolean hasRootId = rowGroup.getItems().stream()
                                .anyMatch(item -> isRootId(item.ParentID));

                if (hasRootId) {
                    sb.append(String.format("<table cellspacing=\"0\" cellpadding=\"0\" style=\"margin:%spx 0px 0px 0px; table-layout:fixed; width:%spx; border-collapse:separate\">\n",
                            topMargin, 500));
                    sb.append("<colgroup>");
                    sb.append("<col style=\"width:20px;\">");
                    sb.append("<col style=\"width:480px;\">");
                } else {
                    sb.append(String.format("<table cellspacing=\"0\" cellpadding=\"0\" style=\"margin:%spx 0px 0px 0px; table-layout:fixed; width:%spx; border-collapse:separate\">\n",
                            topMargin, sortedX.get(sortedX.size() - 1)));
                    sb.append("<colgroup>");
                    for(int curX : sortedX) {
                        if (Objects.nonNull(prevX)) {
                            int width = curX - prevX.intValue();
                            sb.append(String.format("<col style=\"width: %spx\">", width));
                        }
                        prevX = curX;
                    }
                }

                sb.append("</colgroup>\n");
                sb.append("<tbody>\n");

                for(int i = 0; i < sortedY.size() - 1; i++) {
                    int minHeight = sortedY.get(i + 1) - sortedY.get(i);
                    sb.append(String.format("<tr style=\"height:%spx\">\n", minHeight));
                    for (int j = 0; j < sortedX.size() - 1; j++) {
                        int startRowIndex = i;
                        int startColIndex = j;
                        Optional<Item> itemOpt = rowGroup.items.stream()
                                .filter(w -> w.getTop() == sortedY.get(startRowIndex) && w.getLeft() == sortedX.get(startColIndex))
                                .findFirst();

                        if (itemOpt.isPresent()) {
                            Item curItem = itemOpt.get();
                            int endRowIndex = IntStream.range(0, sortedY.size())
                                    .filter(index -> sortedY.get(index) == curItem.getYPoint())
                                    .findFirst()
                                    .orElse(-1);
                            int endColIndex =  IntStream.range(0, sortedX.size())
                                    .filter(index -> sortedX.get(index) == curItem.getLeft() + curItem.getWidth())
                                    .findFirst()
                                    .orElse(-1);
                            int colSpan = endColIndex - startColIndex;
                            int rowSpan = endRowIndex - startRowIndex;

                            if (curItem.Visibility.equals("Visible")) {
                                sb.append(getCellTag(curItem, colSpan, rowSpan));

                            } else {
                                sb.append(getNoneTag(colSpan, rowSpan, 0, 0, 0, 0));
                            }
                        } else {
                            int left = sortedX.get(j);
                            int top = sortedY.get(i);
                            int width = sortedX.get(j + 1) - sortedX.get(j);
                            int height = sortedY.get(i + 1) - sortedY.get(i);

                            Item rItem = rowGroup.items.stream()
                                    .filter(r -> {
                                        int rLeft = r.getLeft();
                                        int rTop = r.getTop();
                                        int rWidth = r.getWidth();
                                        int rHeight = r.getHeight();

                                        return (rLeft <= left) &&
                                                (rTop <= top) &&
                                                (rLeft + rWidth >= left + width) &&
                                                (rTop + rHeight >= top + height);
                                    })
                                    .findFirst()
                                    .orElse(null);

                            if (Objects.isNull(rItem)) {
                                sb.append("<td style=\"min-height: inherit;\" />");
                            }
                        }

                    }
                    sb.append("</tr>\n");
                }

                sb.append("</tbody>\n");
                sb.append("</table>\n");
            }
        }
    }

    private static String getCellTag(Item curItem, int colSpan, int rowSpan) {
        switch (curItem.Type) {
            case "Blank":
            case "Button":
            case "ComboBoxItem":
            case "ExamInterface":
            case "PopupSearchButton":
            case "SearchButtonControl":
            case "SearchButtonTable":
            case "SearchCheckButton":
            case "RepeaterItem":
            case "RepeaterSubItem":
                return "";
            case "None":
                return getNoneTag(colSpan, rowSpan, 0, 0, 0, 0);
            case "Table":
                return getTableTag(curItem, colSpan, rowSpan);
            case "Label":
                return getLabelTag(curItem, colSpan, rowSpan);
            case "RichTextBox":
                return getRichTextBox(curItem, colSpan, rowSpan);
            case "DateTextBox":
            case "ExprTextBox":
            case "NumericTextBox":
            case "TextBox":
            case "UmAlQuraDateTextBox":
                return getTextBoxTag(curItem, colSpan, rowSpan);
            case "CheckBox":
                return getCheckBoxTag(curItem, colSpan, rowSpan);
            case "RadioButton":
                return getRadioButtonTag(curItem, colSpan, rowSpan);
            default:
                return "";

        }
    }

    private static String getRadioButtonTag(Item item, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        String textAlign = "";
        String indent = "";

        if (item.FlowDirection.equals("LeftToRight")) {
            textAlign = item.HorizontalContentAlignment;
            if (Integer.parseInt(item.IndentUnit) > 0) {
                indent = String.format("padding-left: %spx;", item.IndentUnit);
            }
        } else {
            if (item.HorizontalContentAlignment.equals("Left")) {
                textAlign = "Right";
            } else if (item.HorizontalContentAlignment.equals("Right")) {
                textAlign = "Left";
            } else {
                textAlign = item.HorizontalContentAlignment;
            }

            if (Integer.parseInt(item.IndentUnit) > 0) {
                indent = String.format("padding-right: %spx;", item.IndentUnit);
            }
        }

        String text = spaceTrim(item);
        String vcAlign = getContentAlignmentChange(item.VerticalContentAlignment);

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; vertical-align:%s; font-size:%s; font-weight: %s; text-align:%s; direction:%s; \" >",
                colSpan, rowSpan, vcAlign, item.FontSize, item.FontWeight, textAlign, item.FlowDirection.equals("RightToLeft") ? "rtl" : "ltr"));
        sb.append(String.format("<p style=\"margin: 0px 0px 0px 0px; %s \">", indent));
        sb.append(String.format("<input disabled type=\"radio\" %s style=\"margin-left: 1px;margin-right: 4px; margin-bottom: -2px;\" />", item.getValue().equals("1") ? "checked" : ""));
        sb.append(String.format("<span>%s</span>", text));
        sb.append("</p>");
        sb.append("</td>");

        return sb.toString();
    }

    private static String getCheckBoxTag(Item item, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        String textAlign = "";
        String indent = "";

        if (item.FlowDirection.equals("LeftToRight")) {
            textAlign = item.HorizontalContentAlignment;
            if (Integer.parseInt(item.IndentUnit) > 0) {
                indent = String.format("padding-left: %spx;", item.IndentUnit);
            }
        } else {
            if (item.HorizontalContentAlignment.equals("Left")) {
                textAlign = "Right";
            } else if (item.HorizontalContentAlignment.equals("Right")) {
                textAlign = "Left";
            } else {
                textAlign = item.HorizontalContentAlignment;
            }

            if (Integer.parseInt(item.IndentUnit) > 0) {
                indent = String.format("padding-right: %spx;", item.IndentUnit);
            }
        }

        String text = spaceTrim(item);
        String vcAlign = getContentAlignmentChange(item.VerticalContentAlignment);

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; vertical-align:%s; font-size:%s; font-weight: %s; text-align:%s; direction:%s; \" >",
                colSpan, rowSpan, vcAlign, item.FontSize, item.FontWeight, textAlign, item.FlowDirection.equals("RightToLeft") ? "rtl" : "ltr"));
        sb.append(String.format("<p style=\"margin: 0px 0px 0px 0px; %s \">", indent));
        sb.append(String.format("<input disabled type=\"checkbox\" %s style=\"margin-left: 1px;margin-right: 4px; margin-bottom: -2px;\" />", item.getValue().equals("1") ? "checked" : ""));
        sb.append(String.format("<span>%s</span>", text));
        sb.append("</p>");
        sb.append("</td>");

        return sb.toString();
    }

    private static String getTextBoxTag(Item item, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        String text = "";
        String vcAlign = "";

        if (item.Type.equals("DateTextBox") || item.Type.equals("UmAlQuraDateTextBox")) {
            text = getDateTimeToStringFormat(item);
        } else {
            text = StringUtils.isEmpty(item.getValue()) ? "" : item.getValue().replace("  ", " &nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br />");

            if(item.Type.equals("ExprTextBox") || item.Type.equals("TextBox")) {
                if (!StringUtils.isEmpty(item.getValue()) && !StringUtils.isEmpty(item.Suffix))
                    text = text + item.Suffix;
            }
        }

        vcAlign = getContentAlignmentChange(item.VerticalContentAlignment);

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; border-radius: 2px; border-color: #B3B3B3; background-color: #ffffff; word-wrap: break-word;  border-style: solid; border-width: %spx %spx %spx %spx; padding:0px; text-align:%s; vertical-align: %s; font-size: %spx; font-weight: %s; font-style:%s\">",
                colSpan, rowSpan, 1, 1, 1, 1, item.TextAlignment, vcAlign, item.FontSize, item.FontWeight, item.FontStyle));
        sb.append(text);
        sb.append("</td>");


        return sb.toString();
    }

    public static String getDateTimeToStringFormat(Item item) {
        String returnValue = "";

        if (StringUtils.isEmpty(item.getValue())) {
            return returnValue;
        }

        try {
            // Parse the date string
            LocalDateTime dateTime = LocalDateTime.parse(item.getValue(), DateTimeFormatter.ISO_DATE_TIME);
            String formattedDate = "";

            // Check the control type
            if (item.Type.equals("DateTextBox")) {
                formattedDate = dateTime.format(DateTimeFormatter.ofPattern(item.DateFormat, Locale.ENGLISH));
            } else if (item.Type.equals("UmAlQuraDateTextBox")) {
                formattedDate = dateTime.format(DateTimeFormatter.ofPattern(item.DateFormat, new Locale("ar", "SA")));
            }

            returnValue = formattedDate;
        } catch (DateTimeParseException e) {
            return "";
        }

        return returnValue;
    }

    private static String getRichTextBox(Item curItem, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    private static String getLabelTag(Item item, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();
        String textAlign = "";
        String indent = "";
        String vcAlign = "";

        if (item.FlowDirection.equals("LeftToRight")) {
            textAlign = item.TextAlignment;

            if (item.getIndentUnit() > 0) {
                indent = String.format("padding-left: %spx", item.getIndentUnit());
            }
        } else {
            if (item.TextAlignment.equals("Left")) {
                textAlign = "Right";
            } else if (item.TextAlignment.equals("Right")) {
                textAlign = "Left";
            } else {
                textAlign = item.TextAlignment;
            }

            if (item.getIndentUnit() > 0) {
                indent = String.format("padding-right: %spx", item.getIndentUnit());
            }

        }

        String text = spaceTrim(item);

        vcAlign = getContentAlignmentChange(item.VerticalContentAlignment);

        if (isRootId(item.ParentID)) {
            sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; border-style: solid; color: #FFFFFF; border-color: #3DB5FF; border-width:0px 7px 0px 7px; text-align: center; vertical-align: middle; font-size: 16px; font-weight: Bold; direction:ltr; background-color: #3264CD; \">",
                    colSpan, rowSpan));
        } else {
            sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; border-style: solid; border-color: %s; border-width:%spx %spx %spx %spx; text-align:%s; vertical-align: %s; font-size: %spx; font-weight: %s; font-style:%s; direction:%s; %s; \">",
                    colSpan, rowSpan, "#000000", 0, 0, 0, 0, textAlign, vcAlign, 11, "Bold", item.FontStyle, item.FlowDirection.equals("RightToLeft") ? "rtl" : "ltr", indent));
        }

        sb.append(text);
        sb.append("</td>");

        return sb.toString();
    }

    private static String spaceTrim(Item item) {
        String text = "";
        if (StringUtils.isNotEmpty(item.Text)) {
            if (!item.Text.equals(item.Text.trim()) && item.HorizontalContentAlignment.equals("Left")) {
                text = item.Text.trim().replace("  ", " &nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br />");
            }
            else
                text = item.Text.trim().replace("  ", " &nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br />");
        }

        return text;
    }

    private static String getContentAlignmentChange(String contentAlign) {
        if (StringUtils.isEmpty(contentAlign)) return "top";
        String alignment = "";

        switch(contentAlign) {
            case "Top":
                alignment = "top";
                break;
            case "Center":
                alignment = "middle";
                break;
            case "Bottom":
                alignment = "bottom";
                break;
            default :
                alignment = "top";
                break;
        }

        return alignment;
    }

    private static String getTableTag(Item item, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();
        List<String> colWidthInfoList = new ArrayList<>();
        List<String> rowHeightInfoList = new ArrayList<>();

        if (StringUtils.isNotEmpty(item.GridInfo)) {
            colWidthInfoList = Arrays.asList(item.GridInfo.split(":") [0].split("\\|\\|"));
            rowHeightInfoList = Arrays.asList(item.GridInfo.split(":") [1].split("\\|\\|"));
        }

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height: inherit;\">\n", colSpan, rowSpan));
        sb.append(String.format("<table cellspacing=\"0\" cellpadding=\"0\" style=\"margin: 0px 0px 0px 0px; table-layout:fixed; width:%spx; border-collapse:collapse; \">", item.Width));
        sb.append("<colgroup>");

        for(String curW : colWidthInfoList) {
            sb.append(String.format("<col style=\"width: %spx\">", curW));
        }
        sb.append("</colgroup>");

        for(int i = 0; i < rowHeightInfoList.size(); i++) {
            sb.append(String.format("<tr style=\"height:%spx\">", rowHeightInfoList.get(i)));
            for(int j = 0; j < colWidthInfoList.size(); j++) {
                int rowIdx = i;
                int colIdx = j;
                Item curItem = item.items.stream()
                        .filter(it -> Integer.parseInt(it.RowNum) == rowIdx && Integer.parseInt(it.ColNum) == colIdx)
                        .findAny()
                        .orElse(null);
                if (Objects.nonNull(curItem)) {
                    int cSpan = Integer.parseInt(curItem.ColSpan);
                    int rSpan = Integer.parseInt(curItem.RowSpan);
                    if (curItem.Visibility.equals("Visible")) {
                        sb.append(getCellTag(curItem, cSpan, rSpan));
                    } else {
                        sb.append(getNoneTag(cSpan, rSpan, 0, 0, 0, 0));
                    }
                }

            }
            sb.append("</tr>");
        }

        sb.append("</table>\n");
        sb.append("</td>\n");

        return sb.toString();
    }

    private static String getNoneTag(int colSpan, int rowSpan, int borderTop, int borderBottom, int borderRight, int borderLeft) {
        return String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; border-style: solid; border-width:%spx %spx %spx %spx; \"></td>",
                colSpan, rowSpan, borderTop, borderRight, borderBottom, borderLeft);
    }

    private static List<RowGroup> getRowGroup(List<Item> items) {
        List<RowGroup> rowGroups = new ArrayList<>();

        items = items.stream()
             .sorted((a, b) -> {
                 int diff =  a.getTop() - b.getTop() > 0 ? 1
                         : a.getTop() - b.getTop() < 0 ? -1 : 0;
                 if (diff == 0) {
                     return a.getHeight() - b.getHeight() > 0 ? 1
                             : a.getHeight() - b.getHeight() < 0 ? -1 : 0;
                 }
                 return diff;
             })
             .collect(Collectors.toList());

        for(Item item : items) {
            int itemBottom = item.getYPoint();
            RowGroup rowgroup = rowGroups.stream()
                    .filter(w -> (item.getTop() >= w.getY1() && item.getTop() <= w.getY2()) ||
                            (itemBottom >= w.getY1() && itemBottom <= w.getY2()))
                    .findFirst()
                    .orElse(null);

            if (rowgroup != null) {
                rowgroup.setY1(Math.min(rowgroup.getY1(), item.getTop()));
                rowgroup.setY2(Math.max(rowgroup.getY2(), itemBottom));
                rowgroup.getItems().add(item);
                rowgroup.setPoints(item);
            } else {
                rowGroups.add(new RowGroup(item));
            }
        }

        return rowGroups;
    }

    private static Optional<DocumentFormat> convertXmlData(String input) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            DocumentFormat xmlData = xmlMapper.readValue(input, DocumentFormat.class);
            return Optional.ofNullable(xmlData);
        } catch (Exception e) {
            log.error("DocumentFormat Error !! >>> {}", e.getMessage());
            return Optional.empty();
        }
    }

    private static boolean isRootId(String id) {
        return "-1000".equals(id);
    }

    private static boolean isServiceControl(String type) {
        List<String> controls = List.of("SearchCheckButton", "SearchButtonControl", "PopupSearchButton");
        return controls.contains(type);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class RowGroup {
        private int y1;
        private int y2;
        private List<Integer> YPoints;
        private List<Integer> XPoints;
        private List<Item> items;

        public RowGroup(Item item) {
            this.y1 = item.getTop();
            this.y2 = item.getYPoint();
            this.YPoints = new ArrayList<>();
            this.XPoints = new ArrayList<>();
            this.XPoints.add(0);
            this.items = new ArrayList<>();
            this.items.add(item);
            setPoints(item);
        }

        private void setPoints(Item item) {
            int left = item.getLeft();
            int width = item.getWidth();
            int top = item.getTop();
            int yPoint = item.getYPoint();

            if (!XPoints.contains(left))
                XPoints.add(left);
            if (!XPoints.contains(left + width))
                XPoints.add(left + width);
            if (!YPoints.contains(top))
                YPoints.add(top);
            if (!YPoints.contains(yPoint)) {
                YPoints.add(yPoint);
            }
        }

    }
}
