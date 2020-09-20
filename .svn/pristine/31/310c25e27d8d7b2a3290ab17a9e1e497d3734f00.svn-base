package com.feather.system.controller;

import java.util.List;

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
import com.feather.common.core.domain.Ztree;
import com.feather.common.enums.BusinessType;
import com.feather.system.domain.SysDept;
import com.feather.system.domain.SysRole;
import com.feather.system.service.ISysDeptService;

/**
 * 部门信息
 * 
 * @author feather
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    private String prefix = "system/dept";

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("system:dept:view")
    @GetMapping()
    public String dept() {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysDept> list(SysDept dept) {
        List<SysDept> deptList = deptService.selectDeptList(dept);
        return deptList;
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        SysDept dept = new SysDept();
        if (parentId == 0L) {
            dept.setParentId(parentId);
            dept.setParentName("-");
        } else {
            SysDept parent = deptService.selectDeptById(parentId);
            if (parent == null) {
                throw new RuntimeException("未找到上级");
            }
            dept.setParentId(parent.getDeptId());
            dept.setParentName(parent.getDeptName());
        }
        mmap.put("dept", dept);
        return prefix + "/edit";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDept dept) {
        if (!deptService.checkDeptNameUnique(dept)) {
            return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        if (!deptService.checkDeptCodeUnique(dept)) {
            return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门代码已存在");
        }
        return toAjax(deptService.insertDept(dept), dept);
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        SysDept dept = deptService.selectDeptById(deptId);
        if (dept != null && 100L == deptId) {
            dept.setParentName("无");
        }
        mmap.put("dept", dept);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDept dept) {
        if (!deptService.checkDeptNameUnique(dept)) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (!deptService.checkDeptCodeUnique(dept)) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门代码已存在");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 顶级
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/top/{deptId}")
    @ResponseBody
    public AjaxResult top(@PathVariable("deptId") Long deptId) {
        SysDept dept = deptService.selectDeptById(deptId);
        if (dept == null) {
            return AjaxResult.error("未找到部门");
        }
        dept.setParentId(0L);
        return editSave(dept);
    }

    /**
     * 删除
     */
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dept:remove")
    @GetMapping("/remove/{deptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") Long deptId) {
        if (deptService.selectDeptCount(deptId) > 0) {
            return AjaxResult.warn("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return AjaxResult.warn("部门存在用户,不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(SysDept dept) {
        return deptService.checkDeptNameUnique(dept) ? Constants.SUCCESS_OR_ENABLED : Constants.FAIL_OR_DISABLED;
    }

    /**
     * 校验部门代码
     */
    @PostMapping("/checkDeptCodeUnique")
    @ResponseBody
    public String checkDeptCodeUnique(SysDept dept) {
        return deptService.checkDeptCodeUnique(dept) ? Constants.SUCCESS_OR_ENABLED : Constants.FAIL_OR_DISABLED;
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        SysDept dept = deptService.selectDeptById(deptId);
        mmap.put("dept", dept != null ? dept : new SysDept());
        return prefix + "/tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = deptService.selectDeptTree(new SysDept());
        return ztrees;
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Ztree> deptTreeData(SysRole role) {
        List<Ztree> ztrees = deptService.roleDeptTreeData(role);
        return ztrees;
    }
}
