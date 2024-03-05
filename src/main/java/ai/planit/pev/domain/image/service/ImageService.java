package ai.planit.pev.domain.image.service;

import ai.planit.pev.domain.image.dto.ImageDTO;

public interface ImageService {
    ImageDTO getMaskedImage(ImageDTO imageDTO);
}
