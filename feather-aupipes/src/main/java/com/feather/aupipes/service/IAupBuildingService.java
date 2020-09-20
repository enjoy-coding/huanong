package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupBuilding;
import java.util.List;

/**
 * 楼栋信息Service接口
 *
 * @author fancy
 * @date 2019-12-30
 */
public interface IAupBuildingService
{
    /**
     * 查询楼栋信息
     *
     * @param id 楼栋信息ID
     * @return 楼栋信息
     */
    AupBuilding selectAupBuildingById(String id);

    /**
     * 查询楼栋能源信息
     *
     * @param id 楼栋信息ID
     * @return 楼栋信息
     */
    AupBuilding selectAupBuildingEnergyById(String id);

}