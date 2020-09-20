package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupPower;
import com.feather.common.core.domain.LayuiTreeResult;
import com.feather.common.core.domain.Ztree;

import java.util.List;

/**
 * 配电房Service接口
 *
 * @author fancy
 * @date 2020-01-07
 */
public interface IAupPowerService {
    /**
     * 查询配电房
     *
     * @param id 配电房ID
     * @return 配电房
     */
    AupPower selectAupPowerById(Long id);

    /**
     * 查询配电房
     *
     * @param pid 配电房pid
     * @return 配电房
     */
    List<Ztree> selectAupPowerByPid(Long pid);

    /**
     * 查询配电房列表
     *
     * @param aupPower 配电房
     * @return 配电房集合
     */
    List<AupPower> selectAupPowerList(AupPower aupPower);

    /**
     * 查询配电房列表
     *
     * @param aupPower 配电房
     * @return 配电房集合
     */
    List<LayuiTreeResult> selectAupPowerListForLayuiTree(AupPower aupPower);

    List<LayuiTreeResult> selectAupPowerChildrenByIdList(AupPower aupPower);

    /**
     * 新增配电房
     *
     * @param aupPower 配电房
     * @return 结果
     */
    int insertAupPower(AupPower aupPower);

    /**
     * 修改配电房
     *
     * @param aupPower 配电房
     * @return 结果
     */
    int updateAupPower(AupPower aupPower);


    int deleteAupPower(AupPower aupPower);
    /**
     * 查询树结构配电房
     *
     * @param id id
     * @return 菜单列表
     */
    List<Ztree> initZtreePower(Long id);

    /**
     * 判断是否有子节点
     *
     * @param pid pid
     * @return true,false
     */
    boolean selectAupPowerCountByPid(Long pid);

    /**
     * 判断标识码是否唯一
     * @param power 对象
     * @return true,false
     */
    boolean checkAupPowerBsmNoUnque(AupPower power);


}