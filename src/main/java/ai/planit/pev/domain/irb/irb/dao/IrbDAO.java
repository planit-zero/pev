package ai.planit.pev.domain.irb.irb.dao;

import ai.planit.pev.domain.irb.irb.dto.Irb;

import java.util.List;

public interface IrbDAO {
    List<Irb> getIrbList(String stfNo);
}
