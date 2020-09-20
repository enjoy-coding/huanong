package com.feather.aupipes.mapper;

import java.util.List;

import com.feather.aupipes.domain.AupWaterValve;
import org.apache.ibatis.annotations.Param;

import com.feather.aupipes.domain.AupBuildingWaterValve;

/**
 * 水阀与楼栋关系Mapper接口
 */
public interface AupBuildingWaterValveMapper {

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
    public List<AupBuildingWaterValve> selectByValve(String[] valve);

    /**
     * 新增关系
     * 
     * @param AupBuildingWaterValve
     *            关系
     * @return 结果
     */
    public int insertAupBuildingWaterValve(AupWaterValve aupWaterValve);

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

    /**
     * 通过楼栋和阀门删除
     * 
     * @param building
     *            楼栋
     * @param valve
     *            阀门
     * @return 结果
     */
    public int deleteByBuildingAndValve(@Param("building") String building, @Param("valve") String valve);

    /**
     * 通过楼栋或者阀门查询影响的阀门
     */
    List<AupWaterValve> selectByTypeAndValue(@Param("chooseType")String chooseType, @Param("value")String value);

    List<AupWaterValve> selectByTypeAndValueForDown(@Param("chooseType")String chooseType, @Param("value")String value);
}