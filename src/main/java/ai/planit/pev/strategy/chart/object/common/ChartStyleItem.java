package ai.planit.pev.strategy.chart.object.common;

import ai.planit.pev.domain.ods.form.dto.FormStyleItem;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

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

    // Value
    @Setter
    private String value;

    // Children
    @XmlElement(name = "Item")
    private List<FormStyleItem> children;
}
