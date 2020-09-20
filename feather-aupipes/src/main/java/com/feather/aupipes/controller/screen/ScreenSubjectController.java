package com.feather.aupipes.controller.screen;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.feather.common.core.controller.BaseController;
import com.feather.framework.util.ShiroUtils;
import com.feather.system.domain.SysUser;

/**
 * 大屏 - 子模块
 */
@Controller
@RequestMapping("/screen")
public class ScreenSubjectController extends BaseController {
    private String path = "/aupipes/screen/subject";

    /**
     * 预警处置
     */
    @GetMapping("/yjcz/index")
    public String yjczIndex(@RequestParam(name = "menuCode", required = false) String menuCode,
            @RequestParam(name = "menuValue", required = false) String menuValue, ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("modelName", "YJCZ");
        mmap.put("menuCode", menuCode);
        mmap.put("menuValue", menuValue);
        mmap.put("user", user);
        return path;
    }

    /**
     * 运行监控
     */
    @GetMapping("/yxjk/index")
    public String yxjkIndex(@RequestParam(name = "menuCode", required = false) String menuCode,
            @RequestParam(name = "menuValue", required = false) String menuValue, ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("modelName", "YXJK");
        mmap.put("menuCode", menuCode);
        mmap.put("menuValue", menuValue);
        mmap.put("user", user);
        return path;
    }

    /**
     * 人员巡检
     */
    @GetMapping("/ryxj/index")
    public String ryxjIndex(@RequestParam(name = "menuCode", required = false) String menuCode,
            @RequestParam(name = "menuValue", required = false) String menuValue, ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("modelName", "RYXJ");
        mmap.put("menuCode", menuCode);
        mmap.put("menuValue", menuValue);
        mmap.put("user", user);
        return path;
    }

    /**
     * 决策保障
     */
    @GetMapping("/jcbz/index")
    public String jcbzIndex(@RequestParam(name = "menuCode", required = false) String menuCode,
            @RequestParam(name = "menuValue", required = false) String menuValue, ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("modelName", "JCBZ");
        mmap.put("menuCode", menuCode);
        mmap.put("menuValue", menuValue);
        mmap.put("user", user);
        return path;
    }

    /**
     * 能耗监管
     */
    @GetMapping("/nhjg/index")
    public String nhjgIndex(@RequestParam(name = "menuCode", required = false) String menuCode,
            @RequestParam(name = "menuValue", required = false) String menuValue, ModelMap mmap) {
        SysUser user = ShiroUtils.getSysUser();
        mmap.put("modelName", "NHJG");
        mmap.put("menuCode", menuCode);
        mmap.put("menuValue", menuValue);
        mmap.put("user", user);
        return path;
    }
}