package ai.planit.pev.domain.irb.irb.service;

import ai.planit.pev.domain.irb.irb.dto.Irb;

import java.util.List;

public interface IrbService {
    List<Irb> getIrbList(String stfNo);
}
