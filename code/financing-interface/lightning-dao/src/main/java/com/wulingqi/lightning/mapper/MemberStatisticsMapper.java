package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.MemberStatistics;
import java.util.List;

public interface MemberStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberStatistics record);

    MemberStatistics selectByPrimaryKey(Long id);

    List<MemberStatistics> selectAll();

    int updateByPrimaryKey(MemberStatistics record);
}