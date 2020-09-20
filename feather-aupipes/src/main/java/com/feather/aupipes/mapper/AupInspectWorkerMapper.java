package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupInspectWorker;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 巡检任务人员关联Mapper接口
 * 
 * @author fancy
 * @date 2020-06-16
 */
public interface AupInspectWorkerMapper 
{
    /**
     * 查询巡检任务人员关联
     * 
     * @param id 巡检任务人员关联ID
     * @return 巡检任务人员关联
     */
    public AupInspectWorker selectAupInspectWorkerById(Long id);

    /**
     * 通过任务id的列表来查询到中间表记录
     * @param taskIdList
     * @return
     */
    public List<AupInspectWorker> selectAupInspectWorkerByTaskIds(@Param("taskIdList")String[] taskIdList);

    /**
     * 查询巡检任务人员关联列表
     * 
     * @param aupInspectWorker 巡检任务人员关联
     * @return 巡检任务人员关联集合
     */
    public List<AupInspectWorker> selectAupInspectWorkerList(AupInspectWorker aupInspectWorker);

    /**
     * 新增巡检任务人员关联
     * 
     * @param aupInspectWorker 巡检任务人员关联
     * @return 结果
     */
    public int insertAupInspectWorker(AupInspectWorker aupInspectWorker);

    /**
     * 修改巡检任务人员关联
     * 
     * @param aupInspectWorker 巡检任务人员关联
     * @return 结果
     */
    public int updateAupInspectWorker(AupInspectWorker aupInspectWorker);

    /**
     * 删除巡检任务人员关联
     * 
     * @param id 巡检任务人员关联ID
     * @return 结果
     */
    public int deleteAupInspectWorkerById(Long id);

    /**
     * 批量删除巡检任务人员关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupInspectWorkerByIds(String[] ids);

    /**
     * 通过任务id批量删除巡检任务人员关联
     *
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupInspectWorkerByTaskIds(String[] taskIds);
}
