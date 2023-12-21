package ai.planit.pev.domain.meta.record.dto;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.constant.ChartMaskingType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class MetaRecordFormat {
    @Getter
    @Setter
    public static class Request {
        private String recordId;
        private String startDate;
        private String endDate;
    }

    @Getter
    @Setter
    public static class Response {
        private String recordId;
        private int mdfmId;
        private int mdfmFomSeq;
        private int sectionId;
        private String id;
        private String parentId;
        private String mdfmCpemNo;
        private ChartClassType classType;
        private ChartControlType controlType;
        private ChartMaskingType maskingType;
        private String content;
        private String desc;
        private Timestamp loadDtm;
    }
}
