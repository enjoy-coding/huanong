package com.feather.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.config.Global;
import com.feather.common.core.controller.BaseController;
import com.feather.framework.util.ShiroUtils;
import com.feather.system.domain.SysMenu;
import com.feather.system.domain.SysUser;
import com.feather.system.service.ISysMenuService;

/**
 * 首页 业务处理
 * 
 * @author feather
 */
@Controller
public class SysIndexController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        return "index";
    }

    // 获取登录用户
    @GetMapping("/getUser")
    @ResponseBody
    public SysUser getUser() {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        return user;
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", Global.getVersion());
        return "main";
    }
}
