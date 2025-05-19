package ai.planit.pev.utility;

import ai.planit.pev.strategy.chart.object.common.ChartDocumentValue;
import ai.planit.pev.strategy.chart.object.common.ChartStyleItem;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class PevDocumentRenderUtil {
    public static String render(List<ChartStyleItem> items, List<ChartDocumentValue> values) {
        StringBuilder sb = new StringBuilder();

        for(ChartStyleItem item : items) {
            if (isServiceControl(item.getType())) {
                item.setVisibilityStr("Collapsed");
            }
        }

        List<RowGroup> rowGroups = getRowGroup(items.stream()
                .filter(item -> !isRootId(item.getId()))
                .collect(Collectors.toList()));

        if (!rowGroups.isEmpty()) {
            sb.append(String.format("<div style=\"height:%spx\"></div>\n", rowGroups.get(0).y1));
        }

        renderTable(rowGroups, values, sb);


        return sb.toString();
    }

    private static void renderTable(List<RowGroup> rowGroups, List<ChartDocumentValue> values, StringBuilder sb) {
        int prevBottom = 0;

        for(RowGroup rowGroup : rowGroups) {
            List<ChartStyleItem> collapseList = rowGroup.items.stream()
                    .filter(item -> item.getVisibilityStr().equals("Collapsed")).collect(Collectors.toList());
            if (!collapseList.isEmpty()) {
                List<ChartStyleItem> visibleList = rowGroup.items.stream()
                        .filter(item -> !item.getVisibilityStr().equals("Collapsed"))
                        .collect(Collectors.toList());
                List<RowGroup> subRowGroups = getRowGroup(visibleList);
                if (!subRowGroups.isEmpty()) {
                    renderTable(subRowGroups, values, sb);
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

                sb.append("</colgroup>\n");
                sb.append("<tbody>\n");

                for(int i = 0; i < sortedY.size() - 1; i++) {
                    int minHeight = sortedY.get(i + 1) - sortedY.get(i);
                    sb.append(String.format("<tr style=\"height:%spx\">\n", minHeight));
                    for (int j = 0; j < sortedX.size() - 1; j++) {
                        int startRowIndex = i;
                        int startColIndex = j;
                        Optional<ChartStyleItem> itemOpt = rowGroup.items.stream()
                                .filter(w -> w.getTopInt() == sortedY.get(startRowIndex) && w.getLeftInt() == sortedX.get(startColIndex))
                                .findFirst();

                        if (itemOpt.isPresent()) {
                            ChartStyleItem curItem = itemOpt.get();
                            int endRowIndex = IntStream.range(0, sortedY.size())
                                    .filter(index -> sortedY.get(index) == curItem.getYPoint())
                                    .findFirst()
                                    .orElse(-1);
                            int endColIndex =  IntStream.range(0, sortedX.size())
                                    .filter(index -> sortedX.get(index) == curItem.getLeftInt() + curItem.getWidthInt())
                                    .findFirst()
                                    .orElse(-1);
                            int colSpan = endColIndex - startColIndex;
                            int rowSpan = endRowIndex - startRowIndex;

                            if (curItem.getVisibilityStr().equals("Visible")) {
                                sb.append(getCellTag(curItem, values, colSpan, rowSpan));

                            } else {
                                sb.append(getNoneTag(curItem, colSpan, rowSpan));
                            }
                        } else {
                            int left = sortedX.get(j);
                            int top = sortedY.get(i);
                            int width = sortedX.get(j + 1) - sortedX.get(j);
                            int height = sortedY.get(i + 1) - sortedY.get(i);

                            ChartStyleItem rItem = rowGroup.items.stream()
                                    .filter(r -> {
                                        int rLeft = r.getLeftInt();
                                        int rTop = r.getTopInt();
                                        int rWidth = r.getWidthInt();
                                        int rHeight = r.getHeightInt();

                                        return (rLeft <= left) &&
                                                (rTop <= top) &&
                                                (rLeft + rWidth >= left + width) &&
                                                (rTop + rHeight >= top + height);
                                    })
                                    .findFirst()
                                    .orElse(null);

                            if (Objects.isNull(rItem)) {
                                sb.append("<td style=\"min-height: inherit;\"></td>");
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

    private static String getCellTag(ChartStyleItem curItem, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        switch (curItem.getType()) {
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
                return getNoneTag(curItem, colSpan, rowSpan);
            case "Table":
                return getTableTag(curItem, values, colSpan, rowSpan);
            case "Label":
                return getLabelTag(curItem, values, colSpan, rowSpan);
            case "RichTextBox":
//                return getRichTextBox(curItem, values, colSpan, rowSpan);
            case "DateTextBox":
            case "ExprTextBox":
            case "NumericTextBox":
            case "TextBox":
            case "UmAlQuraDateTextBox":
                return getTextBoxTag(curItem, values, colSpan, rowSpan);
            case "CheckBox":
                return getCheckBoxTag(curItem, values, colSpan, rowSpan);
            case "RadioButton":
                return getRadioButtonTag(curItem, values, colSpan, rowSpan);
            case "Image":
                return getImageTag(curItem, values, colSpan, rowSpan);
            case "ComboBox":
                return getComboBoxTag(curItem, values, colSpan, rowSpan);
            case "DataGrid":
                return getDataGridTag(curItem, values, colSpan, rowSpan);
            case "ImageCheckBox":
                return getImageCheckBoxTag(curItem, values, colSpan, rowSpan);
            case "Repeater":
                return getRepeaterTag(curItem, values, colSpan, rowSpan);
            default:
                return "";

        }
    }

    private static String getRepeaterTag(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();
        List<ChartStyleItem> items = new ArrayList<>();

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height: inherit;\" >", colSpan, rowSpan));

        int currentTop = 0;
        for(ChartStyleItem i : item.getChildren()) {
            for(ChartStyleItem child : i.getChildren()) {
                child.setTopValue(String.valueOf(child.getTopInt() + currentTop));
                items.add(child);
            }
            currentTop += items.stream().map(ii -> ii.getTopInt() + ii.getHeightInt()).max(Comparator.comparing(ii -> ii)).orElse(0) + 5;
        }

        sb.append(render(items, values));

        sb.append("</td>");

        return sb.toString();
    }

    private static String getImageCheckBoxTag(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        String text = spaceTrim(item.getText());
        String vcAlign = getContentAlignmentChange(item.getVContentAlignment());
        String value = getValue(item, values);
        String imageTag = "";

        if (StringUtils.isNotEmpty(value)) {
            imageTag = String.format("<img src=\"%s\" style=\"width: 100%;\" />", value);
        }

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; border-color:#B3B3B3; border-width: %s; border-style: solid; vertical-align:%s; font-size: %spx; font-weight: %s; font-style: %s; \"",
                colSpan, rowSpan, getBorderWidth(item), vcAlign, item.getFontSize(), item.getFontWeight(), item.getFontStyle()));
        sb.append(String.format("<p style=\"margin: 0px 0px 0px 0px;\"><input disabled type=\"checkbox\" %s style=\"margin-left: 1px;margin-right: 1px; margin-bottom: -2px;\"/>", StringUtils.isNotEmpty(imageTag) ? "checked" : ""));
        sb.append(text);
        sb.append("</p>");
        sb.append(imageTag);
        sb.append("</td>");

        return sb.toString();
    }

    private static String getDataGridTag(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        if (!StringUtils.isEmpty(item.getColumnSource())) {
            List<ColumnInfo> columns = new Gson().fromJson(item.getColumnSource().replaceAll("&quot;", "\""), new TypeToken<List<ColumnInfo>>() {}.getType());
            sb.append("<td style=\"min-height: inherit; vertical-align:top; font-size:12px; font-weight:normal; \" >\n");
            sb.append(String.format("<table border='1' cellpadding='0' cellspacing='0' class=\"gridtable\" width='%spx' style=\"border-collapse:collapse; \" >\n", item.getWidth()));
            sb.append("<tr height='20px'>\n");

            for(ColumnInfo column : columns) {
                int width = column.getIsWidthStar().equals("true") ? Integer.parseInt(column.getWidth()) * 100 : Integer.parseInt(column.getWidth());
                sb.append(String.format("<th align='center' valign='middle' width='%spx' style=\"background:#D3D3D3; font-size:12px; font-weight:bold; \" >%s</th>\n", width, column.getColumnDisplayName()));
            }

            try(InputStream inputStream = new ByteArrayInputStream(getValue(item, values).getBytes())) {
                Document document = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder()
                        .parse(inputStream);
                document.getDocumentElement().normalize();

                Node doc = document.getElementsByTagName("DocumentElement").item(0);
                int colCount = 0;

                for (int i = 0; i < doc.getChildNodes().getLength(); i++) {
                    Node gridNode = doc.getChildNodes().item(i);
                    if (gridNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element gridElement = (Element) gridNode;
                        NodeList children = gridElement.getChildNodes();

                        sb.append("<tr height='20px'>\n");
                        for (int j = 0; j < children.getLength(); j++) {
                            Node child = children.item(j);
                            if (child.getNodeType() == Node.ELEMENT_NODE) {
                                Element childElement = (Element) child;
                                String value = childElement.getTextContent(); // 태그값
                                sb.append(String.format("<td align='center' valign='middle'>%s</td>", value));
                                if (i == 0) {
                                    colCount++;
                                }
                            }
                        }
                        sb.append("</tr>\n");
                    }
                }

                sb.append("<tr>\n");
                for(int i = 0; i < colCount; i++) {
                    sb.append("<td align='center' valign='middle'></td>\n");
                }
                sb.append("</tr>\n");
            } catch (Exception e) {
                log.error(e.getMessage());
            }


            sb.append("</tr>");

            sb.append("</table>");
            sb.append("</td>");
        }

        return sb.toString();
    }

    private static String getComboBoxTag(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        String textAlign = item.getHorizontalContentAlignment();
        if (item.getHorizontalContentAlignment().equals("Stretch")) {
            textAlign = "Justify";
        }

        String text = spaceTrim(getValue(item, values));
        String vcAlign = getContentAlignmentChange(item.getVerticalContentAlignment());

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; border-style: solid; border-color: #B3B3B3; border-width: %s; padding:0px; text-align:%s; vertical-align: %s; font-size: %spx; font-weight: %s; font-style:%s; \">",
                colSpan, rowSpan, getBorderWidth(item),textAlign, vcAlign, item.getFontSize(), item.getFontWeight(), item.getFontStyle()));
        sb.append(text);
        sb.append("</td>");

        return sb.toString();
    }

    private static String getImageTag(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        String imageSource = getImagePath(getValue(item, values));

        if (StringUtils.isEmpty(imageSource)) {
            sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; border-style: solid; border-color: #B3B3B3; border-width: 0px 0px 0px 0px; width:%spx; \"></td>",
                    colSpan, rowSpan,item.getWidth()));
        } else {
            sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; border-style: solid; border-color: #B3B3B3; border-width: %s; width:%spx; \">",
                    colSpan, rowSpan, getBorderWidth(item), item.getWidth()));
            sb.append(String.format("<img src=\"%s\" align=\"absmiddle\" style=\"padding: 0px; width: 100%%;\"></img>", imageSource));
            sb.append("</td>");
        }

        return sb.toString();
    }

    private static String getImagePath(String value) {
        if (StringUtils.isEmpty(value)) return "";
        return value;
    }

    private static String getRadioButtonTag(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        String textAlign = "";
        String indent = "";

        if (item.getFlowDirection().equals("LeftToRight")) {
            textAlign = item.getHorizontalContentAlignment();
            if (Integer.parseInt(item.getIndentUnit()) > 0) {
                indent = String.format("padding-left: %spx;", item.getIndentUnit());
            }
        } else {
            if (item.getHorizontalContentAlignment().equals("Left")) {
                textAlign = "Right";
            } else if (item.getHorizontalContentAlignment().equals("Right")) {
                textAlign = "Left";
            } else {
                textAlign = item.getHorizontalContentAlignment();
            }

            if (Integer.parseInt(item.getIndentUnit()) > 0) {
                indent = String.format("padding-right: %spx;", item.getIndentUnit());
            }
        }

        String text = spaceTrim(item.getText());
        String vcAlign = getContentAlignmentChange(item.getVerticalContentAlignment());

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; vertical-align:%s; font-size:%s; font-weight: %s; text-align:%s; direction:%s; border-color: #B3B3B3; border-style: solid; border-width:%s;\" >",
                colSpan, rowSpan, vcAlign, item.getFontSizeInt(), item.getFontWeight(), textAlign, item.getFlowDirection().equals("RightToLeft") ? "rtl" : "ltr", getBorderWidth(item)));
        sb.append(String.format("<p style=\"margin: 0px 0px 0px 0px; %s \">", indent));
        sb.append(String.format("<input type=\"radio\" %s style=\"margin-left: 1px;margin-right: 4px; margin-bottom: -2px; pointer-events: none; \" />", getValue(item, values).equals("1") || getValue(item, values).equals("checked") ? "checked" : ""));
        sb.append(String.format("<span>%s</span>", text));
        sb.append("</p>");
        sb.append("</td>");

        return sb.toString();
    }

    private static String getCheckBoxTag(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        String textAlign = "";
        String indent = "";

        if (item.getFlowDirection().equals("LeftToRight")) {
            textAlign = item.getHorizontalContentAlignment();
            if (Integer.parseInt(item.getIndentUnit()) > 0) {
                indent = String.format("padding-left: %spx;", item.getIndentUnit());
            }
        } else {
            if (item.getHorizontalContentAlignment().equals("Left")) {
                textAlign = "Right";
            } else if (item.getHorizontalContentAlignment().equals("Right")) {
                textAlign = "Left";
            } else {
                textAlign = item.getHorizontalContentAlignment();
            }

            if (Integer.parseInt(item.getIndentUnit()) > 0) {
                indent = String.format("padding-right: %spx;", item.getIndentUnit());
            }
        }

        String text = spaceTrim(item.getText());
        String vcAlign = getContentAlignmentChange(item.getVerticalContentAlignment());

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; vertical-align:%s; font-size:%s; font-weight: %s; text-align:%s; direction:%s; border-color: #B3B3B3; border-style: solid; border-width:%s;\" >",
                colSpan, rowSpan, vcAlign, item.getFontSize(), item.getFontWeight(), textAlign, item.getFlowDirection().equals("RightToLeft") ? "rtl" : "ltr", getBorderWidth(item)));
        sb.append(String.format("<p style=\"margin: 0px 0px 0px 0px; %s \">", indent));
        sb.append(String.format("<input type=\"checkbox\" %s style=\"margin-left: 1px;margin-right: 4px; margin-bottom: -2px; pointer-events: none;\" />", getValue(item, values).equals("1") || getValue(item, values).equals("checked") ? "checked" : ""));
        sb.append(String.format("<span>%s</span>", text));
        sb.append("</p>");
        sb.append("</td>");

        return sb.toString();
    }

    private static String getTextBoxTag(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        String text = "";
        String vcAlign = "";

        if (item.getType().equals("DateTextBox") || item.getType().equals("UmAlQuraDateTextBox")) {
            text = getDateTimeToStringFormat(item, values);
        } else {
            text = spaceTrim(getValue(item, values));

            if(item.getType().equals("ExprTextBox") || item.getType().equals("TextBox")) {
                if (!StringUtils.isEmpty(text) && !StringUtils.isEmpty(item.getSuffix()))
                    text = text + item.getSuffix();
            }
        }

        vcAlign = getContentAlignmentChange(item.getVerticalContentAlignment());

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; border-color: #B3B3B3; background-color: #ffffff; word-wrap: break-word;  border-style: solid; border-width: %s; padding:0px; text-align:%s; vertical-align: %s; font-size: %spx; font-weight: %s; font-style:%s\">",
                colSpan, rowSpan, getBorderWidth(item), item.getTextAlignment(), vcAlign, item.getFontSizeInt(), item.getFontWeight(), item.getFontFamily()));
        sb.append(text);
        sb.append("</td>");


        return sb.toString();
    }

    public static String getDateTimeToStringFormat(ChartStyleItem item, List<ChartDocumentValue> values) {
        String returnValue = getValue(item, values);

        if (StringUtils.isEmpty(returnValue)) {
            return returnValue;
        }

        try {
            LocalDate date = LocalDate.parse(returnValue, DateTimeFormatter.ISO_LOCAL_DATE); // LocalDate로 변환
            LocalDateTime dateTime = date.atStartOfDay(); // 00:00:00 시간 추가

            return formatDate(item, dateTime); // 포맷 적용
        } catch (Exception e) {
            return returnValue;
        }
    }

    private static String formatDate(ChartStyleItem item, LocalDateTime dateTime) {
        DateTimeFormatter formatter = null;

        if (item.getType().equals("DateTextBox")) {
            formatter = DateTimeFormatter.ofPattern(item.getDateFormat(), Locale.ENGLISH);
        } else if (item.getType().equals("UmAlQuraDateTextBox")) {
            formatter = DateTimeFormatter.ofPattern(item.getDateFormat(), new Locale("ar", "SA"));
        }

        return dateTime.format(formatter);
    }

    private static String getRichTextBox(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    private static String getLabelTag(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();
        String textAlign = "";
        String indent = "";
        String vcAlign = "";

        if (item.getFlowDirection().equals("LeftToRight")) {
            textAlign = item.getTextAlignment();

            if (item.getIndentUnitInt() > 0) {
                indent = String.format("padding-left: %spx", item.getIndentUnit());
            }
        } else {
            if (item.getTextAlignment().equals("Left")) {
                textAlign = "Right";
            } else if (item.getTextAlignment().equals("Right")) {
                textAlign = "Left";
            } else {
                textAlign = item.getTextAlignment();
            }

            if (item.getIndentUnitInt() > 0) {
                indent = String.format("padding-right: %spx", item.getIndentUnit());
            }

        }

        String text = spaceTrim(item.getText());

        vcAlign = getContentAlignmentChange(item.getVerticalContentAlignment());

        if (isRootId(item.getParentId())) {
            sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; border-style: solid; color: #FFFFFF; border-color: #3DB5FF; border-width:0px 7px 0px 7px; text-align: center; vertical-align: middle; font-size: 16px; font-weight: Bold; direction:ltr; background-color: #3264CD; \">",
                    colSpan, rowSpan));
        } else {
            sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; word-wrap: break-word; border-color: #B3B3B3; border-style: solid; border-width:%s; text-align:%s; vertical-align: %s; font-size: %spx; font-weight: %s; font-style:%s; direction:%s; %s; \">",
                    colSpan, rowSpan, getBorderWidth(item), textAlign, vcAlign, 11, "Bold", item.getFontSizeInt(), item.getFlowDirection().equals("RightToLeft") ? "rtl" : "ltr", indent));
        }

        sb.append(text);
        sb.append("</td>");

        return sb.toString();
    }

    private static String spaceTrim(String text) {
        return StringUtils.isEmpty(text) ? "" : text.trim().replace("  ", " &nbsp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("&lt;em&gt;", "<em>")
                .replace("&lt;/em&gt;", "</em>")
                .replace("\n", "<br />");
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

    private static String getTableTag(ChartStyleItem item, List<ChartDocumentValue> values, int colSpan, int rowSpan) {
        StringBuilder sb = new StringBuilder();
        List<String> colWidthInfoList = new ArrayList<>();
        List<String> rowHeightInfoList = new ArrayList<>();

        if (StringUtils.isNotEmpty(item.getGridInfo())) {
            colWidthInfoList = Arrays.asList(item.getGridInfo().split(":") [0].split("\\|\\|"));
            rowHeightInfoList = Arrays.asList(item.getGridInfo().split(":") [1].split("\\|\\|"));
        }

        sb.append(String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height: inherit; \">\n", colSpan, rowSpan));
        sb.append(String.format("<table cellspacing=\"0\" cellpadding=\"0\" style=\"margin: 0px 0px 0px 0px; table-layout:fixed; width:%spx; border-collapse:collapse; \">", item.getWidth()));
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
                ChartStyleItem curItem = item.getChildren().stream()
                        .filter(it -> Integer.parseInt(it.getRowNum()) == rowIdx && Integer.parseInt(it.getColNum()) == colIdx)
                        .findAny()
                        .orElse(null);
                if (Objects.nonNull(curItem)) {
                    int cSpan = Integer.parseInt(curItem.getColSpan());
                    int rSpan = Integer.parseInt(curItem.getRowSpan());
                    if (curItem.getVisibilityStr().equals("Visible")) {
                        sb.append(getCellTag(curItem, values, cSpan, rSpan));
                    } else {
                        sb.append(getNoneTag(curItem, colSpan, rowSpan));
                    }
                }

            }
            sb.append("</tr>");
        }

        sb.append("</table>\n");
        sb.append("</td>\n");

        return sb.toString();
    }

    private static String getNoneTag(ChartStyleItem item, int colSpan, int rowSpan) {
        return String.format("<td colspan=\"%s\" rowspan=\"%s\" style=\"min-height:inherit; border-color: #B3B3B3; word-wrap: break-word; border-style: solid; border-width:%s; \"></td>",
                colSpan, rowSpan, getBorderWidth(item));
    }

    private static String getBorderWidth(ChartStyleItem item) {
        if (StringUtils.isEmpty(item.getBorderThickness())) {
            return String.format("%spx %spx %spx %spx", 1, 1, 1, 1);
        } else {
            String[] border = item.getBorderThickness().split(",");

            if (item.getFlowDirection().equals("LeftToRight")) {
                return String.format("%spx %spx %spx %spx", border[1], border[2], border[3], border[0]);

            } else {
                return String.format("%spx %spx %spx %spx", border[1], border[0], border[3], border[2]);
            }
        }
    }

    private static List<RowGroup> getRowGroup(List<ChartStyleItem> items) {
        List<RowGroup> rowGroups = new ArrayList<>();

        items = items.stream()
             .sorted((a, b) -> {
                 int diff = Integer.compare(a.getTopInt() - b.getTopInt(), 0);
                 if (diff == 0) {
                     return Integer.compare(a.getHeightInt() - b.getHeightInt(), 0);
                 }
                 return diff;
             })
             .collect(Collectors.toList());

        for(ChartStyleItem item : items) {
            int itemBottom = item.getYPoint();
            RowGroup rowgroup = rowGroups.stream()
                    .filter(w -> (item.getTopInt() >= w.getY1() && item.getTopInt() <= w.getY2()) ||
                            (itemBottom >= w.getY1() && itemBottom <= w.getY2()))
                    .findFirst()
                    .orElse(null);

            if (rowgroup != null) {
                rowgroup.setY1(Math.min(rowgroup.getY1(), item.getTopInt()));
                rowgroup.setY2(Math.max(rowgroup.getY2(), itemBottom));
                rowgroup.getItems().add(item);
                rowgroup.setPoints(item);
            } else {
                rowGroups.add(new RowGroup(item));
            }
        }

        return rowGroups;
    }

    private static boolean isRootId(String id) {
        return "-1000".equals(id);
    }

    private static boolean isServiceControl(String type) {
        List<String> controls = List.of("SearchCheckButton", "SearchButtonControl", "PopupSearchButton");
        return controls.contains(type);
    }

    private static String getValue(ChartStyleItem item, List<ChartDocumentValue> values) {
        return Optional.ofNullable(values.stream()
                .filter(v -> !v.isUsed() && (item.getId().equals(v.getMdfmCpemNo()) || (item.getType().equals("ComboBox") && (item.getParentId() + ".0").equals(v.getParentId()))))
                .map(v -> {
                    v.setUsed(true);
                    return v.getContent();
                })
                .findFirst().orElse(item.getDefaultValue()))
                .orElse("");
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ColumnInfo {
        @SerializedName("IsWidthStar")
        private String isWidthStar;
        @SerializedName("Width")
        private String width;
        @SerializedName("ColumnDisplayName")
        private String columnDisplayName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class RowGroup {
        private int y1;
        private int y2;
        private List<Integer> YPoints;
        private List<Integer> XPoints;
        private List<ChartStyleItem> items;

        public RowGroup(ChartStyleItem item) {
            this.y1 = item.getTopInt();
            this.y2 = item.getYPoint();
            this.YPoints = new ArrayList<>();
            this.XPoints = new ArrayList<>();
            this.XPoints.add(0);
            this.items = new ArrayList<>();
            this.items.add(item);
            setPoints(item);
        }

        private void setPoints(ChartStyleItem item) {
            int left = item.getLeftInt();
            int width = item.getWidthInt();
            int top = item.getTopInt();
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
