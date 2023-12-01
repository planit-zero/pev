package ai.planit.pev.domain.ods.function.dao;

import ai.planit.pev.domain.ods.function.dto.FunctionData;
import ai.planit.pev.domain.ods.function.dto.FunctionDecodeMaster;

import java.util.List;

public interface FunctionDAO {
    List<FunctionDecodeMaster> getFunctionDecodeMasterList(String examKey);

    List<FunctionData> getFunctionData(FunctionDecodeMaster master);
}
