package com.feather.smart.controller;

import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.smart.domain.ZhsqCw;
import com.feather.smart.domain.ZhsqGg;
import com.feather.smart.domain.ZhsqYc;
import com.feather.smart.service.*;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/screen")
public class ScreenIndexController extends BaseController {

    @Autowired
    IZhsqTsjyService zhsqTsjyService;
    @Autowired
    IZhsqYcService zhsqYcService;
    @Autowired
    IZhsqJmService zhsqJmService;
    @Autowired
    IZhsqGgService zhsqGgService;
    @Autowired
    IZhsqZcService zhsqZcService;
    @Autowired
    IZhsqFwService zhsqFwService;
    @Autowired
    IZhsqCwService zhsqCwService;

    @GetMapping()
    public String index() {
        return "redirect:/bus/smartcommunity/index.html";
    }

    @RequestMapping("/api/getPersonInfo")
    @ResponseBody
    public AjaxResult getPersonInfo(@Param("xqid") String xqid, @Param("sqid") String sqid) {
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        return AjaxResult.success(zhsqJmService.getPersonInfo(maps));
    }

    @RequestMapping("/api/getSuggestionList")
    @ResponseBody
    public AjaxResult getSuggestionList(String sqid,String xqid) {
        return AjaxResult.success(zhsqTsjyService.getSuggestionList(sqid,xqid));
    }

    @RequestMapping("/api/getSourceCount")
    @ResponseBody
    public AjaxResult getSourceCount(String sqid,String xqid) {
        return AjaxResult.success(zhsqYcService.getSourceCount(sqid,xqid));
    }

    @RequestMapping("/api/getStatusCount")
    @ResponseBody
    public AjaxResult getStatusCount(String sqid,String xqid) {
        return AjaxResult.success(zhsqYcService.getStatusCount(sqid,xqid));
    }

    // 通知公告
    @RequestMapping("/api/getAdviceList")
    @ResponseBody
    public TableDataInfo getAdviceList(String sqid,String xqid) {
        startPage();
        List<ZhsqGg> list = zhsqGgService.getZhsqGgList(sqid,xqid);
        return getDataTable(list);
    }

    // 查询设备
    @RequestMapping("/api/getSbJc")
    @ResponseBody
    public AjaxResult getSbJc(String type, String id) {
        if (type.equals("YC")) {
            ZhsqYc zhsqYc = zhsqYcService.selectZhsqYcById(id);
            return AjaxResult.success(zhsqYc);
        }
        List<Map> map = zhsqZcService.getSbJc(type, id);
        return AjaxResult.success(map);
    }

    // 查询房屋
    @RequestMapping("/api/getRyXx")
    @ResponseBody
    public AjaxResult getJmXx(@Param("ldid") String ldid) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("ldid", ldid);
        Map<String, Object> map = zhsqFwService.getFwXx(maps);
        return AjaxResult.success(map);
    }

    // 查询人员
    @RequestMapping("/api/getFwRy")
    @ResponseBody
    public AjaxResult getFwRy(@Param("fwid") String fwid) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("fwid", fwid);
        List<Map<String, Object>> map = zhsqJmService.getFwRy(maps);
        return AjaxResult.success(map);
    }

    // 查询重点人员信息
    @RequestMapping("/api/getZdRy")
    @ResponseBody
    public AjaxResult getZdRy(@Param("ldid") String ldid) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("ldid", ldid);
        Map<String, Object> map = zhsqJmService.getZdRy(maps);
        return AjaxResult.success(map);
    }

    // 查询重点人员楼栋信息
    @RequestMapping("/api/getZdRyLd")
    @ResponseBody
    public AjaxResult getZdRyLd() {
        List<Map<String, Object>> map = zhsqJmService.getZdRyLd();
        return AjaxResult.success(map);
    }

    // 查询设备在线统计
    @RequestMapping("/api/getSbxxCount")
    @ResponseBody
    public AjaxResult getSbxxCount(String sqid,String xqid){
        return  AjaxResult.success(zhsqZcService.getSbxxCount(sqid,xqid));
    }

    // 查询重点人员楼栋信息
    @RequestMapping("/api/getSqCwXx")
    @ResponseBody
    public AjaxResult getSqCwXx(@Param("name") String name, ZhsqCw zhsqCw) {
        if(name!=null){
            zhsqCw.setTccmc(name);
        }
        if(name.equals("七宝社区")){
            zhsqCw.setSqid(zhsqCw.getXqid());
            zhsqCw.setXqid("");
        }
        Map<String, Object> map = zhsqCwService.getSqCwXx(zhsqCw);
        return AjaxResult.success(map);
    }

    //查询房屋入住率
    @GetMapping( "/api/rzl")
    @ResponseBody
    public AjaxResult queryRzlCount(@Param("xqid") String xqid,@Param("sqid") String sqid){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        return AjaxResult.success(zhsqFwService.selectRzlCount(maps));
    }

    //房屋统计
    @GetMapping( "/api/tj")
    @ResponseBody
    public AjaxResult queryTjCount(@Param("xqid") String xqid,@Param("sqid") String sqid){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        return AjaxResult.success(zhsqFwService.selectTjCount(maps));
    }

}
