package com.feather.smart.controller;

import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.smart.service.IZhsqZcService;
import com.feather.smart.service.IZhsqZhzlService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 综合治理
 * @author fancy
 * @date 2020-05-14
 */
@Controller
@RequestMapping("/zhzl")
public class ZhsqZhzlController extends BaseController
{
    @Autowired
    private IZhsqZhzlService zhsqZhzlService;
    @Autowired
    private IZhsqZcService zhsqZcService;

    @GetMapping("/api/selectZdryCount")
    @ResponseBody
    public AjaxResult selectZdryCount(@Param("xqid") String xqid, @Param("sqid") String sqid){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        return AjaxResult.success(zhsqZhzlService.selectZdryCount(maps));
    }

    /**
     * 统计重点人员分布情况
     * @return
     */
    @GetMapping("/api/getZdryFb")
    @ResponseBody
    public AjaxResult getZdryFb(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("sfdj") String sfdj, @Param("sfkc") String sfkc, @Param("sfxmsf") String sfxmsf, @Param("sftyjr") String sftyjr, @Param("sfdb") String sfdb, @Param("sfcj") String sfcj){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("sfdj",sfdj);
        maps.put("sfkc",sfkc);
        maps.put("sfxmsf",sfxmsf);
        maps.put("sftyjr",sftyjr);
        maps.put("sfdb",sfdb);
        maps.put("sfcj",sfcj);

        return AjaxResult.success(zhsqZhzlService.getZdryFb(maps));
    }

    /**
     * 统计重点人员年龄结构
     * @return
     */
    @GetMapping("/api/getZdNl")
    @ResponseBody
    public AjaxResult getZdNl(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("sfdj") String sfdj, @Param("sfkc") String sfkc, @Param("sfxmsf") String sfxmsf, @Param("sftyjr") String sftyjr, @Param("sfdb") String sfdb, @Param("sfcj") String sfcj){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("sfdj",sfdj);
        maps.put("sfkc",sfkc);
        maps.put("sfxmsf",sfxmsf);
        maps.put("sftyjr",sftyjr);
        maps.put("sfdb",sfdb);
        maps.put("sfcj",sfcj);
        return AjaxResult.success(zhsqZhzlService.getZdNl(maps));
    }

    /**
     * 统计重点人员男女比例
     * @return
     */
    @GetMapping("/api/getZdNnBl")
    @ResponseBody
    public AjaxResult getZdNnBl(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("sfdj") String sfdj, @Param("sfkc") String sfkc, @Param("sfxmsf") String sfxmsf, @Param("sftyjr") String sftyjr, @Param("sfdb") String sfdb, @Param("sfcj") String sfcj){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("sfdj",sfdj);
        maps.put("sfkc",sfkc);
        maps.put("sfxmsf",sfxmsf);
        maps.put("sftyjr",sftyjr);
        maps.put("sfdb",sfdb);
        maps.put("sfcj",sfcj);
        return AjaxResult.success(zhsqZhzlService.getZdNnBl(maps));
    }

    /**
     * 统计重点人员民族比例
     * @return
     */
    @GetMapping("/api/getZdMzBl")
    @ResponseBody
    public AjaxResult getZdMzBl(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("sfdj") String sfdj, @Param("sfkc") String sfkc, @Param("sfxmsf") String sfxmsf, @Param("sftyjr") String sftyjr, @Param("sfdb") String sfdb, @Param("sfcj") String sfcj){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("sfdj",sfdj);
        maps.put("sfkc",sfkc);
        maps.put("sfxmsf",sfxmsf);
        maps.put("sftyjr",sftyjr);
        maps.put("sfdb",sfdb);
        maps.put("sfcj",sfcj);
        return AjaxResult.success(zhsqZhzlService.getZdMzBl(maps));
    }

    /**
     * 重点人员列表
     * @return
     */
    @GetMapping("/api/getZdRyList")
    @ResponseBody
    public AjaxResult getZdRyList(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("sfdj") String sfdj, @Param("sfkc") String sfkc, @Param("sfxmsf") String sfxmsf, @Param("sftyjr") String sftyjr, @Param("sfdb") String sfdb, @Param("sfcj") String sfcj, @Param("zdry") String zdry){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("sfdj",sfdj);
        maps.put("sfkc",sfkc);
        maps.put("sfxmsf",sfxmsf);
        maps.put("sftyjr",sftyjr);
        maps.put("sfdb",sfdb);
        maps.put("sfcj",sfcj);
        maps.put("zdry",zdry);
        return AjaxResult.success(zhsqZhzlService.getZdRyList(maps));
    }

    /**
     * 巡检任务统计
     * @return
     */
    @GetMapping("/api/selectXjrwCount")
    @ResponseBody
    public AjaxResult selectXjrwCount(@Param("xqid") String xqid, @Param("sqid") String sqid){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        return AjaxResult.success(zhsqZhzlService.selectXjrwCount(maps));
    }

    /**
     * 首页人员巡检
     * @return
     */
    @GetMapping("/api/selectRyxjCount")
    @ResponseBody
    public AjaxResult selectRyxjCount(@Param("xqid") String xqid, @Param("sqid") String sqid){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        return AjaxResult.success(zhsqZhzlService.selectRyxjCount(maps));
    }

    //小区树
    @RequestMapping( "/api/getTree")
    @ResponseBody
    public AjaxResult getTree()
    {
        List<Map> listSq = zhsqZcService.getSqAreaTree();
        List<Map>listSq1 = new ArrayList<>();
        Map map1=null;
        for(Map map:listSq){
            map1  = new HashMap();
            map1.put("id",map.get("SQID").toString());
            map1.put("name",map.get("SQMC").toString());
            map1.put("pId","0");
            map1.put("open",true);

            List<Map>listxq = zhsqZcService.getAreaTree(map.get("SQID").toString());
            List<Map> lista=new ArrayList<>();
            for(Map map2:listxq){
                Map map3 = new HashMap();
                map3.put("id",map2.get("XQID").toString());
                map3.put("name",map2.get("XQMC").toString());
                map3.put("pId",map2.get("SQID").toString());
                map3.put("open",true);
                map3.put("children","");
                lista.add(map3);
            }
            map1.put("children",lista);
        }
        listSq1.add(map1);

        return AjaxResult.success(listSq1);
    }

    /**
     * 巡检任务
     * @return
     */
    @GetMapping("/api/getXjrw")
    @ResponseBody
    public AjaxResult getXjrw(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("xczt") String xczt, @Param("xjrw") String xjrw){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("xczt",xczt);
        maps.put("xjrw",xjrw);
        return AjaxResult.success(zhsqZhzlService.getXjrw(maps));
    }

    /**
     * 重点事件
     * @return
     */
    @GetMapping("/api/selectZdsjCount")
    @ResponseBody
    public AjaxResult selectZdsjCount(@Param("xqid") String xqid, @Param("sqid") String sqid){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        return AjaxResult.success(zhsqZhzlService.selectZdsjCount(maps));
    }

    /**
     * 重点事件扇形图
     * @return
     */
    @GetMapping("/api/selectZdsjTCount")
    @ResponseBody
    public AjaxResult selectZdsjTCount(@Param("xqid") String xqid, @Param("sqid") String sqid){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        return AjaxResult.success(zhsqZhzlService.selectZdsjTCount(maps));
    }

    /**
     * 重点事件列表
     * @return
     */
    @GetMapping("/api/getZdsjList")
    @ResponseBody
    public AjaxResult getZdsjList(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("czzt") String czzt, @Param("sjlx") String sjlx){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("czzt",czzt);
        maps.put("sjlx",sjlx);
        return AjaxResult.success(zhsqZhzlService.getZdsjList(maps));
    }
}
