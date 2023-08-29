package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordXmlFormResponseDTO {
    private int mdfmId;
    private int mdfmFomSeq;
    private int mdfmSctnSeq;
    private String sctnDgnMetaLdat;
}
