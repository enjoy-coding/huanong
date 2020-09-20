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

import com.feather.cms.domain.CmsDomain;
import com.feather.cms.service.ICmsDomainService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

@Controller
@RequestMapping("/cms/domain")
public class CmsDomainController extends BaseController {
    private String prefix = "cms/domain";

    @Autowired
    private ICmsDomainService cmsDomainService;

    @RequiresPermissions("cms:domain:view")
    @GetMapping()
    public String CmsDomain() {
        return prefix + "/domain";
    }

    /**
     * 查询cms域列表
     */
    @RequiresPermissions("cms:domain:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(CmsDomain cmsDomain) {
        startPage();
        List<CmsDomain> list = cmsDomainService.selectCmsDomainList(cmsDomain);
        return getDataTable(list);
    }

    /**
     * 导出cms域列表
     */
    @RequiresPermissions("cms:domain:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CmsDomain cmsDomain) {
        List<CmsDomain> list = cmsDomainService.selectCmsDomainList(cmsDomain);
        ExcelUtil<CmsDomain> util = new ExcelUtil<CmsDomain>(CmsDomain.class);
        return util.exportExcel(list, "CmsDomain");
    }

    /**
     * 新增cms域
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        CmsDomain domain = new CmsDomain();
        mmap.put("domain", domain);
        return prefix + "/edit";
    }

    /**
     * 新增保存cms域
     */
    @RequiresPermissions("cms:domain:add")
    @Log(title = "cms域", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CmsDomain cmsDomain) {
        return toAjax(cmsDomainService.insertCmsDomain(cmsDomain));
    }

    /**
     * 修改cms域
     */
    @GetMapping("/edit/{domainId}")
    public String edit(@PathVariable("domainId") Long domainId, ModelMap mmap) {
        CmsDomain domain = cmsDomainService.selectCmsDomainById(domainId);
        mmap.put("domain", domain);
        return prefix + "/edit";
    }

    /**
     * 修改保存cms域
     */
    @RequiresPermissions("cms:domain:edit")
    @Log(title = "cms域", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CmsDomain cmsDomain) {
        return toAjax(cmsDomainService.updateCmsDomain(cmsDomain));
    }

    /**
     * 删除cms域
     */
    @RequiresPermissions("cms:domain:remove")
    @Log(title = "cms域", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(cmsDomainService.deleteCmsDomainByIds(ids));
    }
}
