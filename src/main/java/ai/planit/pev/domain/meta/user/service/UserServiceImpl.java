package ai.planit.pev.domain.meta.user.service;

import ai.planit.idp.sdk.model.IdpLoginUser;
import ai.planit.pev.domain.meta.user.dto.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@org.springframework.context.annotation.Profile("!idp")
public class UserServiceImpl implements UserService {

    @Override
    public void checkAccountAndSendVerificationCode(UserLogin userLogin) {
        // Mock implementation for dev environment
        // No actual IDP call
    }

    @Override
    public void checkVerificationCodeAndLogin(HttpSession session, UserLogin userLogin) {
        // Mock implementation for dev environment
        // Set mock token in session
        session.setAttribute("pev-token", "mock-token-" + userLogin.getStfNo());
    }

    @Override
    public IdpLoginUser getIdpLoginUser(HttpSession session, String token) {
        // Mock implementation for dev environment
        // Return mock user
        IdpLoginUser mockUser = new IdpLoginUser();
        // Set basic mock data if needed
        return mockUser;
    }

    @Override
    public void signOut(HttpSession session) {
        // Mock implementation for dev environment
        // Clear session
        session.removeAttribute("pev-token");
        session.removeAttribute("pev-user");
    }
}
