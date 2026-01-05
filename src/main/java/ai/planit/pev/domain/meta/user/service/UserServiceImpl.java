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
        
        // Set mock data - extract staff number from session token if available
        String tokenInSession = (String) session.getAttribute("pev-token");
        String stfNo = "12345"; // Default mock staff number
        
        if (tokenInSession != null && tokenInSession.startsWith("mock-token-")) {
            stfNo = tokenInSession.replace("mock-token-", "");
        } else if (token != null && token.startsWith("mock-token-")) {
            stfNo = token.replace("mock-token-", "");
        }
        
        mockUser.setStfNo(stfNo);
        mockUser.setStfNm("테스트사용자");
        mockUser.setDeptCd("DEPT001");
        mockUser.setDeptNm("플랜잇");
        mockUser.setAuthCd("S"); // S = Super admin, or use normal user auth
        
        // Store user in session
        session.setAttribute("pev-user", mockUser);
        
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
