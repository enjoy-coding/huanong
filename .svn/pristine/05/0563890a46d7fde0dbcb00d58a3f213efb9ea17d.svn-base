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

import com.feather.cms.domain.CmsPage;
import com.feather.cms.service.ICmsPageService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 页面Controller
 */
@Controller
@RequestMapping("/cms/page")
public class CmsPageController extends BaseController {
    private String prefix = "cms/page";

    @Autowired
    private ICmsPageService cmsPageService;

    @RequiresPermissions("cms:page:view")
    @GetMapping()
    public String page() {
        return prefix + "/page";
    }

    /**
     * 查询页面列表
     */
    @RequiresPermissions("cms:page:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(CmsPage cmsPage) {
        startPage();
        List<CmsPage> list = cmsPageService.selectCmsPageList(cmsPage);
        return getDataTable(list);
    }

    /**
     * 导出页面列表
     */
    @RequiresPermissions("cms:page:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CmsPage cmsPage) {
        List<CmsPage> list = cmsPageService.selectCmsPageList(cmsPage);
        ExcelUtil<CmsPage> util = new ExcelUtil<CmsPage>(CmsPage.class);
        return util.exportExcel(list, "page");
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        CmsPage page = new CmsPage();
        mmap.put("page", page);
        return prefix + "/edit";
    }

    /**
     * 新增保存页面
     */
    @RequiresPermissions("cms:page:add")
    @Log(title = "页面", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CmsPage cmsPage) {
        return toAjax(cmsPageService.insertCmsPage(cmsPage));
    }

    /**
     * 修改页面
     */
    @GetMapping("/edit/{pageId}")
    public String edit(@PathVariable("pageId") Long pageId, ModelMap mmap) {
        CmsPage cmsPage = cmsPageService.selectCmsPageById(pageId);
        mmap.put("cmsPage", cmsPage);
        return prefix + "/edit";
    }

    /**
     * 修改保存页面
     */
    @RequiresPermissions("cms:page:edit")
    @Log(title = "页面", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CmsPage cmsPage) {
        return toAjax(cmsPageService.updateCmsPage(cmsPage));
    }

    /**
     * 删除页面
     */
    @RequiresPermissions("cms:page:remove")
    @Log(title = "页面", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(cmsPageService.deleteCmsPageByIds(ids));
    }
}
