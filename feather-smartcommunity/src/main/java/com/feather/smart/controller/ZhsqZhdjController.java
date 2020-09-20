package com.feather.smart.controller;

import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.smart.service.IZhsqZhdjService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 智慧党建
 * @author fancy
 * @date 2020-05-14
 */
@Controller
@RequestMapping("/zhdj")
public class ZhsqZhdjController extends BaseController
{

    @Autowired
    private IZhsqZhdjService zhsqZhdjService;


    /**
     * 统计党政队伍
     * @param
     * @return
     */
    @GetMapping("/api/selectDjdwCount")
    @ResponseBody
    public AjaxResult selectZdryCount(@Param("xqid") String xqid, @Param("sqid") String sqid){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        return AjaxResult.success(zhsqZhdjService.selectDjdwCount(maps));
    }

    /**
     * 党组织列表
     * @param
     * @return
     */
    @GetMapping("/api/getDzzList")
    @ResponseBody
    public AjaxResult getDzzList(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("mc") String mc){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("mc",mc);
        return AjaxResult.success(zhsqZhdjService.getDzzList(maps));
    }

    /**
     * 党组织弹框
     * @param
     * @return
     */
    @GetMapping("/api/getDzzInfo")
    @ResponseBody
    public AjaxResult getDzzInfo(@Param("dzzid") String dzzid){
        Map<String,Object> maps = new HashMap<>();
        maps.put("dzzid",dzzid);
        return AjaxResult.success(zhsqZhdjService.getDzzInfo(maps));
    }

    /**
     * 党员志愿者列表
     * @param
     * @return
     */
    @GetMapping("/api/getDyZyzList")
    @ResponseBody
    public AjaxResult getDyZyzList(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("xm") String xm){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("xm",xm);
        return AjaxResult.success(zhsqZhdjService.getDyZyzList(maps));
    }

    /**
     * 志愿者列表
     * @param
     * @return
     */
    @GetMapping("/api/getZyzList")
    @ResponseBody
    public AjaxResult getZyzList(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("zyz") String zyz, @Param("zyzxx") String zyzxx){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("zyz",zyz);
        maps.put("zyzxx",zyzxx);
        return AjaxResult.success(zhsqZhdjService.getZyzList(maps));
    }

    /**
     * 党员学历分布
     * @param
     * @return
     */
    @GetMapping("/api/getDyxlFb")
    @ResponseBody
    public AjaxResult getDyxlFb(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("zyz") String zyz){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("zyz",zyz);
        return AjaxResult.success(zhsqZhdjService.getDyxlFb(maps));
    }

    /**
     * 党员年龄分布
     * @param
     * @return
     */
    @GetMapping("/api/getDyNlFb")
    @ResponseBody
    public AjaxResult getDyNlFb(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("zyz") String zyz){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("zyz",zyz);
        return AjaxResult.success(zhsqZhdjService.getDyNlFb(maps));
    }

    /**
     * 党员党龄分布
     * @param
     * @return
     */
    @GetMapping("/api/getDyNl")
    @ResponseBody
    public AjaxResult getDyNl(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("zyz") String zyz){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("zyz",zyz);
        return AjaxResult.success(zhsqZhdjService.getDyNl(maps));
    }

    /**
     * 党员性别比例
     * @param
     * @return
     */
    @GetMapping("/api/getDyXb")
    @ResponseBody
    public AjaxResult getDyXb(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("zyz") String zyz){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("zyz",zyz);
        return AjaxResult.success(zhsqZhdjService.getDyXb(maps));
    }

    /**
     * 党员民族比例
     * @param
     * @return
     */
    @GetMapping("/api/getDyMz")
    @ResponseBody
    public AjaxResult getDyMz(@Param("xqid") String xqid, @Param("sqid") String sqid, @Param("zyz") String zyz){
        Map<String,Object> maps = new HashMap<>();
        maps.put("xqid",xqid);
        maps.put("sqid",sqid);
        maps.put("zyz",zyz);
        return AjaxResult.success(zhsqZhdjService.getDyMz(maps));
    }

    /**
     * 党员弹框详情
     * @param
     * @return
     */
    @GetMapping("/api/getDyZyzTk")
    @ResponseBody
    public AjaxResult getDyZyzTk(@Param("dyid") String dyid){
        Map<String,Object> maps = new HashMap<>();
        maps.put("dyid",dyid);
        return AjaxResult.success(zhsqZhdjService.getDyZyzTk(maps));
    }
}
