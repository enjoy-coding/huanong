package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.common.core.domain.Ztree;

import java.util.List;
import java.util.Map;

/**
 * @Auther: hmzhu
 * @Date: 2019/12/27 15:59
 * @Description:
 */
public interface IAupNhjgService {

    /**
     * 耗水,耗电,重点建筑
     */
     List<Ztree> selectZtreeBuildingByLessThanLd(String important);
    /**
     * 查询区域树结构列表
     *
     * @param aupBuildingArea 区域树结构
     * @return 区域树结构集合
     */
     List<AupBuildingArea> selectAupBuildingAreaList(AupBuildingArea aupBuildingArea);


    /**
     * 能耗监管面积用能统计
     */
     List<Map<String,Object>> queryAreaUseNumber(Map<String,Object> map);



}
