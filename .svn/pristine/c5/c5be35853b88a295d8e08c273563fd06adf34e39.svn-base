package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupInspectService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 巡检记录设备Mapper接口
 * 
 * @author fancy
 * @date 2020-06-08
 */
public interface AupInspectServiceMapper 
{
    /**
     * 查询巡检记录设备
     * 
     * @param id 巡检记录设备ID
     * @return 巡检记录设备
     */
    public AupInspectService selectAupInspectServiceById(Long id);


    /**
     * 通过任务id的列表来查询到巡检设备列表
     * @param taskIdList
     * @return
     */
    public List<AupInspectService> selectAupInspectServiceByTaskIds(@Param("taskIdList")String[] taskIdList);



    /**
     * 查询巡检记录设备列表
     * 
     * @param aupInspectService 巡检记录设备
     * @return 巡检记录设备集合
     */
    public List<AupInspectService> selectAupInspectServiceList(AupInspectService aupInspectService);

    /**
     * 新增巡检记录设备
     * 
     * @param aupInspectService 巡检记录设备
     * @return 结果
     */
    public int insertAupInspectService(AupInspectService aupInspectService);

    /**
     * 修改巡检记录设备
     * 
     * @param aupInspectService 巡检记录设备
     * @return 结果
     */
    public int updateAupInspectService(AupInspectService aupInspectService);

    /**
     * 删除巡检记录设备
     * 
     * @param id 巡检记录设备ID
     * @return 结果
     */
    public int deleteAupInspectServiceById(Long id);

    /**
     * 批量删除巡检记录设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupInspectServiceByIds(String[] ids);

    /**
     * 通过任务id批量删除巡检记录设备
     *
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupInspectServiceByTaskIds(String[] taskIds);
}
