package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.UnmatchOrder;
import java.util.List;

public interface UnmatchOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UnmatchOrder record);

    UnmatchOrder selectByPrimaryKey(Long id);

    List<UnmatchOrder> selectAll();

    int updateByPrimaryKey(UnmatchOrder record);
}