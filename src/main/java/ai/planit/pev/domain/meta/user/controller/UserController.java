package ai.planit.pev.domain.meta.user.controller;

import ai.planit.pev.domain.meta.user.dto.UserLogin;
import ai.planit.pev.domain.meta.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("account")
    public ResponseEntity<?> checkAccountAndSendVerificationCode(@RequestBody UserLogin userLogin) {
        userService.checkAccountAndSendVerificationCode(userLogin);
        return ResponseEntity.ok().build();
    }

    @PostMapping("verification")
    public ResponseEntity<?> checkVerificationCodeAndLogin(HttpSession session, @RequestBody UserLogin userLogin) {
        userService.checkVerificationCodeAndLogin(session, userLogin);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<?> getIdpLoginUser(HttpSession session, @RequestParam(required = false) String token) {
        return ResponseEntity.ok().body(userService.getIdpLoginUser(session, token));
    }

    @PostMapping("sign-out")
    public ResponseEntity<?> signOut(HttpSession session) {
        userService.signOut(session);
        return ResponseEntity.ok().build();
    }

    @GetMapping("token")
    public ResponseEntity<?> getTokenInSession(HttpSession session) {
        String token = (String) session.getAttribute("pev-token");

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return ResponseEntity.ok().body(map);
    }
}
