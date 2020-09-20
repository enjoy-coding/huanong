package com.feather.cms.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.cms.domain.CmsArgs;
import com.feather.cms.service.ICmsArgsService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 参数Controller
 */
@Controller
@RequestMapping("/cms/args")
public class CmsArgsController extends BaseController {
    private String prefix = "cms/args";

    @Autowired
    private ICmsArgsService cmsArgsService;

    @RequiresPermissions("cms:args:view")
    @GetMapping()
    public String args() {
        return prefix + "/args";
    }

    /**
     * 查询参数列表
     */
    @RequiresPermissions("cms:args:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(CmsArgs cmsArgs) {
        startPage();
        List<CmsArgs> list = cmsArgsService.selectCmsArgsList(cmsArgs);
        return getDataTable(list);
    }

    /**
     * 导出参数列表
     */
    @RequiresPermissions("cms:args:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CmsArgs cmsArgs) {
        List<CmsArgs> list = cmsArgsService.selectCmsArgsList(cmsArgs);
        ExcelUtil<CmsArgs> util = new ExcelUtil<CmsArgs>(CmsArgs.class);
        return util.exportExcel(list, "args");
    }

    /**
     * 新增参数
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        CmsArgs args = new CmsArgs();
        mmap.put("args", args);
        return prefix + "/edit";
    }

    /**
     * 新增保存参数
     */
    @RequiresPermissions("cms:args:add")
    @Log(title = "参数", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CmsArgs cmsArgs) {
        return toAjax(cmsArgsService.insertCmsArgs(cmsArgs));
    }

    /**
     * 修改参数
     */
    @GetMapping("/edit/{argsId}")
    public String edit(@PathVariable("argsId") Long argsId, ModelMap mmap) {
        CmsArgs args = cmsArgsService.selectCmsArgsById(argsId);
        mmap.put("args", args);
        return prefix + "/edit";
    }

    /**
     * 修改保存参数
     */
    @RequiresPermissions("cms:args:edit")
    @Log(title = "参数", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CmsArgs cmsArgs) {
        return toAjax(cmsArgsService.updateCmsArgs(cmsArgs));
    }

    /**
     * 删除参数
     */
    @RequiresPermissions("cms:args:remove")
    @Log(title = "参数", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(cmsArgsService.deleteCmsArgsByIds(ids));
    }
}
