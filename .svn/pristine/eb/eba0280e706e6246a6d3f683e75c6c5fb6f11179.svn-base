package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupElectricitySide;
import com.feather.common.core.domain.Ztree;

import java.util.List;

/**
 * 用电分区Service接口
 *
 * @author fancy
 * @date 2020-06-09
 */
public interface IAupElectricitySideService
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
     * 删除后更新父节点
     * @param aupElectricitySide
     * @return
     */
    public int deleteAupElectricitySide(AupElectricitySide aupElectricitySide);

    /**
     * 根据id获取树形结构
     * @param pid
     * @return
     */
    public List<Ztree> selectZtreeAupElectricitySide(Long pid);


    /**
     * 检查是否存在根节点
     * @param id
     * @return
     */
    public boolean checkAupElectricitySideHasChildren(Long id);
}