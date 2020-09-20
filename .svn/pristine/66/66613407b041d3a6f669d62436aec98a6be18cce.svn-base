package com.feather.smart.service;

import java.util.List;
import java.util.Map;

/**
 * 社区资产Service接口
 *
 * @author fancy
 * @date 2020-05-14
 */
public interface IZhsqSqglService
{
    public List<Map> getCountGk(String sqid,String xqid);
    public List<Map> getCountJc(String sqid,String xqid);
    public List<Map> getCountLx(String sqid,String xqid);

    /**
     * 左侧人员统计
     * @param maps
     * @return
     */
    public  Map<String,Object> selectZcryCount(Map<String,Object> maps);

    /**
     * 年龄结构统计
     * @param maps
     * @return
     */
    public  Object[] getNlCount(Map<String,Object> maps);

    /**
     * 男女比例
     * @param maps
     * @return
     */
    public List<Map<String,Object>> getNnBlCount(Map<String,Object> maps);

    /**
     *民族比例
     * @param maps
     * @return
     */
    public List<Map<String,Object>> getMzBlCount(Map<String,Object> maps);

    /**
     * 人员列表
     * @param maps
     * @return
     */
    public List<Map<String,Object>> getRyList(Map<String,Object> maps);

    /**
     * 手环信息
     * @param maps 人员参数
     * @return 该人员的手环信息
     */
    public Map<String,Object> getBraceletInfo(Map<String, Object> maps);

    /**
     * 定位信息
     * @param maps 人员参数
     * @return 该人员的定位信息
     */
    public Map<String,Object> getLocationInfo(Map<String,Object> maps);

    /**
     * 手环信息
     * @param maps 人员参数
     * @return 该人员的手环信息
     */
    public Map<String,Object> getBreatHeartInfo(Map<String, Object> maps);

    /**
     * 获取报警信息
     * @param map
     * @return
     */
    public Map<String,Object> getWarringInfo(Map<String,Object> map);
    /**
     * 人员信息
     * @param maps
     * @return
     */
    public Map<String,Object> getRyInfo(Map<String,Object> maps);

    /**
     * 重点事件详情
     * @param maps
     * @return
     */
    public Map<String,Object> getZdsjInfo(Map<String,Object> maps);

    /**
     * 巡检任务详情
     * @param maps
     * @return
     */
    public Map<String,Object> getXjrwInfo(Map<String,Object> maps);

    /**
     * 左侧房屋统计
     * @param maps
     * @return
     */
    public  Map<String,Object> selectZcfwCount(Map<String,Object> maps);

    /**
     * 左侧房屋图表统计
     * @param maps
     * @return
     */
    public  List<Map<String,Object>> selectZcfwTbCount(Map<String,Object> maps);

    /**
     * 房屋列表
     * @param maps
     * @return
     */
    public List<Map<String,Object>> getFwList(Map<String,Object> maps);

    /**
     * 楼栋列表
     * @param maps
     * @return
     */
    public List<Map<String,Object>> getLdList(Map<String,Object> maps);

    /**
     * 房屋信息
     * @param maps
     * @return
     */
    public Map<String,Object> getFwInfo(Map<String,Object> maps);

    /**
     * 楼栋弹框信息
     * @param maps
     * @return
     */
    public Map<String,Object> getLdInfo(Map<String,Object> maps);
}