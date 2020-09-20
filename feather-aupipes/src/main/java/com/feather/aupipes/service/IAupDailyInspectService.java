package com.feather.aupipes.service;

import com.feather.common.core.domain.AjaxResult;

/**
 * @author sky
 * @date 2019/12/14 17:11
 */
public interface IAupDailyInspectService {

    /**
     * 查询巡检概况
     */
    public AjaxResult situation();

    /**
     * 巡检人员列表
     */
    public AjaxResult personnelList();

    /**
     * 巡检地点列表
     */
    public AjaxResult siteList();

    /**
     * 异常详情
     */
    public AjaxResult exceptionDetails();

    /**
     * 巡检记录
     */
    public AjaxResult inspectRecord();

    /**
     * 设备占比
     */
    public AjaxResult deviceProportion();

    /**
     * 区域占比
     */
    public AjaxResult areaProportion();

    /**
     * 异常数量
     */
    public AjaxResult exceptionNum();

    /**
     * 人员统计
     */
    public AjaxResult personnelStatistics();


}
