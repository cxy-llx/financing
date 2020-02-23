package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.InviteStatisticsHandleQueue;
import java.util.List;

public interface InviteStatisticsHandleQueueMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InviteStatisticsHandleQueue record);

    InviteStatisticsHandleQueue selectByPrimaryKey(Long id);

    List<InviteStatisticsHandleQueue> selectAll();

    int updateByPrimaryKey(InviteStatisticsHandleQueue record);
}