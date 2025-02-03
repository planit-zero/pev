package ai.planit.pev.strategy.chart.object.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Optional;

@Getter
public class ChartStyleItem {
    // Default
    @XmlAttribute(name = "ID")
    private String id;

    @XmlAttribute(name = "Parent ID")
    private String parentId;

    @XmlAttribute(name = "Type")
    private String type;

    @XmlAttribute(name = "IsArabic")
    private String isArabic;

    @XmlAttribute(name = "IsReadOnly")
    private String isReadOnly;

    @XmlAttribute(name = "IsSuffix")
    private String isSuffix;

    @XmlAttribute(name = "Visibility")
    private String visibility;

    @XmlAttribute(name = "Printable")
    private String printable;

    // Text
    @XmlAttribute(name = "Text")
    private String text;

    @XmlAttribute(name = "TextWrapping")
    private String textWrapping;

    @XmlAttribute(name = "FlowDirection")
    private String flowDirection;

    // Position
    @XmlAttribute(name = "ZIndex")
    private String zIndex;

    @XmlAttribute(name = "Top")
    private String top;

    @XmlAttribute(name = "Left")
    private String left;

    @XmlAttribute(name = "AbsoluteTop")
    private String absoluteTop;

    @XmlAttribute(name = "AbsoluteLeft")
    private String absoluteLeft;

    // Size
    @XmlAttribute(name = "Width")
    private String width;

    @XmlAttribute(name = "MinWidth")
    private String minWidth;

    @XmlAttribute(name = "MaxWidth")
    private String maxWidth;

    @XmlAttribute(name = "Height")
    private String height;

    @XmlAttribute(name = "MinHeight")
    private String minHeight;

    @XmlAttribute(name = "IsAutoHeight")
    private String isAutoHeight;

    // Font
    @XmlAttribute(name = "FontFamily")
    private String fontFamily;

    @XmlAttribute(name = "FontSize")
    private String fontSize;

    @XmlAttribute(name = "FontStyle")
    private String fontStyle;

    @XmlAttribute(name = "FontWeight")
    private String fontWeight;

    // Alignment
    @XmlAttribute(name = "TextAlignment")
    private String textAlignment;

    @XmlAttribute(name = "VContentAlignment")
    private String vContentAlignment;

    @XmlAttribute(name = "HContentAlignment")
    private String hContentAlignment;

    // Border
    @XmlAttribute(name = "BorderThickness")
    private String borderThickness;

    @XmlAttribute(name = "BorderBrush")
    private String borderBrush;

    // Color
    @XmlAttribute(name = "Foreground")
    private String foreGround;

    @XmlAttribute(name = "Background")
    private String background;

    // Table
    @XmlAttribute(name = "TableDepth")
    private String tableDepth;

    @XmlAttribute(name = "ColNum")
    private String colNum;

    @XmlAttribute(name = "RowNum")
    private String rowNum;

    @XmlAttribute(name = "TotalColNum")
    private String totalColNum;

    @XmlAttribute(name = "TotalRowNum")
    private String totalRowNum;

    @XmlAttribute(name = "ColSpan")
    private String colSpan;

    @XmlAttribute(name = "RowSpan")
    private String rowSpan;

    // Unknown
    @XmlAttribute(name = "ArchDepth")
    private String archDepth;

    @XmlAttribute(name = "IndentUnit")
    private String indentUnit;

    @XmlAttribute(name = "OverlapGroupID")
    private String overlapGroupId;

    @XmlAttribute(name = "VerticalInterval")
    private String verticalInterval;

    @XmlAttribute(name = "DefaultValue")
    private String defaultValue;

    // Value
    @Setter
    private String value;

    // Children
    @XmlElement(name = "Item")
    private List<ChartStyleItem> children;

    public void setPosition(String top, String left) {
        this.top = top;
        this.left = left;
    }

    public int getIndentUnitInt() {
        return Integer.parseInt(indentUnit);
    }

    public int getTopInt() {
        return StringUtils.isNotEmpty(top) ? Integer.valueOf(top) : 0;
    }

    public int getLeftInt() {
        return StringUtils.isNotEmpty(left) ? Integer.valueOf(left) : 0;
    }

    public int getWidthInt() {
        return Integer.valueOf(width);
    }

    public int getHeightInt() {
        return StringUtils.isEmpty(height) || "NaN".equals(height) ? getMinHeightInt(): Integer.valueOf(height);
    }

    public int getMinHeightInt() {
        return Integer.valueOf(minHeight);
    }

    public int getYPoint() {
        return getTopInt() + getHeightInt();
    }

    public int getFontSize() {
        return Integer.parseInt(fontSize);
    }

    public String getValue() {
        return StringUtils.isNotEmpty(value) ? value : Optional.ofNullable(defaultValue).orElse("");
    }
}
