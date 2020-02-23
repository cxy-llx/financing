package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.VerificationCode;
import java.util.List;

public interface VerificationCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VerificationCode record);

    VerificationCode selectByPrimaryKey(Long id);

    List<VerificationCode> selectAll();

    int updateByPrimaryKey(VerificationCode record);
}