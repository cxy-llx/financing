package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.MerchantResourcePool;
import java.util.List;

public interface MerchantResourcePoolMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MerchantResourcePool record);

    MerchantResourcePool selectByPrimaryKey(Long id);

    List<MerchantResourcePool> selectAll();

    int updateByPrimaryKey(MerchantResourcePool record);
}