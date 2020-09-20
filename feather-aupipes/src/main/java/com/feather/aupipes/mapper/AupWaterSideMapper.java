package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupWaterSide;
import java.util.List;
/**
 * 水分区Mapper接口
 *
 * @author fancy
 * @date 2020-05-18
 */
public interface AupWaterSideMapper{
    /**
     * 查询水分区
     *
     * @param id 水分区ID
     * @return 水分区
     */
    public AupWaterSide selectAupWaterSideById(Long id);

    /**
     * 查询水分区列表
     *
     * @param aupWaterSide 水分区
     * @return 水分区集合
     */
    public List<AupWaterSide> selectAupWaterSideList(AupWaterSide aupWaterSide);
    /**
     * 判断当前节点的父节点在不包含当前节点的情况下是否还存在其他子节点
     */
    public AupWaterSide checkAupWaterParentHasChildren(AupWaterSide aupWaterSide);

    /**
     * 根据条件查询树形结构
     */
    public List<AupWaterSide> selectAupWaterSideTreeList(AupWaterSide aupWaterSide);

    public List<AupWaterSide> selectHouseTree(Long pid);

    /**
     * 检查行政编码是否唯一
     * @return
     */
    public AupWaterSide checkAupWaterSideTypeNoUnque(String typeNo);

    /**
     * 判断当前节点是否存在字节的
     */
    public int checkAupWaterSideHasChildren(Long id);

     /**
     * 新增水分区
     * 
     * @param aupWaterSide 水分区
     * @return 结果
     */
    public int insertAupWaterSide(AupWaterSide aupWaterSide);

    /**
     * 修改水分区
     * 
     * @param aupWaterSide 水分区
     * @return 结果
     */
    public int updateAupWaterSide(AupWaterSide aupWaterSide);

    /**
     * 删除水分区
     * 
     * @param id 水分区ID
     * @return 结果
     */
    public int deleteAupWaterSideById(Long id);
}
