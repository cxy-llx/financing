package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.TeamLevel;
import java.util.List;

public interface TeamLevelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TeamLevel record);

    TeamLevel selectByPrimaryKey(Long id);

    List<TeamLevel> selectAll();

    int updateByPrimaryKey(TeamLevel record);
}