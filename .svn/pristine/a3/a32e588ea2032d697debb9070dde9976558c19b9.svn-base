package com.feather.aupipes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.domain.query.Fw;
import com.feather.aupipes.domain.query.Hlgx;
import com.feather.aupipes.domain.query.Jsgxgx;
import com.feather.aupipes.domain.query.Jsgxhl;
import com.feather.aupipes.domain.query.Jspoint;
import com.feather.aupipes.domain.query.Line;
import com.feather.aupipes.domain.query.Pdf;
import com.feather.aupipes.domain.query.Point;
import com.feather.aupipes.service.GdService;
import com.feather.aupipes.service.IPipeAnalysis;
import com.feather.aupipes.service.JsService;
import com.feather.aupipes.service.TraSectionAnalysisService;
import com.feather.aupipes.utils.AnalysisValveAndLine;
import com.feather.aupipes.utils.CapacityAnalysis;
import com.feather.aupipes.utils.JsValveToMap;
import com.feather.aupipes.utils.ListToMap;
import com.feather.aupipes.utils.PineLineStandardDistance;
import com.feather.aupipes.utils.TranceResult;
import com.feather.common.core.domain.AjaxResult;

/**
 * 功能描述：
 *
 * @Author: fang xinliang
 * @Date: 2019/12/18 10:22
 */
@Controller
@RequestMapping("/pipe")
public class PipeAnalysis {

    @Autowired
    protected IPipeAnalysis ipaService;
    @Autowired
    private TranceResult tranceResult;
    @Autowired
    private CapacityAnalysis capacityAnalysis;
    @Autowired
    private PineLineStandardDistance pineLineStandardDistance;

    @Autowired
    private GdService gdService;

    @Autowired
    private JsService jsService;
    @Autowired
    private TraSectionAnalysisService traSectionAnalysisService;

    /**
     * 爆管分析
     *
     * @param
     * @return
     */
    @GetMapping("/burstAnalysis")
    @ResponseBody
    public AjaxResult BurstAnalysis(double x, double y) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("data", ipaService.GetBurstAnalysis(x, y));
        ajaxResult.put("code", 0);
        return ajaxResult;
    }

    /**
     * 排管分析
     *
     * @param lineType
     * @param layerOutType
     * @param radius
     * @param buff
     * @param depth
     * @param lineStr
     * @return
     */
    @PostMapping("/capacityAnalysis")
    @ResponseBody
    public AjaxResult CapacityAnalysis(String lineType, String layerOutType, String roadsCategory, String radius,
            double buff, double depth, String pressure, String lineStr) {
        AjaxResult ajaxResult = new AjaxResult();
        String bufferGeo = capacityAnalysis.doBuffer(buff, lineStr);
        List<Map<String, Object>> list = capacityAnalysis.doQuery(bufferGeo);
        list = capacityAnalysis.GetDistance(lineType, layerOutType, radius, buff, depth, pressure, lineStr, list);
        ajaxResult.put("distance_data", list);
        double s_depth = pineLineStandardDistance.GetCoverageStandard(lineType, layerOutType, roadsCategory);
        Map<String, Object> map = new HashMap<>();
        map.put("s_depth", s_depth);
        if (s_depth > depth) {
            map.put("msg", "覆土深度规范分析,埋设不符合标准");
        } else {
            map.put("msg", "覆土深度规范分析,埋设符合标准");
        }
        ajaxResult.put("depth_data", map);
        ajaxResult.put("code", 0);
        return ajaxResult;
    }

    /**
     * 覆土深度规范分析
     *
     * @param lineType
     *            管道类型
     * @param roadsCategory
     *            人行道，车行道
     * @param directly
     *            是否直埋
     * @param depth
     *            埋深
     * @return
     */
    @PostMapping("/getstandardTableCov")
    @ResponseBody
    public AjaxResult GetstandardTableCov(String lineType, String roadsCategory, String directly, double depth) {
        AjaxResult ajaxResult = new AjaxResult();
        double s_depth = pineLineStandardDistance.GetCoverageStandard(lineType, directly, roadsCategory);
        if (s_depth > depth) {
            ajaxResult.put("data", "埋设不符合标准");
        } else {
            ajaxResult.put("data", "埋设符合标准");
        }
        ajaxResult.put("depth", s_depth);
        ajaxResult.put("code", 0);
        return ajaxResult;
    }

    @GetMapping("/getValve")
    @ResponseBody
    public AjaxResult getValveAndLine(@RequestParam("oid") Integer oid) {
        AjaxResult ajaxResult = AjaxResult.success();
        List<Line> burstLine = new ArrayList<>();
        burstLine = ipaService.getLine(oid, "");
        List<Line> allLine = ipaService.getLine();
        List<Point> allPoint = ipaService.getPoint();
        Map<String, Map<String, List<Line>>> linemap = ListToMap.listLineToMap(allLine);
        Map<String, Point> pointmap = ListToMap.listPointToMap(allPoint);
        Map<String, List<Line>> startmap = linemap.get("smap");
        Map<String, List<Line>> endmap = linemap.get("emap");
        Map<String, Point> amap = new HashMap<>();
        String burstStartPoint = burstLine.get(0).getS_point();
        String burstEndPoint = burstLine.get(0).getE_point();
        List<Line> rline = new ArrayList<>();
        List<Point> rpoint = new ArrayList<>();
        List<Line> rline1 = new ArrayList<>();
        List<Point> rpoint1 = new ArrayList<>();
        Map<String, Line> templines = new HashMap<>();
        rline = AnalysisValveAndLine.anlysisLine(rline, burstStartPoint, startmap, endmap, pointmap, amap, templines);
        // templines = new HashMap<>();
        rline1 = AnalysisValveAndLine.anlysisLine(rline1, burstEndPoint, startmap, endmap, pointmap, amap, templines);
        // rpoint =
        // AnalysisValveAndLine.anlysisPoint(rline,rpoint,burstStartPoint,startmap,endmap,pointmap);
        rline.addAll(rline1);
        amap = new HashMap<>();
        rpoint = AnalysisValveAndLine.anlysisPoint(rpoint, burstStartPoint, startmap, endmap, pointmap, amap);
        // amap = new HashMap<>();
        rpoint1 = AnalysisValveAndLine.anlysisPoint(rpoint1, burstEndPoint, startmap, endmap, pointmap, amap);
        // System.out.println("测试");

        rpoint.addAll(rpoint1);
        if (rpoint.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("oid in ( ");
            for (Point point : rpoint) {
                sb.append(point.getOid() + ",");
            }
            sb = sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            ajaxResult.put("point", tranceResult.QueryPointByOID(sb.toString()));
        } else {
            ajaxResult.put("point", null);
        }

        if (rline.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("oid in ( ");
            for (Line line : rline) {
                sb.append(line.getOid() + ",");
            }
            sb = sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            ajaxResult.put("line", tranceResult.QueryLintByOID(sb.toString()));
        } else {
            ajaxResult.put("line", null);
        }
        ajaxResult.put("code", 0);
        return ajaxResult;
    }

    @GetMapping("/getValve3")
    @ResponseBody
    public AjaxResult getValveByPoint(@RequestParam("oid") Integer oid) {
        AjaxResult ajax = AjaxResult.success();
        AjaxResult ajaxResult = AjaxResult.success();
        List<Line> burstLine = new ArrayList<>();
        burstLine = ipaService.getLine(oid, "");
        List<Line> allLine = ipaService.getLine();
        List<Point> allPoint = ipaService.getPoint();
        Map<String, Map<String, List<Line>>> linemap = ListToMap.listLineToMap(allLine);
        Map<String, Point> pointmap = ListToMap.listPointToMap(allPoint);
        Map<String, List<Line>> startmap = linemap.get("smap");
        Map<String, List<Line>> endmap = linemap.get("emap");
        Map<String, Point> amap = new HashMap<>();
        String burstStartPoint = burstLine.get(0).getS_point();
        String burstEndPoint = burstLine.get(0).getE_point();
        List<Line> rline = new ArrayList<>();
        List<Point> rpoint = new ArrayList<>();
        List<Line> rline1 = new ArrayList<>();
        List<Point> rpoint1 = new ArrayList<>();
        List<Point> closevalve = new ArrayList<>();
        List<Point> syvalve = new ArrayList<>();
        Map<String, Line> templines = new HashMap<>();
        rline = AnalysisValveAndLine.anlysisLine(rline, burstStartPoint, startmap, endmap, pointmap, amap, templines);
        rline1 = AnalysisValveAndLine.anlysisLine(rline1, burstEndPoint, startmap, endmap, pointmap, amap, templines);
        rline.addAll(rline1);
        amap = new HashMap<>();
        rpoint = AnalysisValveAndLine.anlysisPoint(rpoint, burstStartPoint, startmap, endmap, pointmap, amap);
        rpoint1 = AnalysisValveAndLine.anlysisPoint(rpoint1, burstEndPoint, startmap, endmap, pointmap, amap);
        List<Jsgxhl> jslist = new ArrayList<>();
        jslist = jsService.getJsgxhl();

        Map<String, List<Jsgxhl>> jshls = JsValveToMap.valveListToMap(jslist);

        closevalve.addAll(rpoint);
        closevalve.addAll(rpoint1);
        rpoint.addAll(rpoint1);
        // closevalve = rpoint;
        // for(int i = 0,j = closevalve.size();i < j;i++){
        // Point tp = closevalve.get(i);
        // List<Jsgxhl> txyfm = new ArrayList<>();
        // txyfm = jshls.get(tp.getExp_no());
        // if(txyfm == null || txyfm.size() ==0){
        // syvalve.add(tp);
        // }
        // }
        List<Point> vresult = new ArrayList<>();
        vresult = AnalysisValveAndLine.anlysisValve(rpoint, jshls, pointmap);
        rline = AnalysisValveAndLine.anlysisLineByValve(rline, rpoint, jshls, startmap, endmap, pointmap, amap,
                templines);
        // System.out.println("AAAA");
        StringBuilder sb = new StringBuilder();
        sb.append("oid in ( ");
        for (Line tempLine : rline) {
            sb.append(tempLine.getOid() + ",");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        StringBuilder vsb = new StringBuilder();
        vsb.append("oid in ( ");
        for (Point tempPoint : vresult) {
            vsb.append(tempPoint.getOid() + ",");
        }
        vsb = vsb.deleteCharAt(vsb.length() - 1);
        vsb.append(")");
        StringBuilder csb = new StringBuilder();
        csb.append("oid in (");
        for (Point temppoint : closevalve) {
            csb.append(temppoint.getOid() + ",");
        }
        csb = csb.deleteCharAt(csb.length() - 1);
        csb = csb.append(")");
        ajax.put("valve", tranceResult.doQuery("给水点", "", vsb.toString()));
        ajax.put("line", tranceResult.doQuery("给水管线", "", sb.toString()));
        ajax.put("closevalve", tranceResult.doQuery("给水点", "", csb.toString()));
        List<Jsgxgx> jsfw = new ArrayList<>();
        jsfw = jsService.getJsgxgx(vresult);
        ajax.put("fw", jsfw);
        return ajax;
    }

    @GetMapping("/getLine")
    @ResponseBody
    public AjaxResult getGdLine(@RequestParam("fwid") String fwid) {
        // tranceResult.doQuery ("供电管线","","");

        AjaxResult aj = AjaxResult.success();
        List<Fw> fw = gdService.getFwInfo(fwid);
        List<Point> point = gdService.getGdPoint();
        List<Line> line = gdService.getGdLine();
        Map<String, Map<String, List<Line>>> lines = ListToMap.listLineToMap(line);
        if (fw.size() > 0) {
            String fwjxid = fw.get(0).getJxid();
            String fwcxid = fw.get(0).getCxid();
            Map<String, List<Line>> sline = lines.get("smap");
            Map<String, List<Line>> eline = lines.get("emap");
            Map<String, String> points = new HashMap<>();
            points.put(fwjxid, fwjxid);
            List<Line> result = new ArrayList<>();
            List<Line> templine = eline.get(fwjxid);
            // if(templine == null){
            // templine = sline.get(fwjxid);
            points.put(fwjxid, fwjxid);
            // }
            Map<String, Line> rmap = new HashMap<>();
            if (templine != null) {
                result = getLines(result, templine, sline, eline, fwcxid, points, rmap);
                StringBuilder sb = new StringBuilder();
                sb.append("oid in ( ");
                for (Line tempLine : result) {
                    sb.append(tempLine.getOid() + ",");
                }
                sb = sb.deleteCharAt(sb.length() - 1);
                sb.append(")");
                aj.put("data", tranceResult.doQuery("供电管线", "", sb.toString()));
            }
            // aj.put("data", result);
        }

        return aj;
    }

    @GetMapping("/getdata")
    @ResponseBody
    public AjaxResult getDataByPdf(@RequestParam("pdfid") String pdfid) {
        AjaxResult ajax = AjaxResult.success();
        List<Fw> pdfinfo = gdService.getPdfInfo(pdfid);
        List<Point> point = gdService.getGdPoint();
        List<Line> line = gdService.getGdLine();
        Map<String, Map<String, List<Line>>> lines = ListToMap.listLineToMap(line);
        Map<String, List<Line>> sline = lines.get("smap");
        Map<String, List<Line>> eline = lines.get("emap");
        List<Pdf> pdfs = gdService.getPdfs();
        Map<String, String> mpdf = ListToMap.ListPdfToMap(pdfs);
        List<Line> result = new ArrayList<>();
        Map<String, Object> aline = new HashMap<>();
        List<String> pdfresult = new ArrayList<>();
        List<String> fwresult = new ArrayList<>();
        Map<String, String> apoint = new HashMap<>();
        Map<String, String> alpoint = new HashMap<>();
        Map<String, String> jxids = new HashMap<>();
        if (pdfinfo.size() > 0) {
            for (int i = 0, len = pdfinfo.size(); i < len; i++) {
                Fw tfw = pdfinfo.get(i);
                fwresult.add(tfw.getFwid());
                jxids.put(tfw.getJxid(), tfw.getJxid());
            }
            Fw fw = pdfinfo.get(0);
            String pdfcxid = fw.getCxid();
            result = getPdfData(pdfcxid, sline, eline, result, aline, apoint, jxids);
            pdfresult = getPdfs(pdfcxid, sline, eline, pdfresult, alpoint, mpdf);

            // ajax.put("line", result);
            StringBuilder sb = new StringBuilder();
            sb.append("oid in ( ");
            for (Line tempLine : result) {
                sb.append(tempLine.getOid() + ",");
            }
            sb = sb.deleteCharAt(sb.length() - 1);
            sb.append(")");

            ajax.put("line", tranceResult.doQuery("供电管线", "", sb.toString()));

            // ajax.put("pdf", pdfresult);
            // ajax.put("fw", fw);
            ajax.put("pdf",
                    tranceResult.doQuery("供电管线点", "", org.apache.commons.lang3.StringUtils.join(pdfresult, ",")));
            ajax.put("fw", tranceResult.doQuery("供电管线点", "", org.apache.commons.lang3.StringUtils.join(fwresult, ",")));

        }
        return ajax;
    }

    /**
     * 横断面分析
     * 
     * @param lineType
     * @param lineStr
     * @return
     */
    @GetMapping("/traSectionAnalysis")
    @ResponseBody
    public AjaxResult TraSectionAnalysis(String lineType, String lineStr) {
        AjaxResult ajaxResult = new AjaxResult();
        List<Map<String, Object>> list = traSectionAnalysisService.DoTraSectionAnalysis(lineType, lineStr);
        if (list == null) {
            ajaxResult.put("code", 500);
        } else {
            ajaxResult.put("code", 0);
        }
        ajaxResult.put("data", list);
        return ajaxResult;
    }

    @GetMapping("/gethldata")
    @ResponseBody
    public AjaxResult getDataByHl(@RequestParam("hlid") String hlid) {
        AjaxResult ajax = AjaxResult.success();
        List<Hlgx> hlgxs = gdService.getHlgxs(hlid);
        List<Point> point = gdService.getGdPoint();
        List<Line> line = gdService.getGdLine();
        Map<String, Map<String, List<Line>>> lines = ListToMap.listLineToMap(line);
        Map<String, List<Line>> sline = lines.get("smap");
        Map<String, List<Line>> eline = lines.get("emap");

        List<String> pdfresult = new ArrayList<>();
        List<String> fwresult = new ArrayList<>();
        List<Line> result = new ArrayList<>();
        if (hlgxs.size() > 0) {
            for (int i = 0, j = hlgxs.size(); i < j; i++) {
                Hlgx hl = hlgxs.get(i);
                fwresult.add(String.valueOf(hl.getFwid()));
                // System.out.println("fwid:" + String.valueOf(hl.getFwid()));
                Map<String, String> apoint = new HashMap<>();
                Map<String, Object> aline = new HashMap<>();
                List<Line> tresult = new ArrayList<>();
                tresult = analysisHlgx(hl.getCxid(), hl.getJxid(), sline, eline, tresult, aline, apoint);
                result.removeAll(tresult);
                result.addAll(tresult);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("oid in ( ");
        for (Line tempLine : result) {
            sb.append(tempLine.getOid() + ",");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        ajax.put("line", tranceResult.doQuery("供电管线", "", sb.toString()));
        ajax.put("fw", fwresult);
        return ajax;
    }

    @GetMapping("/getdatabyoid")
    @ResponseBody
    public AjaxResult getDataByLine(@RequestParam("oid") String oid, @RequestParam("type") String type) {
        AjaxResult ajax = AjaxResult.success();
        List<Line> cline = gdService.getGdLineByOid(oid);
        List<Fw> fws = gdService.getFws();
        List<Pdf> pdfs = gdService.getPdfs();
        List<Line> alllines = gdService.getGdLine(); // 供电管线段所有信息
        Map<String, String> fwm = ListToMap.ListFwToMap(fws);
        Map<String, String> pdfm = ListToMap.ListPdfToMap(pdfs);
        Map<String, Map<String, List<Line>>> lines = ListToMap.listLineToMap(alllines);
        Map<String, List<Line>> sline = lines.get("smap");
        Map<String, List<Line>> eline = lines.get("emap");
        Map<String, Object> aline = new HashMap<>();
        Map<String, String> apoint = new HashMap<>();
        List<Line> lineresult = new ArrayList<>();
        List<String> pdfresult = new ArrayList<>();
        List<String> fwresult = new ArrayList<>();
        if (cline != null && cline.size() > 0) {
            Line line = cline.get(0);
            if (type != null && "up".equals(type)) {
                apoint.put(line.getE_point(), line.getE_point());
            } else if (type != null && "down".equals(type)) {
                apoint.put(line.getS_point(), line.getS_point());
            }
            // System.out.println("line.getS_point():"+line.getS_point());
            // System.out.println("line.getE_point():"+line.getE_point());
            lineresult = getLinesAfter(line.getE_point(), sline, eline, lineresult, aline, apoint, fwm, pdfm);
            pdfresult = getPdfsAfter(line.getE_point(), sline, eline, pdfresult, aline, apoint, fwm, pdfm);
            fwresult = getFwsAfter(line.getE_point(), sline, eline, fwresult, aline, apoint, fwm, pdfm);

            StringBuilder sb = new StringBuilder();
            sb.append("oid in ( ");
            for (Line tempLine : lineresult) {
                sb.append(tempLine.getOid() + ",");
            }
            sb = sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            // ajax.put("line1",lineresult);
            ajax.put("line", tranceResult.doQuery("供电管线", "", sb.toString()));

            ajax.put("pdf",
                    tranceResult.doQuery("供电管线点", "", org.apache.commons.lang3.StringUtils.join(pdfresult, ",")));
            ajax.put("fw", tranceResult.doQuery("供电管线点", "", org.apache.commons.lang3.StringUtils.join(fwresult, ",")));
        }

        return ajax;
    }

    @GetMapping("/getValveRelation")
    @ResponseBody
    public AjaxResult getValveRealtion(@RequestParam("id") String id) {
        AjaxResult aj = AjaxResult.success();
        List<Jsgxhl> syfm = new ArrayList<>();
        List<Jsgxhl> xyfm = new ArrayList<>();
        syfm = jsService.getSyfm(id);
        xyfm = jsService.getXyfm(id);
        List<Jspoint> jssyfm = new ArrayList<>();
        List<Jspoint> jsxyfm = new ArrayList<>();
        if (syfm != null && syfm.size() > 0) {
            jssyfm = jsService.getValveInfo(syfm, "sy");
        }
        if (xyfm != null && xyfm.size() > 0) {
            jsxyfm = jsService.getValveInfo(xyfm, "xy");
        }
        aj.put("syfm", jssyfm);
        aj.put("xyfm", jsxyfm);
        return aj;
    }

    public static List<Line> getLines(List<Line> result, List<Line> list, Map<String, List<Line>> sline,
            Map<String, List<Line>> eline, String cxid, Map<String, String> points, Map<String, Line> rmap) {

        for (int i = 0, len = list.size(); i < len; i++) {
            Line line = list.get(i);
            String spoint = line.getS_point();
            if (spoint != null && spoint.equals(cxid)) {
                break;
            }
            // System.out.println("******" + line.getE_point());
            if (rmap.get(line.getOid()) == null) {
                result.add(line);
                rmap.put(String.valueOf(line.getOid()), line);
            }
            if (points.get(spoint) == null) {
                List<Line> templist = eline.get(spoint);
                points.put(spoint, spoint);
                if (templist == null) {
                    templist = sline.get(spoint);
                    points.put(spoint, spoint);
                }
                if (templist != null && templist.size() > 0) {
                    if (templist != null) {
                        getLines(result, templist, sline, eline, cxid, points, rmap);
                    }
                }
            }

        }
        return result;
    }

    public static List<Line> getPdfData(String pdfcxid, Map<String, List<Line>> sline, Map<String, List<Line>> eline,
            List<Line> result, Map<String, Object> aline, Map<String, String> apoint, Map<String, String> jxids) {
        // System.out.println("分析点号为:" + pdfcxid);
        List<Line> tlist = sline.get(pdfcxid);
        if (tlist != null && eline.get(pdfcxid) != null) {
            // System.out.println(tlist);
            // System.out.println(eline.get(pdfcxid));
            tlist.addAll(eline.get(pdfcxid));
        } else if (eline.get(pdfcxid) != null) {
            tlist = eline.get(pdfcxid);
        }

        if (tlist != null && tlist.size() > 0) {
            for (int i = 0, len = tlist.size(); i < len; i++) {
                Line line = tlist.get(i);
                Object to = aline.get(String.valueOf(line.getOid()));
                if (to != null) {

                } else {
                    result.add(line);
                    aline.put(String.valueOf(line.getOid()), line);

                    for (int j = 0; j < 2; j++) {
                        if (j == 0) {
                            String tpoint = apoint.get(line.getS_point());
                            if (tpoint != null && tpoint.equals(jxids.get(tpoint))) {
                                break;
                            }
                            if (tpoint == null) {
                                apoint.put(line.getS_point(), line.getS_point());
                                getPdfData(line.getS_point(), sline, eline, result, aline, apoint, jxids);
                            }
                        }
                        if (j == 1) {
                            String tpoint = apoint.get(line.getE_point());
                            if (tpoint != null && tpoint.equals(jxids.get(tpoint))) {
                                break;
                            }
                            if (tpoint == null) {
                                apoint.put(line.getE_point(), line.getE_point());
                                getPdfData(line.getE_point(), sline, eline, result, aline, apoint, jxids);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    // 分析配电房
    public static List<String> getPdfs(String pdfcxid, Map<String, List<Line>> sline, Map<String, List<Line>> eline,
            List<String> result, Map<String, String> apoint, Map<String, String> mpdf) {
        List<Line> tlist = sline.get(pdfcxid);
        if (tlist != null && eline.get(pdfcxid) != null) {
            tlist.addAll(eline.get(pdfcxid));
        } else if (eline.get(pdfcxid) != null) {
            tlist = eline.get(pdfcxid);
        }
        apoint.put(pdfcxid, pdfcxid);
        if (tlist != null && tlist.size() > 0) {
            for (int i = 0, len = tlist.size(); i < len; i++) {
                Line line = tlist.get(i);
                Object to = apoint.get(line.getS_point());
                if (to != null) {

                } else {
                    for (int j = 0; j < 2; j++) {
                        if (j == 0 && apoint.get(line.getS_point()) == null) {
                            if (mpdf.get(line.getS_point()) != null) {
                                result.add(line.getS_point());
                            }
                            getPdfs(line.getS_point(), sline, eline, result, apoint, mpdf);
                            apoint.put(line.getS_point(), line.getS_point());
                        }
                        if (j == 1 && apoint.get(line.getE_point()) == null) {
                            if (mpdf.get(line.getE_point()) != null) {
                                result.add(line.getE_point());
                            }
                            getPdfs(line.getE_point(), sline, eline, result, apoint, mpdf);
                            apoint.put(line.getE_point(), line.getE_point());
                        }

                    }

                }

            }
        }
        return result;
    }

    public static List<Line> getLinesAfter(String startid, Map<String, List<Line>> sline, Map<String, List<Line>> eline,
            List<Line> result, Map<String, Object> aline, Map<String, String> apoint, Map<String, String> fw,
            Map<String, String> pdf) {
        // System.out.println("分析点号为:" + startid);
        List<Line> tlist = sline.get(startid);
        if (tlist != null && eline.get(startid) != null) {
            tlist.addAll(eline.get(startid));
        } else if (eline.get(startid) != null) {
            tlist = eline.get(startid);
        }

        if (tlist != null && tlist.size() > 0) {
            for (int i = 0, len = tlist.size(); i < len; i++) {
                Line line = tlist.get(i);
                Object to = aline.get(String.valueOf(line.getOid()));
                if (to != null) {

                } else {
                    result.add(line);
                    aline.put(String.valueOf(line.getOid()), line);

                    for (int j = 0; j < 2; j++) {
                        if (j == 0) {
                            String tpoint = apoint.get(line.getS_point());
                            if (tpoint != null && (tpoint.equals(fw.get(tpoint)) || tpoint.equals(pdf.get(tpoint)))) {
                                break;
                            }
                            if (tpoint == null) {
                                apoint.put(line.getS_point(), line.getS_point());
                                getLinesAfter(line.getS_point(), sline, eline, result, aline, apoint, fw, pdf);
                            }
                        }
                        if (j == 1) {
                            String tpoint = apoint.get(line.getE_point());
                            if (tpoint != null && (tpoint.equals(fw.get(tpoint)) || tpoint.equals(pdf.get(tpoint)))) {
                                break;
                            }
                            if (tpoint == null) {
                                apoint.put(line.getE_point(), line.getE_point());
                                getLinesAfter(line.getE_point(), sline, eline, result, aline, apoint, fw, pdf);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<String> getPdfsAfter(String startid, Map<String, List<Line>> sline,
            Map<String, List<Line>> eline, List<String> result, Map<String, Object> aline, Map<String, String> apoint,
            Map<String, String> fw, Map<String, String> pdf) {
        List<Line> tlist = sline.get(startid);
        if (tlist != null && eline.get(startid) != null) {
            tlist.addAll(eline.get(startid));
        } else if (eline.get(startid) != null) {
            tlist = eline.get(startid);
        }

        if (tlist != null && tlist.size() > 0) {
            for (int i = 0, len = tlist.size(); i < len; i++) {
                Line line = tlist.get(i);
                Object to = aline.get(String.valueOf(line.getOid()));
                if (to != null) {

                } else {
                    // result.add(line);
                    // aline.put(String.valueOf(line.getOid()),line);

                    for (int j = 0; j < 2; j++) {
                        if (j == 0) {
                            String tpoint = apoint.get(line.getS_point());
                            if (tpoint != null && tpoint.equals(fw.get(tpoint))) {
                                result.add(tpoint);
                                break;
                            }
                            if (tpoint == null) {
                                apoint.put(line.getS_point(), line.getS_point());
                                getPdfsAfter(line.getS_point(), sline, eline, result, aline, apoint, fw, pdf);
                            }
                        }
                        if (j == 1) {
                            String tpoint = apoint.get(line.getE_point());
                            if (tpoint != null && tpoint.equals(fw.get(tpoint))) {
                                result.add(tpoint);
                                break;
                            }
                            if (tpoint == null) {
                                apoint.put(line.getE_point(), line.getE_point());
                                getPdfsAfter(line.getE_point(), sline, eline, result, aline, apoint, fw, pdf);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<String> getFwsAfter(String startid, Map<String, List<Line>> sline, Map<String, List<Line>> eline,
            List<String> result, Map<String, Object> aline, Map<String, String> apoint, Map<String, String> fw,
            Map<String, String> pdf) {
        List<Line> tlist = sline.get(startid);
        if (tlist != null && eline.get(startid) != null) {
            tlist.addAll(eline.get(startid));
        } else if (eline.get(startid) != null) {
            tlist = eline.get(startid);
        }

        if (tlist != null && tlist.size() > 0) {
            for (int i = 0, len = tlist.size(); i < len; i++) {
                Line line = tlist.get(i);
                Object to = aline.get(String.valueOf(line.getOid()));
                if (to != null) {

                } else {
                    // result.add(line);
                    // aline.put(String.valueOf(line.getOid()),line);

                    for (int j = 0; j < 2; j++) {
                        if (j == 0) {
                            String tpoint = apoint.get(line.getS_point());
                            if (tpoint != null && tpoint.equals(pdf.get(tpoint))) {
                                result.add(tpoint);
                                break;
                            }
                            if (tpoint == null) {
                                apoint.put(line.getS_point(), line.getS_point());
                                getPdfsAfter(line.getS_point(), sline, eline, result, aline, apoint, fw, pdf);
                            }
                        }
                        if (j == 1) {
                            String tpoint = apoint.get(line.getE_point());
                            if (tpoint != null && tpoint.equals(pdf.get(tpoint))) {
                                result.add(tpoint);
                                break;
                            }
                            if (tpoint == null) {
                                apoint.put(line.getE_point(), line.getE_point());
                                getPdfsAfter(line.getE_point(), sline, eline, result, aline, apoint, fw, pdf);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<Line> analysisHlgx(String cxid, String jxid, Map<String, List<Line>> sline,
            Map<String, List<Line>> eline, List<Line> result, Map<String, Object> aline, Map<String, String> apoint) {
        if (cxid.equals(jxid)) {
            return result;
        }
        List<Line> tlist = sline.get(cxid);
        if (tlist != null && eline.get(cxid) != null) {
            tlist.addAll(eline.get(cxid));
        } else if (eline.get(cxid) != null) {
            tlist = eline.get(cxid);
        }

        if (tlist != null && tlist.size() > 0) {
            for (int i = 0, len = tlist.size(); i < len; i++) {
                Line line = tlist.get(i);
                Object to = aline.get(String.valueOf(line.getOid()));
                if (to != null) {

                } else {
                    result.add(line);
                    aline.put(String.valueOf(line.getOid()), line);

                    for (int j = 0; j < 2; j++) {
                        if (j == 0) {
                            String tpoint = apoint.get(line.getS_point());
                            if (tpoint != null) {
                                break;
                            }
                            if (tpoint == null) {
                                apoint.put(line.getS_point(), line.getS_point());
                                analysisHlgx(line.getS_point(), jxid, sline, eline, result, aline, apoint);
                            }
                        }
                        if (j == 1) {
                            String tpoint = apoint.get(line.getE_point());
                            if (tpoint != null) {
                                break;
                            }
                            if (tpoint == null) {
                                apoint.put(line.getE_point(), line.getE_point());
                                analysisHlgx(line.getS_point(), jxid, sline, eline, result, aline, apoint);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

}
