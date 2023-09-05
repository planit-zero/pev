package ai.planit.pev.domain.form.controller;

import ai.planit.pev.domain.form.dto.FormContentRequest;
import ai.planit.pev.domain.form.dto.FormContentResponse;
import ai.planit.pev.domain.form.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/form")
public class FormController {
    private final FormService formService;

    @PostMapping("content")
    public ResponseEntity<FormContentResponse> getFormContent(@RequestBody FormContentRequest formContentRequest) {
        return ResponseEntity.ok().body(formService.getFormContent(formContentRequest));
    }
}
