package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.MerchantBalanceTrade;
import java.util.List;

public interface MerchantBalanceTradeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MerchantBalanceTrade record);

    MerchantBalanceTrade selectByPrimaryKey(Long id);

    List<MerchantBalanceTrade> selectAll();

    int updateByPrimaryKey(MerchantBalanceTrade record);
}