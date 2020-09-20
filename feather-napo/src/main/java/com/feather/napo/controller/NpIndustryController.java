package com.feather.napo.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.framework.util.ShiroUtils;
import com.feather.napo.domain.NpIndustry;
import com.feather.napo.service.INpIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-29 9:26
 */
@Controller
@RequestMapping("/np/industry")
public class NpIndustryController extends BaseController {
    private String prefix = "napo/industry";

    @Autowired
    INpIndustryService npIndustryService;

    @GetMapping()
    public String index(ModelMap mmap) {
        return prefix + "/list";
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        NpIndustry npIndustry = new NpIndustry();
        mmap.put("npIndustry", npIndustry);
        return prefix + "/edit";
    }

    @GetMapping("/edit/{industryId}")
    public String edit(@PathVariable("industryId") Long industryId, ModelMap mmap) {
        NpIndustry npIndustry = npIndustryService.selectNpIndustryById(industryId);
        mmap.put("npIndustry", npIndustry);
        return prefix + "/edit";
    }

    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(NpIndustry npIndustry) {
        startPage();
        List<NpIndustry> list = npIndustryService.selectNpIndustryList(npIndustry);
        return getDataTable(list);
    }

    @Log(title = "那坡行业", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated NpIndustry npIndustry) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(npIndustryService.insertNpIndustry(npIndustry), npIndustry);
    }

    @Log(title = "那坡行业", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated NpIndustry npIndustry) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return AjaxResult.success(npIndustryService.updateNpIndustry(npIndustry));
    }

    @Log(title = "那坡行业", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(npIndustryService.deleteNpIndustryByIds(ids));
    }
}
