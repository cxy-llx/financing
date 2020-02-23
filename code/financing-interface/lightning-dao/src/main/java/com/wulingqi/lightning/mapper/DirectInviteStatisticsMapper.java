package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.DirectInviteStatistics;
import java.util.List;

public interface DirectInviteStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DirectInviteStatistics record);

    DirectInviteStatistics selectByPrimaryKey(Long id);

    List<DirectInviteStatistics> selectAll();

    int updateByPrimaryKey(DirectInviteStatistics record);
}