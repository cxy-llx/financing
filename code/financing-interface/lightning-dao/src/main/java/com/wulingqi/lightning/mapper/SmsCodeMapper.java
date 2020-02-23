package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.SmsCode;
import java.util.List;

public interface SmsCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SmsCode record);

    SmsCode selectByPrimaryKey(Long id);

    List<SmsCode> selectAll();

    int updateByPrimaryKey(SmsCode record);
}