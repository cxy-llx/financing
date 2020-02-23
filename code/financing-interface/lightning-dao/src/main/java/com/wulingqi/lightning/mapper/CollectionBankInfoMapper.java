package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.CollectionBankInfo;
import java.util.List;

public interface CollectionBankInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CollectionBankInfo record);

    CollectionBankInfo selectByPrimaryKey(Long id);

    List<CollectionBankInfo> selectAll();

    int updateByPrimaryKey(CollectionBankInfo record);
}