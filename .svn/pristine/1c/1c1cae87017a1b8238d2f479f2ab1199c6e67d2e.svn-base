package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupInspect;
import com.feather.aupipes.utils.vo.AupInspectVo;
import com.feather.common.core.domain.AjaxResult;

import java.util.List;

/**
 * 巡检任务Service接口
 * 
 * @author fancy
 * @date 2020-06-03
 */
public interface IAupInspectService
{

    /**
     * 按巡检状态统计数量
     * @param aupInspect
     * @return
     */
    AjaxResult selectAupInspectCount(AupInspect aupInspect);

    /**
     * 查询巡检任务
     * 
     * @param inspectId 巡检任务ID
     * @return 巡检任务
     */
    AupInspect selectAupInspectById(Long inspectId);

    /**
     * 通过id来查询巡检任务列表
     * @param ids
     * @return
     */
    List<AupInspectVo> selectAupInspectVoListByIds(String ids);

    /**
     * 查询巡检任务列表
     * 
     * @param aupInspect 巡检任务
     * @return 巡检任务集合
     */
    List<AupInspect> selectAupInspectList(AupInspect aupInspect);

    /**
     * 新增巡检任务
     * 
     * @param aupInspect 巡检任务
     * @return 结果
     */
    int insertAupInspect(AupInspect aupInspect);

    /**
     * 修改巡检任务
     * 
     * @param aupInspect 巡检任务
     * @return 结果
     */
    int updateAupInspect(AupInspect aupInspect);

    /**
     * 批量删除巡检任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupInspectByIds(String ids);

    /**
     * 删除巡检任务信息
     * 
     * @param inspectId 巡检任务ID
     * @return 结果
     */
    int deleteAupInspectById(Long inspectId);
}
