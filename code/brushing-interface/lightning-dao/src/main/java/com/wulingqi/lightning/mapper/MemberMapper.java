package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.Member;
import java.util.List;

public interface MemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    Member selectByPrimaryKey(Long id);

    List<Member> selectAll();

    int updateByPrimaryKey(Member record);
}