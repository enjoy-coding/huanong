package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupInspect;
import com.feather.aupipes.domain.AupPatrolCount;

import java.util.List;

/**
 * 巡检任务Mapper接口
 * 
 * @author fancy
 * @date 2020-06-03
 */
public interface AupInspectMapper 
{

    public Integer selectTotal();

    public Integer selectCurMonthCount();

    public Integer selectAlreadyCurMonthCount();

    public List<AupPatrolCount> selectAupInspectCount(AupInspect aupInspect);

    /**
     * 查询巡检任务
     * 
     * @param id 巡检任务ID
     * @return 巡检任务
     */
    public AupInspect selectAupInspectById(Long id);

    public List<AupInspect> selectAupInspectListByIds(String[] ids);

    /**
     * 查询巡检任务列表
     * 
     * @param aupInspect 巡检任务
     * @return 巡检任务集合
     */
    public List<AupInspect> selectAupInspectList(AupInspect aupInspect);

    /**
     * 新增巡检任务
     * 
     * @param aupInspect 巡检任务
     * @return 结果
     */
    public int insertAupInspect(AupInspect aupInspect);

    /**
     * 修改巡检任务
     * 
     * @param aupInspect 巡检任务
     * @return 结果
     */
    public int updateAupInspect(AupInspect aupInspect);

    /**
     * 删除巡检任务
     * 
     * @param id 巡检任务ID
     * @return 结果
     */
    public int deleteAupInspectById(Long id);

    /**
     * 批量删除巡检任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupInspectByIds(String[] ids);
}
