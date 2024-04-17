package ai.planit.pev.domain.meta.report.service;

import ai.planit.idp.sdk.model.IdpLoginUser;
import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.meta.event.dao.EventDAO;
import ai.planit.pev.domain.meta.event.dto.Event;
import ai.planit.pev.domain.meta.event.dto.EventSearchTargetDistribution;
import ai.planit.pev.domain.meta.report.dao.ReportDAO;
import ai.planit.pev.domain.meta.report.dto.*;
import ai.planit.pev.domain.meta.user.dao.UserDAO;
import ai.planit.pev.domain.meta.user.dto.UserLoginDept;
import ai.planit.pev.domain.meta.user.dto.UserLoginHour;
import ai.planit.pev.domain.meta.user.dto.UserLoginWeek;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportDAO reportDAO;
    private final UserDAO userDAO;
    private final EventDAO eventDAO;

    @Override
    public void insertReport(HttpSession session, ChartReport report) {
        String irb = (String) session.getAttribute("pev-irb");
        String rid = (String) session.getAttribute("pev-rid");

        report.setIrb(irb != null ? irb : "");
        report.setRid(rid != null ? rid : "");

        reportDAO.insertReport(report);
        reportDAO.insertReportDetail(report);
    }

    @Override
    public void insertChartError(HttpSession session, ChartError chartError) {
        String irb = (String) session.getAttribute("pev-irb");
        String rid = (String) session.getAttribute("pev-rid");

        chartError.setIrb(irb);
        chartError.setRid(rid);

        reportDAO.insertChartError(chartError);
    }

    @Override
    public List<Report> getReportList(HttpSession session) {
        String userStr = (String) session.getAttribute("pev-user");

        if (userStr == null) {
            throw new BaseException(ErrorType.IDP_TOKEN_NOT_FOUND);
        }

        Gson gson = new Gson();
        IdpLoginUser idpLoginUser = gson.fromJson(userStr, IdpLoginUser.class);

        return reportDAO.getReportList(idpLoginUser);
    }

    @Override
    public List<ReportDetail> getReportDetailList(int reportId) {
        return reportDAO.getReportDetailList(reportId);
    }

    @Override
    public void updateProcess(ReportDetailUpdate reportDetailUpdate) {
        reportDAO.updateProcess(reportDetailUpdate);
    }

    @Override
    public List<ChartError> getChartErrorList(HttpSession session) {
        ChartErrorRequest request = new ChartErrorRequest();

        String userInSession = (String) session.getAttribute("pev-user");

        if (userInSession == null) {
            throw new BaseException(ErrorType.IDP_TOKEN_NOT_FOUND);
        }

        Gson gson = new Gson();
        IdpLoginUser user = gson.fromJson(userInSession, IdpLoginUser.class);

        request.setStfNo(user.getStfNo());
        request.setAuthCd(user.getAuthCd());

        return reportDAO.getChartErrorList(request);
    }

    @Override
    public void updateChartErrorProcess(int errId) {
        reportDAO.updateChartErrorProcess(errId);
    }

    @Override
    public List<Log> getLogUser(HttpSession session) {
        return userDAO.getLogUser();
    }

    @Override
    public List<Event> getLogEvent(HttpSession session) {
        return eventDAO.getLogEvent();
    }

    @Override
    public List<UserLoginWeek> getStatisticsUserMonth(HttpSession session) {
        return userDAO.getStatisticsUserMonth();
    }

    @Override
    public List<UserLoginHour> getStatisticsUserHour(HttpSession session) {
        return userDAO.getStatisticsUserHour();
    }

    @Override
    public List<EventSearchTargetDistribution> getEventSearchTargetDistribution(HttpSession session) {
        return eventDAO.getEventSearchTargetDistribution();
    }

    @Override
    public List<UserLoginDept> getStatisticsUserDept(HttpSession session) {
        return userDAO.getStatisticsUserDept();
    }
}
