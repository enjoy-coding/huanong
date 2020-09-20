package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupElectricitySide;

import java.util.List;

/**
 * 用电分区Mapper接口
 *
 * @author fancy
 * @date 2020-06-09
 */
public interface AupElectricitySideMapper
{
    /**
     * 查询用电分区
     *
     * @param id 用电分区ID
     * @return 用电分区
     */
    public AupElectricitySide selectAupElectricitySideById(Long id);

    /**
     * 查询用电分区列表
     *
     * @param aupElectricitySide 用电分区
     * @return 用电分区集合
     */
    public List<AupElectricitySide> selectAupElectricitySideList(AupElectricitySide aupElectricitySide);

    /**
     * 查询用电分区列表
     *
     * @param aupElectricitySide 用电分区
     * @return 用电分区集合
     */
    public List<AupElectricitySide> selectAupElectricitySideListByPid(AupElectricitySide aupElectricitySide);

    /**
     * 新增用电分区
     *
     * @param aupElectricitySide 用电分区
     * @return 结果
     */
    public int insertAupElectricitySide(AupElectricitySide aupElectricitySide);

    /**
     * 修改用电分区
     *
     * @param aupElectricitySide 用电分区
     * @return 结果
     */
    public int updateAupElectricitySide(AupElectricitySide aupElectricitySide);

    /**
     * 删除用电分区
     *
     * @param id 用电分区ID
     * @return 结果
     */
    public int deleteAupElectricitySideById(Long id);

    /**
     * 批量删除用电分区
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupElectricitySideByIds(String[] ids);

    public List<AupElectricitySide> selectHouseTree(Long pid);

    public int checkAupElectricitySideHasChildren(Long id);
}