package ai.planit.pev.domain.meta.user.dao;

import ai.planit.idp.sdk.model.IdpLoginUser;
import ai.planit.pev.domain.meta.report.dto.Log;
import ai.planit.pev.domain.meta.user.dto.UserLoginDay;
import ai.planit.pev.domain.meta.user.dto.UserLoginDept;
import ai.planit.pev.domain.meta.user.dto.UserLoginHour;
import ai.planit.pev.domain.meta.user.dto.UserLoginWeek;

import java.util.List;

public interface UserDAO {
    void insertLoginLog(IdpLoginUser idpLoginUser);

    List<Log> getLogUser();

    List<UserLoginWeek> getStatisticsUserMonth();

    List<UserLoginDay> getStatisticsUserDay();

    List<UserLoginHour> getStatisticsUserHour();

    List<UserLoginDept> getStatisticsUserDept();
}
