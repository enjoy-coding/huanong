package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupPumpPoint;
import java.util.List;

/**
 * 泵房点位Mapper接口
 *
 * @author fancy
 * @date 2020-04-17
 */
public interface AupPumpPointMapper
{
    /**
     * 查询泵房点位
     *
     * @param id 泵房点位ID
     * @return 泵房点位
     */
    public AupPumpPoint selectAupPumpPointById(Long id);

    /**
     * 查询泵房点位列表
     *
     * @param aupPumpPoint 泵房点位
     * @return 泵房点位集合
     */
    public List<AupPumpPoint> selectAupPumpPointList(AupPumpPoint aupPumpPoint);

    /**
     * 新增泵房点位
     *
     * @param aupPumpPoint 泵房点位
     * @return 结果
     */
    public int insertAupPumpPoint(AupPumpPoint aupPumpPoint);

    /**
     * 修改泵房点位
     *
     * @param aupPumpPoint 泵房点位
     * @return 结果
     */
    public int updateAupPumpPoint(AupPumpPoint aupPumpPoint);

    /**
     * 删除泵房点位
     *
     * @param pumId 泵房点位ID
     * @return 结果
     */
    public int deleteAupPumpPointById(Long pumId);

    /**
     * 批量删除泵房点位
     *
     * @param pumIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupPumpPointByIds(String[] pumIds);
}