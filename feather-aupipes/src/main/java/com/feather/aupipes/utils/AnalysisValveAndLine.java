package com.feather.aupipes.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.feather.aupipes.domain.query.Jsgxhl;
import com.feather.aupipes.domain.query.Line;
import com.feather.aupipes.domain.query.Point;

/**
 * @author tql
 * @description 分析爆管最近阀门及相关线路
 * @date 2019年12月19日14:43:12
 */
public class AnalysisValveAndLine {

    /**
     * 功能描述 分析爆管处影响管线信息
     * 
     * @author tql
     * @date 2019/12/19
     * @param rline
     *            影响爆管线段集合；startPoint 爆管处管线段开始点；smap 起始点对应管线段信息;emap
     *            结束点对应管线段信息；amap 已经分析过的点新 templines 已经分析过的线信息
     * @return java.util.List<com.kanq.pipe.domain.query.Line> 受爆管线影响的管线段信息
     */
    public static List<Line> anlysisLine(List<Line> rline, String startPoint, Map<String, List<Line>> smap,
            Map<String, List<Line>> emap, Map<String, Point> pmap, Map<String, Point> amap,
            Map<String, Line> templines) {
        // System.out.println(startPoint);
        List<Line> lines = new ArrayList<>();
        if (smap.get(startPoint) != null) {
            lines.addAll(smap.get(startPoint));
        }
        if (emap.get(startPoint) != null) {
            lines.addAll(emap.get(startPoint));
        }
        amap.put(startPoint, pmap.get(startPoint));
        if (lines != null && lines.size() > 0) {
            for (int i = 0, size = lines.size(); i < size; i++) {
                Line line = lines.get(i);
                Point spoint = pmap.get(line.getS_point());
                Point epoint = pmap.get(line.getE_point());
                Line templine = templines.get(String.valueOf(line.getOid()));
                if (templine == null) {
                    rline.add(line);
                    // System.out.println(line.getOid());
                    templines.put(String.valueOf(line.getOid()), line);
                }
                if ("阀门".equals(spoint.getSubsid()) || "阀门井".equals(spoint.getSubsid())
                        || "水表".equals(spoint.getSubsid()) || "水表井".equals(spoint.getSubsid())) {
                    // rline.add(line);
                    continue;
                } else if ("阀门".equals(epoint.getExp_no()) || "阀门井".equals(epoint.getSubsid())
                        || "水表".equals(epoint.getExp_no()) || "水表井".equals(epoint.getExp_no())) {
                    // rline.add(line);
                    continue;
                } else {
                    Point p1 = amap.get(epoint.getExp_no());
                    if (p1 == null) {
                        anlysisLine(rline, epoint.getExp_no(), smap, emap, pmap, amap, templines);
                    }
                    Point p2 = amap.get(spoint.getExp_no());
                    if (p2 == null) {
                        anlysisLine(rline, spoint.getExp_no(), smap, emap, pmap, amap, templines);
                    }
                }
            }
        }
        return rline;
    }

    /**
     * 功能描述 分析爆管处影响的阀门点信息
     * 
     * @author tql
     * @date 2019/12/24
     * @param rpoint
     *            影响爆管线点集合；startPoint 爆管处管线段开始点；smap 起始点对应管线段信息;emap
     *            结束点对应管线段信息；amap 已经分析过的点新
     * @return java.util.List<com.kanq.pipe.domain.query.Point>
     */
    public static List<Point> anlysisPoint(List<Point> rpoint, String startPoint, Map<String, List<Line>> smap,
            Map<String, List<Line>> emap, Map<String, Point> pmap, Map<String, Point> amap) {
        List<Line> lines = new ArrayList<>();
        if (smap.get(startPoint) != null) {
            lines.addAll(smap.get(startPoint));
        }
        if (emap.get(startPoint) != null) {
            lines.addAll(emap.get(startPoint));
        }
        amap.put(startPoint, pmap.get(startPoint));
        if (lines != null && lines.size() > 0) {
            for (int i = 0, size = lines.size(); i < size; i++) {
                Line line = lines.get(i);
                Point spoint = pmap.get(line.getS_point());
                Point epoint = pmap.get(line.getE_point());
                if ("阀门".equals(spoint.getSubsid()) || "阀门井".equals(spoint.getSubsid())
                        || "水表".equals(spoint.getSubsid()) || "水表井".equals(spoint.getSubsid())) {
                    rpoint.add(spoint);
                    continue;
                } else if ("阀门".equals(epoint.getSubsid()) || "阀门井".equals(epoint.getSubsid())
                        || "水表".equals(epoint.getSubsid()) || "水表井".equals(epoint.getSubsid())) {
                    rpoint.add(epoint);
                    continue;
                } else {
                    Point p1 = amap.get(epoint.getExp_no());
                    if (p1 == null) {
                        anlysisPoint(rpoint, epoint.getExp_no(), smap, emap, pmap, amap);
                    }
                    Point p2 = amap.get(spoint.getExp_no());
                    if (p2 == null) {
                        anlysisPoint(rpoint, spoint.getExp_no(), smap, emap, pmap, amap);
                    }
                }
            }
        }
        return rpoint;
    }

    /**
     * 功能描述 根据爆管分析影响的阀门及阀门层级关系分析，爆管处影响范围内的阀门信息
     * 
     * @author tql
     * @date 2020/3/26
     * @param vp
     *            爆管处影响的关阀闭环需要关闭的阀门，jshl 给水阀门层级关系，allpoint 给水管线所有管点信息集合
     * @return java.util.List<com.kanq.pipe.domain.query.Point>
     */
    public static List<Point> anlysisValve(List<Point> vp, Map<String, List<Jsgxhl>> jshl,
            Map<String, Point> allpoint) {
        for (int i = 0, j = vp.size(); i < j; i++) {
            Point p = vp.get(i);
            List<Jsgxhl> ejv = jshl.get(p.getExp_no());
            if (ejv != null && ejv.size() > 0) {
                List<Point> tvp = hlgxToPoint(ejv, allpoint);
                if (tvp != null) {
                    tvp.removeAll(vp);
                    vp.addAll(tvp);
                    anlysisValve(tvp, jshl, allpoint);
                } else {
                    // System.out.println("tvp为null");
                }

            }
        }
        return vp;
    }

    /**
     * 功能描述 将二级阀门信息转成给水管线点信息集合
     * 
     * @author tql
     * @date 2020/3/26
     * @param gxhls
     *            二级阀门信息集合 allpoint 给水管线点信息集合
     * @return java.util.List<com.kanq.pipe.domain.query.Point>
     */
    public static List<Point> hlgxToPoint(List<Jsgxhl> gxhls, Map<String, Point> allpoint) {
        List<Point> list = new ArrayList<>();
        for (int i = 0, j = gxhls.size(); i < j; i++) {
            Jsgxhl gxhl = gxhls.get(i);
            Point point = allpoint.get(gxhl.getXyfmid());
            if (point != null) {
                list.add(allpoint.get(gxhl.getXyfmid()));
            } else {
                // System.out.println("没有点信息的阀门："+gxhl.getXyfmid());
            }
        }
        return list;
    }

    public static List<Line> anlysisLineByValve(List<Line> rline, List<Point> vp, Map<String, List<Jsgxhl>> jshl,
            Map<String, List<Line>> startmap, Map<String, List<Line>> endmap, Map<String, Point> pmap,
            Map<String, Point> amap, Map<String, Line> templines) {
        for (int i = 0, j = vp.size(); i < j; i++) {
            Point tp = vp.get(i);
            if (tp != null) {
                List<Line> tline = new ArrayList<>();
                tline = anlysisLine(tline, tp.getExp_no(), startmap, endmap, pmap, amap, templines);
                tline.removeAll(rline);
                rline.addAll(tline);
            }
        }
        return rline;
    }

}
