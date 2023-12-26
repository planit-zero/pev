package ai.planit.pev.domain.meta.user.dto;

import ai.planit.idp.sdk.constant.IdpLoginType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogin {
    private String stfNo;
    private String stfPw;
    private String verificationCode;
    private String dbKey;
    private IdpLoginType loginType;
}
