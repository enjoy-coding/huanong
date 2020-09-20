package com.feather.aupipes.service;

import com.feather.aupipes.domain.UseMeter;
import com.feather.common.core.domain.Ztree;
import com.feather.common.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface IScreenYxjkService {

    /**
     * 运行监控统计
     *
     * @return 结果
     */
    List<Map<String, Object>> getOperationMonitorCount();

    /**
     * 根据设备获取设备树结构
     *
     * @param pid
     * @return 结果
     */
    List<Ztree> getOthertTree(String pid);



    UseMeter nhjgUseMeter(int id, String address, String code);

    /**
     * 重点建筑物用量查询
     * @param year 年度
     * @param address 地址
     * @param code 编码
     * @return 结果
     */
    UseMeter nhjgImportantUseMeter(int year,String address, String code,int level,String important);

    /**
     * 水电树点击详情用能信息
     * @param typeid 类型id
     * @param queryType 查询类型
     * @return 结果
     */
    List<Map<String,Object>> queryMeterInfo(String typeid,String queryType);

    /**
     * 水电用户离线树
     */
    List<Ztree> queryMeterAreaUnline(String pid);

     /**
     * 水电用户全部树
     */
    List<Ztree> queryMeterAreaAll(String pid);

    /**
     * 实时获取所有控制器信息
     * @return 结果
     */
    JSONObject[] getAllControlInfo();
    /**
     * 实时获取所有控制器信息
     * @return 结果
     */
    JSONObject getControlInfoById(String sid,String cuid);

    /**
     * 获取路灯实时光照值，亮灯数
     * @return 结果
     */
    Map<String,Object> queryStreetlightTime();
   
}
