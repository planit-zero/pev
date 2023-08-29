package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordDataRequestDTO {
    private List<DetailResponseDTO> targets;
}
