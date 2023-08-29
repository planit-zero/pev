package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordDataResponseDTO {
    private int mdfmId;
    private int mdfmFomSeq;
    private int mdrcId;
    private int mdrcFomSeq;
    private List<RecordSectionDTO> sections;
}
