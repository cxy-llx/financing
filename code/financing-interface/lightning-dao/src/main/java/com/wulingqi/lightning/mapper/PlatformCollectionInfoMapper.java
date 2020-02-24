package com.wulingqi.lightning.mapper;

import com.wulingqi.lightning.model.PlatformCollectionInfo;
import java.util.List;

public interface PlatformCollectionInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PlatformCollectionInfo record);

    PlatformCollectionInfo selectByPrimaryKey(Long id);

    List<PlatformCollectionInfo> selectAll();

    int updateByPrimaryKey(PlatformCollectionInfo record);
}