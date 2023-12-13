package ai.planit.pev.domain.meta.record.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetaRecord {
    private String id;
    private String parentId;
    private String name;
    private int displaySeq;
}
