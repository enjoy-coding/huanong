package com.feather.aupipes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.domain.AupServiceInfo;
import com.feather.aupipes.service.IAupServiceInfoService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 设备Controller
 * 
 * @author fancy
 * @date 2020-06-08
 */
@Controller
@RequestMapping("/aupipes/serviceinfo")
public class AupServiceInfoController extends BaseController {
    private String prefix = "aupipes/serviceinfo";

    @Autowired
    private IAupServiceInfoService aupServiceInfoService;

    // @RequiresPermissions("aupipes:info:view")
    @GetMapping()
    public String info() {
        return prefix + "/info";
    }

    /**
     * 查询设备列表
     */
    // @RequiresPermissions("aupipes:info:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupServiceInfo aupServiceInfo) {
        startPage();
        List<AupServiceInfo> list = aupServiceInfoService.selectAupServiceInfoList(aupServiceInfo);
        return getDataTable(list);
    }

    /**
     * 导出设备列表
     */
    // @RequiresPermissions("aupipes:info:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupServiceInfo aupServiceInfo) {
        List<AupServiceInfo> list = aupServiceInfoService.selectAupServiceInfoList(aupServiceInfo);
        ExcelUtil<AupServiceInfo> util = new ExcelUtil<AupServiceInfo>(AupServiceInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 新增设备
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        AupServiceInfo info = new AupServiceInfo();
        mmap.put("info", info);
        return prefix + "/edit";
    }

    /**
     * 新增保存设备
     */
    // @RequiresPermissions("aupipes:info:add")
    @Log(title = "设备", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupServiceInfo aupServiceInfo) {
        return toAjax(aupServiceInfoService.insertAupServiceInfo(aupServiceInfo), aupServiceInfo);
    }

    /**
     * 修改设备
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        AupServiceInfo info = aupServiceInfoService.selectAupServiceInfoById(id);
        mmap.put("info", info);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备
     */
    // @RequiresPermissions("aupipes:info:edit")
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupServiceInfo aupServiceInfo) {
        return toAjax(aupServiceInfoService.updateAupServiceInfo(aupServiceInfo));
    }

    /**
     * 删除设备
     */
    // @RequiresPermissions("aupipes:info:remove")
    @Log(title = "设备", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(aupServiceInfoService.deleteAupServiceInfoByIds(ids));
    }
}
