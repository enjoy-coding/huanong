package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupStreetlightDetail;
import java.util.List;
import java.util.Map;

/**
 * 灯具Service接口
 *
 * @author fancy
 * @date 2020-03-05
 */
public interface IAupStreetlightDetailService
{
    /**
     * 查询灯具
     *
     * @param luid 灯具ID
     * @return 灯具
     */
    AupStreetlightDetail selectAupStreetlightDetailByIuid(String luid);

    List<AupStreetlightDetail> selectAupStreetlightDetailExpList(String luid,String startTime,String endTime);

    boolean checkAupStreetlightDetailByIuid(AupStreetlightDetail aupStreetlightDetail);

    /**
     * 查询灯具列表
     *
     * @param aupStreetlightDetail 灯具
     * @return 灯具集合
     */
    List<AupStreetlightDetail> selectAupStreetlightDetailList(AupStreetlightDetail aupStreetlightDetail);

    /**
     * 新增灯具
     *
     * @param aupStreetlightDetail 灯具
     * @return 结果
     */
    int insertAupStreetlightDetail(AupStreetlightDetail aupStreetlightDetail);

    /**
     * 定时执行路灯数据
     */
    void addTimeLightDetail();

    Map<String,Object> getAupStreeLightTimeMonitor(String luid);

    void  deleteStreetlightDetailTime();

    Map<String,Object> getTypename(String typeid);

    Map<String,Object> getAupStreeLightTimeMonitorByLid(String lid);

}