package ai.planit.pev.domain.meta.user.dao;

import ai.planit.idp.sdk.model.IdpLoginUser;

public interface UserDAO {
    void insertLoginLog(IdpLoginUser idpLoginUser);
}
