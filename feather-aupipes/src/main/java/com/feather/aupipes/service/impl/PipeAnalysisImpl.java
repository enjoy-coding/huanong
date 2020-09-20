package com.feather.aupipes.service.impl;


import com.feather.aupipes.config.ConfigPipe;
import com.feather.aupipes.domain.query.Line;
import com.feather.aupipes.domain.query.Point;
import com.feather.aupipes.mapper.LineDAO;
import com.feather.aupipes.mapper.PointDAO;
import com.feather.aupipes.service.IPipeAnalysis;
import com.feather.aupipes.utils.FeatureServer;
import com.feather.aupipes.utils.TranceResult;
import com.feather.common.annotation.DataSource;
import com.feather.common.enums.DataSourceType;
import com.feather.common.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *
 * @Author: fang xinliang
 * @Date: 2019/12/18 13:51
 */
@Service
public class PipeAnalysisImpl implements IPipeAnalysis {

    @Autowired
    private LineDAO lineDAO;

    @Autowired
    private PointDAO pointDAO;

    @Autowired
    private TranceResult tranceResult;

    @Autowired
    private ConfigPipe configPipe;
    @Override
    public Map GetBurstAnalysis(double x, double y) {
        //  测试方法
        String traceBack= configPipe.getJsxUrl();//
        // 追溯分析，向上
        String traceBackModel_0 = configPipe.getJsxParam0()+x+","+y;
         // 追溯分析，向下
        String traceBackModel_1 = configPipe.getJsxParam1()+x+","+y;

        //获取路径，根据路径查下 拐点
        String mode_0 = FeatureServer.DoPost2GIS(traceBack,traceBackModel_0);
        Map listData_0 = tranceResult.GetPath(mode_0, "0",x,y);

        // 向下分析没有找支流（多分析一次） ，需要特殊处理,终点变成起点 向上分析
        String mode_1 = FeatureServer.DoPost2GIS(traceBack,traceBackModel_1);
        // 特殊处理，收尾交换 分析
        Map listData_1 = tranceResult.GetPathEndNode(mode_1, "1");
         if(listData_1!=null){
             Map strat=  tranceResult.GetPathStartNode(mode_0, "0",x,y);
             if(strat!=null){
                 String  traceParam= configPipe.getJsxParam0().replace("barrierpoints=","barrierpoints="+strat.get("startX") +","+strat.get("startY")) +listData_1.get("endX")+","+listData_1.get("endY");
                 mode_1 = FeatureServer.DoPost2GIS(traceBack,traceParam);
                 listData_1 =  tranceResult.GetPath(mode_1,"0",(double)strat.get("startX"),(double)strat.get("startY"));
             }
         }

         List<Point> listPointAll=new ArrayList<Point>();
         List<JSONObject> listPathAll=new ArrayList<JSONObject>();
        if(listData_0!=null){
            if(listData_0.get("points")!=null){
                listPointAll.addAll((List)listData_0.get("points"));
            }
            if(listData_0.get("paths")!=null){
                listPathAll.addAll((List)listData_0.get("paths"));
            }
        }

         if(listData_1!=null){
            if(listData_1.get("points")!=null){
                listPointAll.addAll((List)listData_1.get("points"));
            }
           if(listData_1.get("paths")!=null){
               listPathAll.addAll((List)listData_1.get("paths"));
           }
        }
        Map map =  new HashMap<String,Object>(){
            {
                put("points", listPointAll);
                put("paths", listPathAll);
            }
        };
      return  map;
    }
    @Override
    public List<Line> getLine(int oid, String name) {
        List<Line> line = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("oid",oid);
        map.put("name",name);
        line = lineDAO.getLineByCondition(map);
        return line;
    }
    @Override
    public List<Line> getLine() {
        Map<String,Object> map = new HashMap<>();
        List<Line> line = new ArrayList<>();
        line = lineDAO.getLineByCondition(map);
        return line;
    }
    @Override
    public List<Point> getPoint() {
        Map<String,Object> map = new HashMap<>();
        List<Point> point = new ArrayList<>();
        point = pointDAO.getPointByCondition(map);
        return point;
    }
    @Override
    public List<Point> getValve() {
        Map<String,Object> map = new HashMap<>();
        List<Point> point = new ArrayList<>();
        point = pointDAO.getValve(map);
        return point;
    }
    /**
     *  排管分析
     * @param line
     * @param radius
     * @param maishen
     * @param lineType
     * @param layoutType
     * @param buff
     * @return
     */
    @Override
    public Map CapacityAnalysis(String line,double radius,double maishen,String lineType,String layoutType,double buff){
        return null;
    }
}
