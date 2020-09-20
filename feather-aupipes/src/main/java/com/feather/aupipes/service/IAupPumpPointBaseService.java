package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupPumpPointBase;
import java.util.List;

/**
 * 泵组基本数据值Service接口
 *
 * @author fancy
 * @date 2020-04-18
 */
public interface IAupPumpPointBaseService
{
    /**
     * 查询泵组基本数据值
     *
     * @param id 泵组基本数据值ID
     * @return 泵组基本数据值
     */
    AupPumpPointBase selectAupPumpPointBaseById(Long id);

    AupPumpPointBase selectAupPumpPointBaseByPointId(Long pointId);

    /**
     * 查询泵组基本数据值列表
     *
     * @param aupPumpPointBase 泵组基本数据值
     * @return 泵组基本数据值集合
     */
    List<AupPumpPointBase> selectAupPumpPointBaseList(AupPumpPointBase aupPumpPointBase);

    /**
     * 新增泵组基本数据值
     *
     * @param aupPumpPointBase 泵组基本数据值
     * @return 结果
     */
    int insertAupPumpPointBase(AupPumpPointBase aupPumpPointBase);

    /**
     * 修改泵组基本数据值
     *
     * @param aupPumpPointBase 泵组基本数据值
     * @return 结果
     */
    int updateAupPumpPointBase(AupPumpPointBase aupPumpPointBase);

    /**
     * 批量删除泵组基本数据值
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupPumpPointBaseByIds(String ids);

    /**
     * 删除泵组基本数据值信息
     *
     * @param id 泵组基本数据值ID
     * @return 结果
     */
    int deleteAupPumpPointBaseById(Long id);

    /**
     * 查询最近更新的一条数据
     * @param pointId 点
     * @return List<AupPumpPointBase>
     */
    List<AupPumpPointBase> selectMaxTimeAupPumpPointBase(Long pointId);
}