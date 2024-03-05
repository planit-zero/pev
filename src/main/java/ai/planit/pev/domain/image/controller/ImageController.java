package ai.planit.pev.domain.image.controller;

import ai.planit.pev.domain.image.dto.ImageDTO;
import ai.planit.pev.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("")
    public ResponseEntity<?> getMaskedImage(@RequestBody ImageDTO imageDTO) {
        return ResponseEntity.ok().body(imageService.getMaskedImage(imageDTO));
    }
}
