package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@XmlRootElement(name = "DocumentFormat")
public class RecordSectionDTO {
    @Setter
    private int mdfmSctnSeq;

    @XmlAttribute(name = "SectionThemeType")
    private String sectionThemeType;

    @XmlAttribute(name = "Width")
    private String width;

    @XmlAttribute(name = "Height")
    private String height;

    @XmlElement(name = "Item")
    private List<RecordItemDTO> items;
}
