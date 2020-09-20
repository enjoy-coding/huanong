package com.feather.napo.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.framework.util.ShiroUtils;
import com.feather.napo.domain.NpDevice;
import com.feather.napo.service.INpDeviceService;
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
@RequestMapping("/np/device")
public class NpDeviceController extends BaseController {
    private String prefix = "napo/device";

    @Autowired
    INpDeviceService npDeviceService;

    @GetMapping()
    public String index(ModelMap mmap) {
        return prefix + "/list";
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        NpDevice npDevice = new NpDevice();
        mmap.put("npDevice", npDevice);
        return prefix + "/edit";
    }

    @GetMapping("/edit/{deviceId}")
    public String edit(@PathVariable("deviceId") Long deviceId, ModelMap mmap) {
        NpDevice npDevice = npDeviceService.selectNpDeviceById(deviceId);
        mmap.put("npDevice", npDevice);
        return prefix + "/edit";
    }

    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(NpDevice npDevice) {
        startPage();
        List<NpDevice> list = npDeviceService.selectNpDeviceList(npDevice);
        return getDataTable(list);
    }

    @Log(title = "设施设备", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated NpDevice npDevice) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(npDeviceService.insertNpDevice(npDevice), npDevice);
    }

    @Log(title = "设施设备", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated NpDevice npDevice) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return AjaxResult.success(npDeviceService.updateNpDevice(npDevice));
    }

    @Log(title = "设施设备", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(npDeviceService.deleteNpDeviceByIds(ids));
    }
}
