package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupPumpPointStatus;
import java.util.List;

/**
 * 泵组状态值Service接口
 *
 * @author fancy
 * @date 2020-04-18
 */
public interface IAupPumpPointStatusService
{
    /**
     * 查询泵组状态值
     *
     * @param id 泵组状态值ID
     * @return 泵组状态值
     */
    AupPumpPointStatus selectAupPumpPointStatusById(Long id);
    Long selectAupPumpPointStatusByPointId(AupPumpPointStatus aupPumpPointStatus);

    /**
     * 查询泵组状态值列表
     *
     * @param aupPumpPointStatus 泵组状态值
     * @return 泵组状态值集合
     */
    List<AupPumpPointStatus> selectAupPumpPointStatusList(AupPumpPointStatus aupPumpPointStatus);

    /**
     * 新增泵组状态值
     *
     * @param aupPumpPointStatus 泵组状态值
     * @return 结果
     */
    int insertAupPumpPointStatus(AupPumpPointStatus aupPumpPointStatus);

    /**
     * 修改泵组状态值
     *
     * @param aupPumpPointStatus 泵组状态值
     * @return 结果
     */
    int updateAupPumpPointStatus(AupPumpPointStatus aupPumpPointStatus);

    /**
     * 批量删除泵组状态值
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupPumpPointStatusByIds(String ids);

    /**
     * 删除泵组状态值信息
     *
     * @param id 泵组状态值ID
     * @return 结果
     */
    int deleteAupPumpPointStatusById(Long id);

    List<AupPumpPointStatus> getAupPumpPointStatusByPoint(Long point);
}