package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordFormInfoResponseDTO {
    private int mdrcId;
    private int mdrcFomSeq;
    private String mdfmClsCd;
    private String itemType;
    private String writingDate;
    private String writingDateTime;
    private String writingDeptNm;
    private String medDeptNm;
    private String writerNm;
}
