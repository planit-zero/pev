package ai.planit.pev.domain.ods.fall.dao;

import java.util.List;

public interface FallDAO {
    List<String> getFallDetailTextList(String keyId);
    String getFallTotalText(String keyId);
    String getFallWriterNm(String keyId);
}
