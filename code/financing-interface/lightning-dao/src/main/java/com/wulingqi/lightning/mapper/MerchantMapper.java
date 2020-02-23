package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.Merchant;
import java.util.List;

public interface MerchantMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Merchant record);

    Merchant selectByPrimaryKey(Long id);

    List<Merchant> selectAll();

    int updateByPrimaryKey(Merchant record);
}