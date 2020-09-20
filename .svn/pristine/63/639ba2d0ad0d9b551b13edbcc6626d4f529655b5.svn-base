package com.feather.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.annotation.Log;
import com.feather.common.constant.Constants;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.LayuiTreeResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.enums.BusinessType;
import com.feather.framework.util.ShiroUtils;
import com.feather.system.domain.SysMenu;
import com.feather.system.domain.SysRole;
import com.feather.system.service.ISysMenuService;

/**
 * 菜单信息
 * 
 * @author feather
 */
@Controller
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
    private String prefix = "system/menu";

    @Autowired
    private ISysMenuService menuService;

    @RequiresPermissions("system:menu:view")
    @GetMapping()
    public String menu() {
        return prefix + "/menu1";
    }

    @RequiresPermissions("system:menu:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysMenu> list(SysMenu menu) {
        Long userId = ShiroUtils.getUserId();
        List<SysMenu> menuList = menuService.selectMenuList(menu, userId);
        return menuList;
    }

    /**
     * 删除菜单
     */
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:menu:remove")
    @GetMapping("/remove/{menuId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.selectCountMenuByParentId(menuId) > 0) {
            return AjaxResult.warn("存在子菜单,不允许删除");
        }
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(menuService.deleteMenuById(menuId));
    }

    /**
     * 新增
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        SysMenu menu = new SysMenu();
        SysMenu parent = menuService.selectMenuById(parentId);
        if (parent != null) {
            menu.setParentId(parent.getMenuId());
            menu.setParentName(parent.getMenuName());
        } else {
            menu.setParentId(0L);
            menu.setMenuName("主目录");
        }
        mmap.put("menu", menu);
        return prefix + "/edit";
    }

    /**
     * 新增保存菜单
     */
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:menu:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(menuService.insertMenu(menu), menu);
    }

    /**
     * 修改菜单
     */
    @GetMapping("/edit/{menuId}")
    public String edit(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        mmap.put("menu", menuService.selectMenuById(menuId));
        return prefix + "/edit";
    }

    /**
     * 修改保存菜单
     */
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:menu:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        ShiroUtils.clearCachedAuthorizationInfo();
        menuService.updateMenu(menu);
        return AjaxResult.success(menuService.selectLayuiTreeMenu(menu));
    }

    /**
     * 修改保存菜单
     */
    @Log(title = "菜单树结构管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:menu:edit")
    @PostMapping("/tree/edit")
    @ResponseBody
    public AjaxResult editTreeSave(Long menuId, Long parentId) {
        SysMenu menu = menuService.selectMenuById(menuId);
        menu.setParentId(parentId);
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 选择菜单图标
     */
    @GetMapping("/icon")
    public String icon() {
        return prefix + "/icon";
    }

    /**
     * 校验菜单名称
     */
    @PostMapping("/checkMenuNameUnique")
    @ResponseBody
    public String checkMenuNameUnique(SysMenu menu) {
        return menuService.checkMenuNameUnique(menu) ? Constants.SUCCESS_OR_ENABLED : Constants.FAIL_OR_DISABLED;
    }

    /**
     * 加载角色菜单列表树
     */
    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public List<Ztree> roleMenuTreeData(SysRole role) {
        Long userId = ShiroUtils.getUserId();
        List<Ztree> ztrees = menuService.roleMenuTreeData(role, userId);
        return ztrees;
    }

    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menuTreeData")
    @ResponseBody
    public List<Ztree> menuTreeData() {
        Long userId = ShiroUtils.getUserId();
        List<Ztree> ztrees = menuService.menuTreeData(userId);
        return ztrees;
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree/{menuId}")
    public String selectMenuTree(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        SysMenu current = menuService.selectMenuById(menuId);
        mmap.put("menu", current != null ? current : new SysMenu());
        return prefix + "/tree";
    }

    /**
     * 获取根节点菜单目录
     * 
     * @param menu
     * @return
     */
    @RequiresPermissions("system:menu:list")
    @GetMapping("/root/list")
    @ResponseBody
    public AjaxResult rootlist(SysMenu menu) {
        Long userId = ShiroUtils.getUserId();
        // menu.setParentId(0L);
        // 第一次查询出所有
        List<LayuiTreeResult> menuList = menuService.selectMenuByIdList(menu, userId);
        return AjaxResult.success(menuList);
    }

    /**
     * 根据子节点列表
     */
    @GetMapping("/root/list/selectMenuById/{menuId}")
    @ResponseBody
    public AjaxResult listByMenuId(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        SysMenu menu = new SysMenu();
        menu.setParentId(menuId);
        List<LayuiTreeResult> menuList = menuService.selectMenuChildrenByIdList(menu);

        return AjaxResult.success(menuList);
    }

    /**
     * 修改菜单
     */
    @GetMapping("/selectTreeTableById/{menuId}")
    @ResponseBody
    public AjaxResult selectTreeTableById(@PathVariable("menuId") Long menuId) {
        SysMenu menu = new SysMenu();
        menu.setMenuId(menuId);
        LayuiTreeResult layuiTreeResult = menuService.selectLayuiTreeMenu(menu);
        return AjaxResult.success(layuiTreeResult);
    }

    /**
     * 判断节点是否是当前父节点的最后一个
     * 
     * @param menuId
     * @return
     */
    @GetMapping("/hasLastChildren/{menuId}")
    @ResponseBody
    public AjaxResult hasLastChildren(@PathVariable("menuId") Long menuId) {
        SysMenu menu = menuService.selectMenuById(menuId);
        // 根据当前id获取父节点
        SysMenu parentMenu = new SysMenu();
        parentMenu.setParentId(menu.getParentId());
        boolean isLastChildren = menuService.hasLastChildren(menuId);
        SysMenu sysParentMenu = menuService.selectMenuById(parentMenu.getParentId());
        Map<String, Object> parentMenuMap = new HashMap<String, Object>();
        parentMenuMap.put("isLastChildren", isLastChildren);
        parentMenuMap.put("parentMenu", sysParentMenu);
        return AjaxResult.success(parentMenuMap);
    }

    /**
     * 判断当前id是否存在子节点
     */
    @GetMapping("/hasChildren/{menuId}")
    @ResponseBody
    public AjaxResult hasChildren(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        boolean hasChildren = menuService.checkHaveChild(menuId);

        return AjaxResult.success(hasChildren);
    }
}