package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.StatisticsHandleQueue;
import java.util.List;

public interface StatisticsHandleQueueMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StatisticsHandleQueue record);

    StatisticsHandleQueue selectByPrimaryKey(Long id);

    List<StatisticsHandleQueue> selectAll();

    int updateByPrimaryKey(StatisticsHandleQueue record);
}