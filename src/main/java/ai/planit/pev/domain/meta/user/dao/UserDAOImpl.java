package ai.planit.pev.domain.meta.user.dao;

import ai.planit.idp.sdk.model.IdpLoginUser;
import ai.planit.pev.domain.meta.report.dto.Log;
import ai.planit.pev.domain.meta.user.dto.UserLoginDept;
import ai.planit.pev.domain.meta.user.dto.UserLoginHour;
import ai.planit.pev.domain.meta.user.dto.UserLoginWeek;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void insertLoginLog(IdpLoginUser idpLoginUser) {
        sqlSessionTemplate.insert("insertLoginLog", idpLoginUser);
    }

    @Override
    public List<Log> getLogUser() {
        return sqlSessionTemplate.selectList("getLogUser");
    }

    @Override
    public List<UserLoginWeek> getStatisticsUserMonth() {
        return sqlSessionTemplate.selectList("getStatisticsUserMonth");
    }

    @Override
    public List<UserLoginHour> getStatisticsUserHour() {
        return sqlSessionTemplate.selectList("getStatisticsUserHour");
    }

    @Override
    public List<UserLoginDept> getStatisticsUserDept() {
        return sqlSessionTemplate.selectList("getStatisticsUserDept");
    }
}
