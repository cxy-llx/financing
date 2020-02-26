package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.AccountConcurrentError;
import java.util.List;

public interface AccountConcurrentErrorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccountConcurrentError record);

    AccountConcurrentError selectByPrimaryKey(Long id);

    List<AccountConcurrentError> selectAll();

    int updateByPrimaryKey(AccountConcurrentError record);
}