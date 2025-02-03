package ai.planit.pev.domain.meta.report.service;

import ai.planit.pev.domain.meta.event.dto.Event;
import ai.planit.pev.domain.meta.event.dto.EventSearchTargetDistribution;
import ai.planit.pev.domain.meta.report.dto.*;
import ai.planit.pev.domain.meta.user.dto.UserLoginDay;
import ai.planit.pev.domain.meta.user.dto.UserLoginDept;
import ai.planit.pev.domain.meta.user.dto.UserLoginHour;
import ai.planit.pev.domain.meta.user.dto.UserLoginWeek;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ReportService {
    void insertReport(HttpSession session, ChartReport report);

    void insertChartError(HttpSession session, ChartError chartError);

    List<Report> getReportList(HttpSession session);

    List<ReportDetail> getReportDetailList(int reportId);

    void updateProcess(ReportDetailUpdate reportDetailUpdate);

    List<ChartError> getChartErrorList(HttpSession session);

    void updateChartErrorProcess(int errId);

    List<Log> getLogUser(HttpSession session);

    List<Event> getLogEvent(HttpSession session);

    List<UserLoginWeek> getStatisticsUserMonth(HttpSession session);

    List<UserLoginDay> getStatisticsUserDay(HttpSession session);

    List<UserLoginHour> getStatisticsUserHour(HttpSession session);

    List<EventSearchTargetDistribution> getEventSearchTargetDistribution(HttpSession session);

    List<UserLoginDept> getStatisticsUserDept(HttpSession session);
}
