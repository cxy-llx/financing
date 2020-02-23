package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.BusinessDictionary;
import java.util.List;

public interface BusinessDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessDictionary record);

    BusinessDictionary selectByPrimaryKey(Long id);

    List<BusinessDictionary> selectAll();

    int updateByPrimaryKey(BusinessDictionary record);
}