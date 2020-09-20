package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupPumpPointBase;
import java.util.List;

/**
 * 泵组基本数据值Mapper接口
 *
 * @author fancy
 * @date 2020-04-18
 */
public interface AupPumpPointBaseMapper
{
    /**
     * 查询泵组基本数据值
     *
     * @param id 泵组基本数据值ID
     * @return 泵组基本数据值
     */
    public AupPumpPointBase selectAupPumpPointBaseById(Long id);

    public AupPumpPointBase selectAupPumpPointBaseByPointId(Long pointId);

    /**
     * 查询泵组基本数据值列表
     *
     * @param aupPumpPointBase 泵组基本数据值
     * @return 泵组基本数据值集合
     */
    public List<AupPumpPointBase> selectAupPumpPointBaseList(AupPumpPointBase aupPumpPointBase);

    /**
     * 新增泵组基本数据值
     *
     * @param aupPumpPointBase 泵组基本数据值
     * @return 结果
     */
    public int insertAupPumpPointBase(AupPumpPointBase aupPumpPointBase);

    /**
     * 修改泵组基本数据值
     *
     * @param aupPumpPointBase 泵组基本数据值
     * @return 结果
     */
    public int updateAupPumpPointBase(AupPumpPointBase aupPumpPointBase);

    /**
     * 删除泵组基本数据值
     *
     * @param id 泵组基本数据值ID
     * @return 结果
     */
    public int deleteAupPumpPointBaseById(Long id);

    /**
     * 批量删除泵组基本数据值
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupPumpPointBaseByIds(String[] ids);

    public List<AupPumpPointBase> selectMaxTimeAupPumpPointBase(Long pointId);
}