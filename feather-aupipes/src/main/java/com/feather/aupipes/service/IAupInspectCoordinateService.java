package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupInspectCoordinate;
import com.feather.common.core.domain.AjaxResult;

import java.util.List;

/**
 * 巡检坐标Service接口
 * 
 * @author fancy
 * @date 2020-03-13
 */
public interface IAupInspectCoordinateService
{
    /**
     * 查询巡检坐标
     * 
     * @param coordinateId 巡检坐标ID
     * @return 巡检坐标
     */
    AupInspectCoordinate selectAupInspectCoordinateById(Long coordinateId);

    /**
     * 查询巡检坐标列表
     * 
     * @param aupInspectCoordinate 巡检坐标
     * @return 巡检坐标集合
     */
    List<AupInspectCoordinate> selectAupInspectCoordinateList(AupInspectCoordinate aupInspectCoordinate);


    AjaxResult selectAupInspectCoordinateGeoJson(AupInspectCoordinate aupInspectCoordinate);

    /**
     * 新增巡检坐标
     * 
     * @param aupInspectCoordinate 巡检坐标
     * @return 结果
     */
    int insertAupInspectCoordinate(AupInspectCoordinate aupInspectCoordinate);

    /**
     * 修改巡检坐标
     * 
     * @param aupInspectCoordinate 巡检坐标
     * @return 结果
     */
    int updateAupInspectCoordinate(AupInspectCoordinate aupInspectCoordinate);

    /**
     * 批量删除巡检坐标
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupInspectCoordinateByIds(String ids);

    /**
     * 删除巡检坐标信息
     * 
     * @param coordinateId 巡检坐标ID
     * @return 结果
     */
    int deleteAupInspectCoordinateById(Long coordinateId);
}
