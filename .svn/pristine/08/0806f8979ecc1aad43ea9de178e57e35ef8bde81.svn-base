package com.feather.napo.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.framework.util.ShiroUtils;
import com.feather.napo.domain.NpCar;
import com.feather.napo.service.INpCarService;
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
@RequestMapping("/np/car")
public class NpCarController extends BaseController {
    private String prefix = "napo/car";

    @Autowired
    INpCarService npCarService;

    @GetMapping()
    public String index(ModelMap mmap) {
        return prefix + "/list";
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        NpCar npCar = new NpCar();
        mmap.put("npCar", npCar);
        return prefix + "/edit";
    }

    @GetMapping("/edit/{carId}")
    public String edit(@PathVariable("carId") Long carId, ModelMap mmap) {
        NpCar npCar = npCarService.selectNpCarById(carId);
        mmap.put("npCar", npCar);
        return prefix + "/edit";
    }

    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(NpCar npCar) {
        startPage();
        List<NpCar> list = npCarService.selectNpCarList(npCar);
        return getDataTable(list);
    }

    @Log(title = "那坡车辆", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated NpCar npCar) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(npCarService.insertNpCar(npCar), npCar);
    }

    @Log(title = "那坡车辆", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated NpCar npCar) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return AjaxResult.success(npCarService.updateNpCar(npCar));
    }

    @Log(title = "那坡车辆", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(npCarService.deleteNpCarByIds(ids));
    }
}
