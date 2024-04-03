package ai.planit.pev.utility;

import ai.planit.idp.sdk.model.IdpLoginUser;
import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import com.google.gson.Gson;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    /**
     * 접속한 사용자
     */
    public static IdpLoginUser getLoginUser (HttpSession session) {
        String userStr = (String) session.getAttribute("pev-user");

        if (userStr == null) {
            throw new BaseException(ErrorType.IDP_TOKEN_NOT_FOUND);
        }

        Gson gson = new Gson();
        return gson.fromJson(userStr, IdpLoginUser.class);
    }

    /**
     * PID
     */
    public static String getPid(HttpSession session) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        return pid;
    }

}
