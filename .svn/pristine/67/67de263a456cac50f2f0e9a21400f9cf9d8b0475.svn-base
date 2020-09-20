package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupRepairPipeline;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管线设备Mapper接口
 * 
 * @author fancy
 * @date 2020-04-16
 */
public interface AupRepairPipelineMapper 
{
    /**
     * 查询管线设备
     * 
     * @param id 管线设备ID
     * @return 管线设备
     */
    public AupRepairPipeline selectAupRepairPipelineById(Long id);

    /**
     * 查询管线设备列表
     * 
     * @param aupRepairPipeline 管线设备
     * @return 管线设备集合
     */
    public List<AupRepairPipeline> selectAupRepairPipelineList(AupRepairPipeline aupRepairPipeline);

    /**
     * 新增管线设备
     * 
     * @param aupRepairPipeline 管线设备
     * @return 结果
     */
    public int insertAupRepairPipelines(@Param("list")List<AupRepairPipeline> aupRepairPipelineList);

    /**
     * 新增管线设备
     *
     * @param aupRepairPipeline 管线设备
     * @return 结果
     */
    public int insertAupRepairPipeline(AupRepairPipeline aupRepairPipeline);

    /**
     * 修改管线设备
     * 
     * @param aupRepairPipeline 管线设备
     * @return 结果
     */
    public int updateAupRepairPipeline(AupRepairPipeline aupRepairPipeline);

    /**
     * 删除管线设备
     * 
     * @param id 管线设备ID
     * @return 结果
     */
    public int deleteAupRepairPipelineById(Long id);

    /**
     * 批量删除管线设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupRepairPipelineByIds(String[] ids);
}
