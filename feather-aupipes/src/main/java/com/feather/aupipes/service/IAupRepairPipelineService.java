package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupRepairPipeline;
import java.util.List;

/**
 * 管线设备Service接口
 * 
 * @author fancy
 * @date 2020-04-16
 */
public interface IAupRepairPipelineService
{
    /**
     * 查询管线设备
     * 
     * @param id 管线设备ID
     * @return 管线设备
     */
    AupRepairPipeline selectAupRepairPipelineById(Long id);

    /**
     * 查询管线设备列表
     * 
     * @param aupRepairPipeline 管线设备
     * @return 管线设备集合
     */
    List<AupRepairPipeline> selectAupRepairPipelineList(AupRepairPipeline aupRepairPipeline);

    /**
     * 新增管线设备
     * 
     * @param aupRepairPipelineList 管线设备
     * @return 结果
     */
    int insertAupRepairPipelines(List<AupRepairPipeline> aupRepairPipelineList);

    /**
     * 新增管线设备
     *
     * @param aupRepairPipeline 管线设备
     * @return 结果
     */
    int insertAupRepairPipeline(AupRepairPipeline aupRepairPipeline);

    /**
     * 修改管线设备
     * 
     * @param aupRepairPipeline 管线设备
     * @return 结果
     */
    int updateAupRepairPipeline(AupRepairPipeline aupRepairPipeline);

    /**
     * 批量删除管线设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupRepairPipelineByIds(String ids);

    /**
     * 删除管线设备信息
     * 
     * @param id 管线设备ID
     * @return 结果
     */
    int deleteAupRepairPipelineById(Long id);
}
