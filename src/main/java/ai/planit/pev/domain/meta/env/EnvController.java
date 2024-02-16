package ai.planit.pev.domain.meta.env;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/meta/env")
@RequiredArgsConstructor
public class EnvController {

    @Value("${spring.profiles.active:local}")
    private final List<String> profiles;

    @GetMapping("profile")
    public ResponseEntity<?> getServerActiveProfile() {
        String profile;

        if (profiles.contains("local")) {
            profile = "local";
        } else if (profiles.contains("prod")) {
            profile = "prod";
        } else {
            profile = "unknown";
        }

        Map<String, String> map = new HashMap<>();
        map.put("profile", profile);

        return ResponseEntity.ok().body(map);
    }
}
