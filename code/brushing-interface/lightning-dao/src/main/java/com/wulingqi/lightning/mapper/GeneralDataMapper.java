package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.GeneralData;
import java.util.List;

public interface GeneralDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GeneralData record);

    GeneralData selectByPrimaryKey(Integer id);

    List<GeneralData> selectAll();

    int updateByPrimaryKey(GeneralData record);
}