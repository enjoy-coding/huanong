package com.feather.aupipes.mapper;

import java.util.List;
import java.util.Map;

/**
 * 统计
 */
public interface AupCountMapper {
    void updateEquipmentCount();


    List<Map<String,Object>> queryAreaUseNumber(Map<String,Object> map);



    /**
     * 用电总量和重点建筑用电量
     * @return
     */
    public Map<String,Object> queryTotalUseNumber(Map<String,Object> params);

    void insertAupArea();
    void insertHouseMeterInfo();

    /**
     * 用水分区
     */
    void insertWaterUseNumber();

    /**
     * 用地昂平衡结构
     */
    void insetElectricityUseNumber();
    void updateHouseMeterInfo();
    void updateMeterInfo();
    void updateMeterUseNumber();

    void updateTotalUseNumber();
}
