package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupYjtables;
import java.util.List;

/**
 * 预警信息统计Service接口
 * 
 * @author fancy
 * @date 2020-01-06
 */
public interface IAupYjtablesService
{
    /**
     * 查询预警信息统计
     * 
     * @param tid 预警信息统计ID
     * @return 预警信息统计
     */
    AupYjtables selectAupYjtablesById(String tid);

    /**
     * 查询预警信息统计列表
     * 
     * @param aupYjtables 预警信息统计
     * @return 预警信息统计集合
     */
    List<AupYjtables> selectAupYjtablesList(AupYjtables aupYjtables);

    /**
     * 新增预警信息统计
     * 
     * @param aupYjtables 预警信息统计
     * @return 结果
     */
    int insertAupYjtables(AupYjtables aupYjtables);

    /**
     * 修改预警信息统计
     * 
     * @param aupYjtables 预警信息统计
     * @return 结果
     */
    int updateAupYjtables(AupYjtables aupYjtables);

    /**
     * 批量删除预警信息统计
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupYjtablesByIds(String ids);

    /**
     * 删除预警信息统计信息
     * 
     * @param tid 预警信息统计ID
     * @return 结果
     */
    int deleteAupYjtablesById(String tid);
}
