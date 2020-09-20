package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupPumpPointStatus;
import java.util.List;

/**
 * 泵组状态值Mapper接口
 *
 * @author fancy
 * @date 2020-04-18
 */
public interface AupPumpPointStatusMapper
{
    /**
     * 查询泵组状态值
     *
     * @param id 泵组状态值ID
     * @return 泵组状态值
     */
    public AupPumpPointStatus selectAupPumpPointStatusById(Long id);

    public Long selectAupPumpPointStatusByPointId(AupPumpPointStatus aupPumpPointStatus);

    /**
     * 查询泵组状态值列表
     *
     * @param aupPumpPointStatus 泵组状态值
     * @return 泵组状态值集合
     */
    public List<AupPumpPointStatus> selectAupPumpPointStatusList(AupPumpPointStatus aupPumpPointStatus);

    /**
     * 新增泵组状态值
     *
     * @param aupPumpPointStatus 泵组状态值
     * @return 结果
     */
    public int insertAupPumpPointStatus(AupPumpPointStatus aupPumpPointStatus);

    /**
     * 修改泵组状态值
     *
     * @param aupPumpPointStatus 泵组状态值
     * @return 结果
     */
    public int updateAupPumpPointStatus(AupPumpPointStatus aupPumpPointStatus);

    /**
     * 删除泵组状态值
     *
     * @param id 泵组状态值ID
     * @return 结果
     */
    public int deleteAupPumpPointStatusById(Long id);

    /**
     * 批量删除泵组状态值
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupPumpPointStatusByIds(String[] ids);

    public List<AupPumpPointStatus> selectAupPumpPointStatusByPoint(Long point);
}