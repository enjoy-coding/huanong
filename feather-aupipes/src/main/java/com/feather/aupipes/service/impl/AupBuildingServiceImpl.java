package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupBuilding;
import com.feather.aupipes.mapper.AupBuildingMapper;
import com.feather.aupipes.service.IAupBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 楼栋信息Service业务层处理
 *
 * @author fancy
 * @date 2019-12-30
 */
@Service
public class AupBuildingServiceImpl implements IAupBuildingService
{
    @Autowired
    private AupBuildingMapper aupBuildingMapper;

    /**
     * 查询楼栋信息
     *
     * @param id 楼栋信息ID
     * @return 楼栋信息
     */
    @Override
    public AupBuilding selectAupBuildingById(String id)
    {
        return aupBuildingMapper.selectAupBuildingById(id);
    }


    /**
     * 查询楼栋能源信息
     *
     * @param id 楼栋信息ID
     * @return 楼栋信息
     */
    @Override
    public AupBuilding selectAupBuildingEnergyById(String id){
        return aupBuildingMapper.selectAupBuildingEnergyById(id);
    }
}