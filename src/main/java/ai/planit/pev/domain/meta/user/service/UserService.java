package ai.planit.pev.domain.meta.user.service;

import ai.planit.idp.sdk.model.IdpLoginUser;
import ai.planit.pev.domain.meta.user.dto.UserLogin;

import javax.servlet.http.HttpSession;

public interface UserService {
    void checkAccountAndSendVerificationCode(UserLogin userLogin);
    void checkVerificationCodeAndLogin(HttpSession session, UserLogin userLogin);

    IdpLoginUser getIdpLoginUser(HttpSession session, String token);
}
