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

import com.feather.aupipes.domain.AupInspectAddress;
import com.feather.aupipes.service.IAupInspectAddressService;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 巡检地址Controller
 * 
 * @author fancy
 * @date 2020-01-09
 */
@Controller
@RequestMapping("/aupipes/address")
public class AupInspectAddressController extends BaseController {
    private String prefix = "aupipes/address";

    @Autowired
    private IAupInspectAddressService aupInspectAddressService;

    @RequiresPermissions("aupipes:address:view")
    @GetMapping()
    public String address() {
        return prefix + "/address";
    }

    /** 查询巡检地址列表 */
    @RequiresPermissions("aupipes:address:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AupInspectAddress aupInspectAddress) {
        startPage();
        List<AupInspectAddress> list = aupInspectAddressService.selectAupInspectAddressList(aupInspectAddress);
        return getDataTable(list);
    }

    /**
     * 导出巡检地址列表
     */
    @RequiresPermissions("aupipes:address:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupInspectAddress aupInspectAddress) {
        List<AupInspectAddress> list = aupInspectAddressService.selectAupInspectAddressList(aupInspectAddress);
        ExcelUtil<AupInspectAddress> util = new ExcelUtil<AupInspectAddress>(AupInspectAddress.class);
        return util.exportExcel(list, "address");
    }

    /**
     * 新增保存巡检地址
     */
    @RequiresPermissions("aupipes:address:add")
    @Log(title = "巡检地址", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupInspectAddress aupInspectAddress) {
        return toAjax(aupInspectAddressService.insertAupInspectAddress(aupInspectAddress));
    }

    /**
     * 修改巡检地址
     */
    @GetMapping("/edit/{addressId}")
    public String edit(@PathVariable("addressId") Long addressId, ModelMap mmap) {
        AupInspectAddress aupInspectAddress = aupInspectAddressService.selectAupInspectAddressById(addressId);
        mmap.put("aupInspectAddress", aupInspectAddress);
        return prefix + "/edit";
    }

    /**
     * 修改保存巡检地址
     */
    @RequiresPermissions("aupipes:address:edit")
    @Log(title = "巡检地址", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupInspectAddress aupInspectAddress) {
        return toAjax(aupInspectAddressService.updateAupInspectAddress(aupInspectAddress));
    }

}
