package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.TeamInviteStatistics;
import java.util.List;

public interface TeamInviteStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TeamInviteStatistics record);

    TeamInviteStatistics selectByPrimaryKey(Long id);

    List<TeamInviteStatistics> selectAll();

    int updateByPrimaryKey(TeamInviteStatistics record);
}