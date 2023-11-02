package ai.planit.pev.domain.ods.form.dto;

import lombok.Getter;
import lombok.Setter;

public class FormStyleXML {
    @Getter
    @Setter
    public static class Request {
        private int mdfmId;
        private int mdfmFomSeq;
        private int mdfmSctnSeq;
    }

    @Getter
    @Setter
    public static class Response {
        private String sctnDgnMetaLdat;
    }
}
