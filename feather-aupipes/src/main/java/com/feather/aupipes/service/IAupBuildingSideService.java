package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupBuildingSide;
import com.feather.common.core.domain.Ztree;

import java.util.List;
import java.util.Map;

/**
 * 楼栋分区Service接口
 *
 * @author fancy
 * @date 2020-03-25
 */
public interface IAupBuildingSideService
{

    /**
     * 查询楼栋分区列表
     *
     * @param aupBuildingSide 楼栋分区
     * @return 楼栋分区集合
     */
    List<AupBuildingSide> selectAupBuildingSideList(AupBuildingSide aupBuildingSide);

    List<AupBuildingSide> selectAupBuildingSideListLdjd(AupBuildingSide aupBuildingSide);
    
    /**
     * 重点建筑分区
     * @param id id
     * @param level_ 等级
     * @param important 重点建筑标识
     * @param elementById 节点id
     * @return List<Ztree>
     */
    List<Ztree> initWaterAndElectricityZtrees(int id,int level_,String important,String elementById);

    /**
     * 用电总量和重点建筑用电量
     * @return Map<String,Object>
     */
    Map<String,Object> queryTotalUseNumber(Map<String,Object> params);

    List<Map> getBuildSide(String areano);


}