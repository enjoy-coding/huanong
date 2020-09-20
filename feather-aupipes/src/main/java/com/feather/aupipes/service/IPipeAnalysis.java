package com.feather.aupipes.service;

import com.feather.aupipes.domain.query.Line;
import com.feather.aupipes.domain.query.Point;


import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *
 * @Author: fang xinliang
 * @Date: 2019/12/18 13:47
 */
public interface IPipeAnalysis {
    /// <summary>
    /// 获取爆管分析结果
    /// </summary>
    /// <param name="model">爆管分析参数模型</param>
    /// <returns>爆管分析结果集合</returns>
    Map GetBurstAnalysis(double x, double y);

    /**
    *功能描述   根据oid或name查询爆管处对应管线段
    * @author tql
    * @date 2019/12/18
    * @param oid id
    * @return java.util.List<domain.query.Line>
    */
    List<Line> getLine(int oid, String name);

    /**
    *功能描述   查询所有管线段信息
    * @author tql
    * @date 2019/12/18
    * @return 集合
    */
    List<Line> getLine();

    /**
    *功能描述 查询所有管线点信息
    * @author tql
    * @date 2019/12/18
    * @return java.util.List<domain.query.Point>
    */
    List<Point> getPoint();

    /**
    *功能描述 查询给水管线点阀门及阀门井信息
    * @author tql
    * @date 2019/12/26
    * @return java.util.List<com.kanq.pipe.domain.query.Point>
    */
    List<Point> getValve();

    /**
     *  排管分析
     * @param line 线
     * @param radius 角度
     * @param maishen 埋深
     * @param lineType 线类型
     * @param layoutType 类型
     * @param buff buff
     * @return map对象
     */
    Map CapacityAnalysis(String line, double radius, double maishen, String lineType, String layoutType, double buff);
}
