package ai.planit.pev.domain.image.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private String url;
    private boolean refresh;
}
