package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.EffectiveMemberStatistics;
import java.util.List;

public interface EffectiveMemberStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EffectiveMemberStatistics record);

    EffectiveMemberStatistics selectByPrimaryKey(Long id);

    List<EffectiveMemberStatistics> selectAll();

    int updateByPrimaryKey(EffectiveMemberStatistics record);
}