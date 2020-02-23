package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.MerchantWithdrawApply;
import java.util.List;

public interface MerchantWithdrawApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MerchantWithdrawApply record);

    MerchantWithdrawApply selectByPrimaryKey(Long id);

    List<MerchantWithdrawApply> selectAll();

    int updateByPrimaryKey(MerchantWithdrawApply record);
}