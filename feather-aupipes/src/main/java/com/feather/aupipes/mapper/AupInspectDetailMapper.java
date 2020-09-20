package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupInspectDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 巡检设备Mapper接口
 * 
 * @author fancy
 * @date 2020-06-04
 */
public interface AupInspectDetailMapper 
{
    /**
     * 查询巡检设备
     * 
     * @param id 巡检设备ID
     * @return 巡检设备
     */
    public AupInspectDetail selectAupInspectDetailById(Long id);

    /**
     * 通过任务id的列表来查询到巡检记录列表
     * @param taskIdList
     * @return
     */
    public List<AupInspectDetail> selectAupInspectDetailByTaskIds(@Param("taskIdList")String[] taskIdList);

    /**
     * 查询巡检设备列表
     * 
     * @param aupInspectDetail 巡检设备
     * @return 巡检设备集合
     */
    public List<AupInspectDetail> selectAupInspectDetailList(AupInspectDetail aupInspectDetail);

    /**
     * 新增巡检设备
     * 
     * @param aupInspectDetail 巡检设备
     * @return 结果
     */
    public int insertAupInspectDetail(AupInspectDetail aupInspectDetail);

    /**
     * 修改巡检设备
     * 
     * @param aupInspectDetail 巡检设备
     * @return 结果
     */
    public int updateAupInspectDetail(AupInspectDetail aupInspectDetail);

    /**
     * 删除巡检设备
     * 
     * @param id 巡检设备ID
     * @return 结果
     */
    public int deleteAupInspectDetailById(Long id);

    /**
     * 批量删除巡检设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupInspectDetailByIds(String[] ids);

    /**
     * 通过巡检任务id批量删除巡检设备
     *
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupInspectDetailByTaskIds(String[] taskIds);
}
