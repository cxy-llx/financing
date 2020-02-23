package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.AppUpgrade;
import java.util.List;

public interface AppUpgradeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppUpgrade record);

    AppUpgrade selectByPrimaryKey(Long id);

    List<AppUpgrade> selectAll();

    int updateByPrimaryKey(AppUpgrade record);
}