package com.feather.napo.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.framework.util.ShiroUtils;
import com.feather.napo.domain.NpDevice;
import com.feather.napo.domain.NpMobileDevice;
import com.feather.napo.service.INpDeviceService;
import com.feather.napo.service.INpMobileDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author nothing
 * @date 2020-07-29 9:26
 */
@Controller
@RequestMapping("/np/mobileDevice")
public class NpMobileDeviceController extends BaseController {
    private String prefix = "napo/mobileDevice";

    @Autowired
    INpMobileDeviceService npMobileDeviceService;

    @GetMapping()
    public String index(ModelMap mmap) {
        return prefix + "/list";
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        NpMobileDevice npMobileDevice = new NpMobileDevice();
        mmap.put("npMobileDevice", npMobileDevice);
        return prefix + "/edit";
    }

    @GetMapping("/edit/{deviceId}")
    public String edit(@PathVariable("deviceId") String deviceId, ModelMap mmap) {
        NpMobileDevice npMobileDevice = npMobileDeviceService.selectNpMobileDeviceById(deviceId);
        mmap.put("npMobileDevice", npMobileDevice);
        return prefix + "/edit";
    }

    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(NpMobileDevice npMobileDevice) {
        startPage();
        List<NpMobileDevice> list = npMobileDeviceService.selectNpMobileDeviceList(npMobileDevice);
        return getDataTable(list);
    }

    @Log(title = "移动设备", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated NpMobileDevice npMobileDevice) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(npMobileDeviceService.insertNpMobileDevice(npMobileDevice), npMobileDevice);
    }

    /*@Log(title = "移动设备", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated NpMobileDevice npMobileDevice) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return AjaxResult.success(npDeviceService.updateNpMobileDevice(npMobileDevice));
    }*/

    @Log(title = "移动设备", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(npMobileDeviceService.deleteNpMobileDeviceByIds(ids));
    }
}
