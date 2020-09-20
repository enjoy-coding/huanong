package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupInspectCoordinate;

import java.util.List;

/**
 * 巡检坐标Mapper接口
 * 
 * @author fancy
 * @date 2020-03-13
 */
public interface AupInspectCoordinateMapper 
{
    /**
     * 查询巡检坐标
     * 
     * @param coordinateId 巡检坐标ID
     * @return 巡检坐标
     */
    public AupInspectCoordinate selectAupInspectCoordinateById(Long coordinateId);

    /**
     * 查询巡检坐标列表
     * 
     * @param aupInspectCoordinate 巡检坐标
     * @return 巡检坐标集合
     */
    public List<AupInspectCoordinate> selectAupInspectCoordinateList(AupInspectCoordinate aupInspectCoordinate);

    /**
     * 新增巡检坐标
     * 
     * @param aupInspectCoordinate 巡检坐标
     * @return 结果
     */
    public int insertAupInspectCoordinate(AupInspectCoordinate aupInspectCoordinate);

    /**
     * 修改巡检坐标
     * 
     * @param aupInspectCoordinate 巡检坐标
     * @return 结果
     */
    public int updateAupInspectCoordinate(AupInspectCoordinate aupInspectCoordinate);

    /**
     * 删除巡检坐标
     * 
     * @param coordinateId 巡检坐标ID
     * @return 结果
     */
    public int deleteAupInspectCoordinateById(Long coordinateId);

    /**
     * 批量删除巡检坐标
     * 
     * @param coordinateIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupInspectCoordinateByIds(String[] coordinateIds);

    /**
     * 通过巡检任务id批量删除巡检坐标
     *
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupInspectCoordinateByTaskIds(String[] taskIds);
}
