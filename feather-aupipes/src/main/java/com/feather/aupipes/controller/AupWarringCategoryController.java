package com.feather.aupipes.controller;

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

import com.feather.aupipes.domain.AupWarringCategory;
import com.feather.aupipes.service.IAupWarringCategoryService;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 预警类型Controller
 *
 * @author fancy
 * @date 2019-12-20
 */
@Controller
@RequestMapping("/aupipes/warringCategory")
public class AupWarringCategoryController extends BaseController {
    private String prefix = "aupipes/category";

    @Autowired
    private IAupWarringCategoryService aupWarringCategoryService;

    @RequiresPermissions("aupipes:category:view")
    @GetMapping()
    public String category() {
        return prefix + "/category";
    }

    /**
     * 导出预警类型列表
     */
    @RequiresPermissions("aupipes:category:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupWarringCategory aupWarringCategory) {
        List<AupWarringCategory> list = aupWarringCategoryService.selectAupWarringCategoryList(aupWarringCategory);
        ExcelUtil<AupWarringCategory> util = new ExcelUtil<AupWarringCategory>(AupWarringCategory.class);
        return util.exportExcel(list, "category");
    }

    /**
     * 新增保存预警类型
     */
    @RequiresPermissions("aupipes:category:add")
    @Log(title = "预警类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupWarringCategory aupWarringCategory) {
        return toAjax(aupWarringCategoryService.insertAupWarringCategory(aupWarringCategory));
    }

    /**
     * 修改预警类型
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        AupWarringCategory aupWarringCategory = aupWarringCategoryService.selectAupWarringCategoryById(id);
        mmap.put("aupWarringCategory", aupWarringCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存预警类型
     */
    @RequiresPermissions("aupipes:category:edit")
    @Log(title = "预警类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupWarringCategory aupWarringCategory) {
        return toAjax(aupWarringCategoryService.updateAupWarringCategory(aupWarringCategory));
    }

    /**
     * 加载预警列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData(String name) {
        List<Ztree> ztrees = aupWarringCategoryService.selectWarringCategoryTree(name);
        return ztrees;
    }
}