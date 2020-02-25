package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.OrderCallback;
import java.util.List;

public interface OrderCallbackMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderCallback record);

    OrderCallback selectByPrimaryKey(Long id);

    List<OrderCallback> selectAll();

    int updateByPrimaryKey(OrderCallback record);
}