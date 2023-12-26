package ai.planit.pev.domain.meta.user.service;

import ai.planit.idp.sdk.constant.IdpLoginType;
import ai.planit.idp.sdk.handler.IdpRequestHandler;
import ai.planit.idp.sdk.model.IdpLoginUser;
import ai.planit.idp.sdk.model.IdpResponse;
import ai.planit.idp.sdk.option.IdpRequestOptions;
import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.meta.user.dto.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IdpRequestHandler<IdpLoginUser> idpRequestHandler;

    @Override
    public void checkAccountAndSendVerificationCode(UserLogin userLogin) {
        IdpRequestOptions options = IdpRequestOptions.builder()
                .loginId(userLogin.getStfNo())
                .password(userLogin.getStfPw())
                .dbKey(userLogin.getDbKey())
                .loginType(IdpLoginType.getIdpLoginTypeByCode(userLogin.getLoginType().toString()))
                .build();

        IdpResponse<IdpLoginUser> response = idpRequestHandler.checkAccountAndSendVerificationCode(options);

        if (HttpStatus.OK != response.getStatus()) {
            throw new RuntimeException(response.getError().getMessage());
        }
    }

    @Override
    public void checkVerificationCodeAndLogin(HttpSession session, UserLogin userLogin) {
        IdpRequestOptions options = IdpRequestOptions.builder()
                .loginId(userLogin.getStfNo())
                .password(userLogin.getStfPw())
                .verificationCode(userLogin.getVerificationCode())
                .dbKey(userLogin.getDbKey())
                .loginType(IdpLoginType.getIdpLoginTypeByCode(userLogin.getLoginType().toString()))
                .build();

        IdpResponse<IdpLoginUser> response = idpRequestHandler.checkVerificationCodeAndLogin(options);

        if (HttpStatus.OK != response.getStatus()) {
            throw new RuntimeException(response.getError().getMessage());
        }

        session.setAttribute("pev-token", response.getToken());
    }

    @Override
    public IdpLoginUser getIdpLoginUser(HttpSession session, String token) {
        if (token == null && Objects.isNull(session.getAttribute("pev-token"))) {
            throw new BaseException(ErrorType.IDP_TOKEN_NOT_FOUND);
        }

        IdpResponse<IdpLoginUser> response = idpRequestHandler.getIdpLoginUser(
                token != null
                        ? token
                        : session.getAttribute("pev-token").toString());

        if (HttpStatus.OK != response.getStatus()) {
            throw new RuntimeException(response.getError().getMessage());
        }

        return response.getLoginUser();
    }
}
