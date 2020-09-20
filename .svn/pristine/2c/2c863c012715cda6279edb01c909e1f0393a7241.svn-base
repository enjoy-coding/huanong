package com.feather.aupipes.service;

import java.util.List;

import com.feather.aupipes.domain.AupBuildingWaterValve;
import com.feather.aupipes.domain.AupWaterValve;

/**
 * 水房屋回路Service接口
 */
public interface IAupBuildingWaterValveService {
    /**
     * 通过房屋查询
     * 
     * @param building
     *            楼栋
     * @return 关系集合
     */
    public List<AupBuildingWaterValve> selectByBuilding(String building);

    /**
     * 通过阀门查询
     * 
     * @param valve
     *            阀门
     * @return 关系集合
     */
    public List<AupBuildingWaterValve> selectByValve(String valve);

    /**
     * 新增关系
     * 
     * @param AupBuildingWaterValve
     *            关系
     * @return 结果
     */
    public int insertAupBuildingWaterValve(List<AupWaterValve> aupWaterValve);

    /**
     * 通过楼栋删除
     * 
     * @param building
     *            楼栋
     * @return 结果
     */
    public int deleteByBuilding(String building);

    /**
     * 通过阀门楼栋删除
     * 
     * @param valve
     *            阀门
     * @return 结果
     */
    public int deleteByValve(String valve);

    List<AupWaterValve> selectByTypeAndValue(String type, String value);

    List<AupWaterValve> selectByTypeAndValueForDown(String chooseType, String value);
}
