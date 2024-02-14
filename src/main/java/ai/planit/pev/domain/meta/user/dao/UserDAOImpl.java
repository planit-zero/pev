package ai.planit.pev.domain.meta.user.dao;

import ai.planit.idp.sdk.model.IdpLoginUser;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void insertLoginLog(IdpLoginUser idpLoginUser) {
        sqlSessionTemplate.insert("insertLoginLog", idpLoginUser);
    }
}
