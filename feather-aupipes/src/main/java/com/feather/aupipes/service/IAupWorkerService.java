package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupWorker;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author fancy
 * @date 2020-01-06
 */
public interface IAupWorkerService
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param workerId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    AupWorker selectAupWorkerById(Long workerId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param aupWorker 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<AupWorker> selectAupWorkerList(AupWorker aupWorker);

    /**
     * 新增【请填写功能名称】
     * 
     * @param aupWorker 【请填写功能名称】
     * @return 结果
     */
    int insertAupWorker(AupWorker aupWorker);

    /**
     * 修改【请填写功能名称】
     * 
     * @param aupWorker 【请填写功能名称】
     * @return 结果
     */
    int updateAupWorker(AupWorker aupWorker);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupWorkerByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param workerId 【请填写功能名称】ID
     * @return 结果
     */
    int deleteAupWorkerById(Long workerId);
}
