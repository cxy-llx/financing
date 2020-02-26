package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.MemberUnmatchAmount;
import java.util.List;

public interface MemberUnmatchAmountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberUnmatchAmount record);

    MemberUnmatchAmount selectByPrimaryKey(Long id);

    List<MemberUnmatchAmount> selectAll();

    int updateByPrimaryKey(MemberUnmatchAmount record);
}