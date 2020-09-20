package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupRepair;
import com.feather.common.core.domain.AjaxResult;

import java.util.List;

/**
 * 维修记录Service接口
 * 
 * @author fancy
 * @date 2020-04-01
 */
public interface IAupRepairService
{
    /**
     * 查询维修记录
     * 
     * @param id 维修记录ID
     * @return 维修记录
     */
    AupRepair selectAupRepairById(Long id);

    /**
     * 查询维修记录列表
     * 
     * @param aupRepair 维修记录
     * @return 维修记录集合
     */
    List<AupRepair> selectAupRepairList(AupRepair aupRepair);

    AjaxResult selectAupRepairListOfGeoJson();

    /**
     * 新增维修记录
     * 
     * @param aupRepair 维修记录
     * @return 结果
     */
    int insertAupRepair(AupRepair aupRepair);

    /**
     * 修改维修记录
     * 
     * @param aupRepair 维修记录
     * @return 结果
     */
    int updateAupRepair(AupRepair aupRepair);

    /**
     * 批量删除维修记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupRepairByIds(String ids);

    /**
     * 删除维修记录信息
     * 
     * @param id 维修记录ID
     * @return 结果
     */
    int deleteAupRepairById(Long id);
}
