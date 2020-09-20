package com.feather.aupipes.service;


import com.feather.aupipes.domain.AupWaterSide;
import com.feather.common.core.domain.LayuiTreeResult;
import com.feather.common.core.domain.Ztree;

import java.util.List;
/**
 * 水分区Service接口
 *
 * @author fancy
 * @date 2020-05-18
 */
public interface IAupWaterSideService {

    /**
     * 查询水分区
     *
     * @param id 水分区ID
     * @return 水分区
     */
    AupWaterSide selectAupWaterSideById(Long id);

    /**
     * 查询水分区列表
     *
     * @param aupWaterSide 水分区
     * @return 水分区集合
     */
    List<AupWaterSide> selectAupWaterSideList(AupWaterSide aupWaterSide);

    List<Ztree> selectZtreeAupWaterSide(Long pid,int level);

    List<AupWaterSide> selectHouseTree(Long pid);

    /**
     * 校验行政编码是否唯一
     * 
     * @param aupWaterSide 对象
     * @return 结果
     */
    boolean checkAupWaterSideTypeNoUnque(AupWaterSide aupWaterSide);

    /**
     * 判断当前节点的父节点在不包含当前节点的情况下是否还存在其他子节点
     */
    AupWaterSide checkAupWaterParentHasChildren(AupWaterSide aupWaterSide);

    /**
     * 判断当前节点是否存在字节的
     */
    boolean checkAupWaterSideHasChildren(Long id);
     /**
     * 新增水分区
     * 
     * @param aupWaterSide 水分区
     * @return 结果
     */
    int insertAupWaterSide(AupWaterSide aupWaterSide);

    /**
     * 修改水分区
     * 
     * @param aupWaterSide 水分区
     * @return 结果
     */
    int updateAupWaterSide(AupWaterSide aupWaterSide);

    /**
     * 删除水分区
     * 
     * @param id 水分区ID
     * @return 结果
     */
    int deleteAupWaterSideById(Long id);

     /**
     * 删除水分区
     * 
     * @param aupWaterSide 水分区对象
     * @return 结果
     */
    int deleteAupWaterSide(AupWaterSide aupWaterSide);

    /**
     * 根据条件查询树结构
     * @param aupWaterSide 水分区对象
     * @return 结果
     */
    List<LayuiTreeResult> selectAupWaterSideByTree(AupWaterSide aupWaterSide);

}
