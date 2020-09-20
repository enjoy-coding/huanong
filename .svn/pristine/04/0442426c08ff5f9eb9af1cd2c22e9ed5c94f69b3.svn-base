package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupLdqk;
import com.feather.aupipes.domain.AupPower;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IAupIndexService {

    /**
     * 设备统计
     */
    List<Map<String, Object>> getEquipmentCount();

    /**
     * 预警统计
     * 
     * @return
     */
    int[] getWarringCount();



    Map<String, Object> getEquipmtCount(int online,int total);
    /**
     * 路灯情况
     * 
     * @return
     */
    Map<String, Object> getLightsDetail();

    /**
     * 预案总数
     * 
     * @return
     */
    Long getPlanNum();

    /**
     * 智能安防
     * 
     * @return
     */
    Map<String, Object> getSecurity();


    /**
     * 预警处置
     * 
     * @return
     */
    ArrayList<String> getYjcz();

    /**
     * 路灯实时开关灯信息
     * @return
     */
    AupLdqk getRealTimeLdqk();
  
    /**
     * 查询泵房基本信息
     * @return
     */
     List<Map<String,Object>> queryPumpNumber();

    /**
     * 一级配电房，回路数，变压器
     * @return
     */
    List<AupPower> queryPowerHouseInfo();

    Map<String, Object> getNhtjUseNumber();

}
