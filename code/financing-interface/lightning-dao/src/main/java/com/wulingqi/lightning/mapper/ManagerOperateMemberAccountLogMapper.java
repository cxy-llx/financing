package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.ManagerOperateMemberAccountLog;
import java.util.List;

public interface ManagerOperateMemberAccountLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ManagerOperateMemberAccountLog record);

    ManagerOperateMemberAccountLog selectByPrimaryKey(Long id);

    List<ManagerOperateMemberAccountLog> selectAll();

    int updateByPrimaryKey(ManagerOperateMemberAccountLog record);
}