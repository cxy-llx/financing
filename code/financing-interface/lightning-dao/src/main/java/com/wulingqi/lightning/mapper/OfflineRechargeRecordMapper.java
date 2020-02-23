package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.OfflineRechargeRecord;
import java.util.List;

public interface OfflineRechargeRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OfflineRechargeRecord record);

    OfflineRechargeRecord selectByPrimaryKey(Long id);

    List<OfflineRechargeRecord> selectAll();

    int updateByPrimaryKey(OfflineRechargeRecord record);
}