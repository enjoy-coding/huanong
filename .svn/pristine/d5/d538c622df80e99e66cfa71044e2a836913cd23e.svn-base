package com.feather.aupipes.controller.screen;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feather.common.core.controller.BaseController;
import com.feather.framework.util.ShiroUtils;
import com.feather.system.domain.SysUser;

/**
 * 大屏 - 首页
 */
@Controller
@RequestMapping("/screen")
public class ScreenController extends BaseController {
    private String prefix = "/aupipes/screen";

    /**
     * 入口
     */
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("user", user);
        return prefix + "/index";
    }

    /**
     * 设备统计
     */
    @GetMapping("/index/sbtj")
    public String sbjt(ModelMap mmap) {
        return prefix + "/index/sbtj";
    }

    /**
     * 预警处置
     */
    @GetMapping("/index/yjcz")
    public String yjcz(ModelMap mmap) {
        return prefix + "/index/yjcz";
    }

    /**
     * 维修服务 人工巡检
     */
    @GetMapping("/index/wxxj")
    public String wxxj(ModelMap mmap) {
        return prefix + "/index/wxxj";
    }

    /**
     * 预案模拟
     */
    @GetMapping("/index/yamn")
    public String yamn(ModelMap mmap) {
        return prefix + "/index/yamn";
    }

    /**
     * 路灯情况
     */
    @GetMapping("/index/ldqk")
    public String ldqk(ModelMap mmap) {
        return prefix + "/index/ldqk";
    }

    /**
     * 智能安防
     */
    @GetMapping("/index/znaf")
    public String znaf(ModelMap mmap) {
        return prefix + "/index/znaf";
    }

    /**
     * 能耗统计 用水用电 总量
     */
    @GetMapping("/index/nhtj/total")
    public String nhtjTotal(ModelMap mmap) {
        return prefix + "/index/nhtj-total";
    }

    /**
     * 能耗统计 用水用电 比量
     */
    @GetMapping("/index/nhtj/rate")
    public String nhtjRate(ModelMap mmap) {
        return prefix + "/index/nhtj-rate";
    }

    /**
     * 能耗统计 用水用电 分区分类
     */
    @GetMapping("/index/nhtj/zone")
    public String nhtjZone(ModelMap mmap) {
        return prefix + "/index/nhtj-zone";
    }

    /**
     * 泵房信息
     */
    @GetMapping("/index/bfxx")
    public String bfxx(ModelMap mmap) {
        return prefix + "/index/bfxx";
    }
}