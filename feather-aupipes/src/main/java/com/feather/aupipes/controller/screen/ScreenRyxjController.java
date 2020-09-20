package com.feather.aupipes.controller.screen;

import com.feather.framework.util.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feather.common.core.controller.BaseController;

/**
 * 大屏 子模块 - 人员巡检
 */
@Controller
@RequestMapping("/screen/ryxj")
public class ScreenRyxjController extends BaseController {
    String prefix = "/aupipes/screen/ryxj";

    /**
     * 巡检概况
     */
    @RequestMapping("/mobile")
    public String mobile(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return "/aupipes/screen/ryxj-Mobile";
    }

    /**
     * 巡检概况
     */
    @RequestMapping("/xjgk")
    public String xjgk(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/xjgk";
    }

    /**
     * 巡检详情
     */
    @RequestMapping("/xjxq")
    public String xjxq(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/xjxq";
    }

    /**
     * 巡检详情
     */
    @RequestMapping("/wxxq")
    public String wxxq(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/wxxq";
    }

    /**
     * 巡检记录--弹框
     */
    @RequestMapping("/xjjl")
    public String xjjl(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/xjjl";
    }

    /**
     * 维修记录--弹框
     */
    @RequestMapping("/wxjl")
    public String wxjl(ModelMap mmap) {
        mmap.put("xxxxxxx", null);
        return prefix + "/wxjl";
    }

    /**
     * 新增巡检--弹框
     */
    @RequestMapping("/addInspect")
    public String addInspect(ModelMap mmap) {
        mmap.put("loginId",  ShiroUtils.getSysUser().getUserId());
        mmap.put("loginName",  ShiroUtils.getSysUser().getUserName());
        return prefix + "/addInspect";
    }

    /**
     * 巡检表--弹框
     */
    @RequestMapping("/inspectForm")
    public String inspectForm(ModelMap mmap) {

        return prefix + "/inspectForm";
    }
}