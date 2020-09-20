package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupPumpPoint;

import java.util.List;

/**
 * 泵房点位Service接口
 *
 * @author fancy
 * @date 2020-04-17
 */
public interface IAupPumpPointService
{
    /**
     * 查询泵房点位
     *
     * @param id 泵房点位ID
     * @return 泵房点位
     */
    AupPumpPoint selectAupPumpPointById(Long id);

    /**
     * 查询泵房点位列表
     *
     * @param aupPumpPoint 泵房点位
     * @return 泵房点位集合
     */
    List<AupPumpPoint> selectAupPumpPointList(AupPumpPoint aupPumpPoint);

    String getPumpPointValue(AupPumpPoint point);
    String getPumpPointStatesValue(AupPumpPoint point);

    /**
     * 新增泵房点位
     *
     * @param aupPumpPoint 泵房点位
     * @return 结果
     */
    int insertAupPumpPoint(AupPumpPoint aupPumpPoint);

    /**
     * 修改泵房点位
     *
     * @param aupPumpPoint 泵房点位
     * @return 结果
     */
    int updateAupPumpPoint(AupPumpPoint aupPumpPoint);

    /**
     * 批量删除泵房点位
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupPumpPointByIds(String ids);

    /**
     * 删除泵房点位信息
     *
     * @param pumId 泵房点位ID
     * @return 结果
     */
    int deleteAupPumpPointById(Long pumId);
}