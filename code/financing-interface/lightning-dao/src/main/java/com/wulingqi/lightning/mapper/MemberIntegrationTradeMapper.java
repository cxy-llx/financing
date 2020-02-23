package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.MemberIntegrationTrade;
import java.util.List;

public interface MemberIntegrationTradeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberIntegrationTrade record);

    MemberIntegrationTrade selectByPrimaryKey(Long id);

    List<MemberIntegrationTrade> selectAll();

    int updateByPrimaryKey(MemberIntegrationTrade record);
}