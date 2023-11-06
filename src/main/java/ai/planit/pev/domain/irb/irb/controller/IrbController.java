package ai.planit.pev.domain.irb.irb.controller;

import ai.planit.pev.domain.irb.irb.service.IrbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/irb/")
@RequiredArgsConstructor
public class IrbController {
    private final IrbService irbService;

    @GetMapping("list")
    public ResponseEntity<?> getIrbList(@RequestParam String stfNo) {
        return ResponseEntity.ok().body(irbService.getIrbList(stfNo));
    }
}
