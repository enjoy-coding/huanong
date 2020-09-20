package com.feather.smart.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.smart.domain.ZhsqZc;
import com.feather.smart.service.IZhsqZcService;
import com.feather.smart.service.IZhsqZnafService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/znaf")
public class ZhsqZnafController extends BaseController {

    @Autowired
    public IZhsqZnafService iZhsqZnafService;
    @Autowired
    public IZhsqZcService iZhsqZcService;

    @RequestMapping("/api/getCountSb")
    @ResponseBody
    public AjaxResult getCountSb(String sqid,String xqid) {
        if(sqid==null && xqid==null){
            sqid="SQ000001";
        }
        return AjaxResult.success(iZhsqZnafService.getCountSb(sqid,xqid));
    }
    @RequestMapping("/api/getYcList")
    @ResponseBody
    public AjaxResult getYcList(String sqid,String xqid) {
        if(sqid==null && xqid==null){
            sqid="SQ000001";
        }
        return AjaxResult.success(iZhsqZnafService.getYcList(sqid,xqid));
    }
    @RequestMapping("/api/getCountMj")
    @ResponseBody
    public AjaxResult getCountMj(String sqid,String xqid) {
        if(sqid==null && xqid==null){
            sqid="SQ000001";
        }
        return AjaxResult.success(iZhsqZnafService.getCountMj(sqid,xqid));
    }
    @RequestMapping("/api/getMjjcList")
    @ResponseBody
    public AjaxResult getMjjcList(String sqid,String xqid) {
        if(sqid==null && xqid==null){
            sqid="SQ000001";
        }
        return AjaxResult.success(iZhsqZnafService.getMjjcList(sqid,xqid));
    }
    @RequestMapping("/api/getCountZj")
    @ResponseBody
    public AjaxResult getCountZj(String sqid,String xqid) {
        if(sqid==null && xqid==null){
            sqid="SQ000001";
        }
        return AjaxResult.success(iZhsqZnafService.getCountZj(sqid,xqid));
    }
    @RequestMapping("/api/getZjjcList")
    @ResponseBody
    public AjaxResult getZjjcList(String sqid,String xqid) {
        if(sqid==null && xqid==null){
            sqid="SQ000001";
        }
        return AjaxResult.success(iZhsqZnafService.getZjjcList(sqid,xqid));
    }
    @RequestMapping("/api/getCountDg")
    @ResponseBody
    public AjaxResult getCountDg(String sqid,String xqid) {
        if(sqid==null && xqid==null){
            sqid="SQ000001";
        }
        return AjaxResult.success(iZhsqZnafService.getCountDg(sqid,xqid));
    }
    @RequestMapping("/api/getDgjcList")
    @ResponseBody
    public AjaxResult getDgjcList(String sqid,String xqid) {
        if(sqid==null && xqid==null){
            sqid="SQ000001";
        }
        return AjaxResult.success(iZhsqZnafService.getDgjcList(sqid,xqid));
    }
    @RequestMapping("/api/getSxtList")
    @ResponseBody
    public AjaxResult getSxtList(String wzlx,String sqid,String xqid) {
        if(sqid==null && xqid==null){
            sqid="SQ000001";
        }
        return AjaxResult.success(iZhsqZnafService.getSxtList(wzlx,sqid,xqid));
    }

    @RequestMapping("/api/getSsjk")
    @ResponseBody
    public AjaxResult getSsjk(String sqid,String xqid) {
        List<Map> countDg = iZhsqZnafService.getCountDg(sqid,xqid);
        List<Map> countZj = iZhsqZnafService.getCountZj(sqid,xqid);
        List<Map> list = new ArrayList<>();
        Map map1 = new HashMap();
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        Map map4 = new HashMap();
        map1.put("进入人数","0");
        map2.put("进入车辆","0");
        map3.put("外出人数","0");
        map4.put("外出车辆","0");
        for(Map map :countDg){
           String jczt = map.get("jczt").toString();
            String num = map.get("num").toString();
            if("进入".equals(jczt)){
                map2.put("进入车辆",num);
            }else{
                map4.put("外出车辆",num);
            }
        }
        for(Map map :countZj){
            String jczt = map.get("jczt").toString();
            String num = map.get("num").toString();
            if("进入".equals(jczt)){
                map1.put("进入人数",num);
            }else{
                map3.put("外出人数",num);
            }
        }

        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        return AjaxResult.success(list);
    }

    @RequestMapping("/api/getSblxYcCount")
    @ResponseBody
    public AjaxResult getSblxYcCount(String sqid,String xqid){
        return AjaxResult.success(iZhsqZcService.getSblxYcCount(sqid,xqid));

    }
    @RequestMapping("/api/getListSb")
    @ResponseBody
    public AjaxResult getListSb(String sblx,String sbmc,String zcid,String sqid,String xqid){
        return AjaxResult.success(iZhsqZcService.getListSb(sblx,sbmc,zcid,sqid,xqid));

    }



}