package com.feather.aupipes.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.domain.AupPump;
import com.feather.aupipes.service.IAupPumpService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 泵房Controller
 *
 * @author fancy
 * @date 2020-04-17
 */
@Controller
@RequestMapping("/aupipes/pump")
public class AupPumpController extends BaseController {
    private String prefix = "aupipes/pump";

    @Autowired
    private IAupPumpService aupPumpService;

    @RequiresPermissions("aupipes:pump:view")
    @GetMapping()
    public String pump() {
        return prefix + "/pump";
    }

    /**
     * 查询探漏列列表
     */
    @RequiresPermissions("aupipes:pump:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupPump aupPump) {
        startPage();
        List<AupPump> list = aupPumpService.selectAupPumpList(aupPump);
        return getDataTable(list);
    }

    /**
     * 修改泵房
     */
    @GetMapping("/point/view/{id}")
    public String pointView(@PathVariable("id") Long id, ModelMap mmap) {
        AupPump aupPump = aupPumpService.selectAupPumpById(id);
        mmap.put("aupPump", aupPump);
        return prefix + "/point";
    }

    /**
     * 导出泵房列表
     */
    @RequiresPermissions("aupipes:pump:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupPump aupPump) {
        List<AupPump> list = aupPumpService.selectAupPumpList(aupPump);
        ExcelUtil<AupPump> util = new ExcelUtil<AupPump>(AupPump.class);
        return util.exportExcel(list, "pump");
    }

    /**
     * 新增保存泵房
     */
    @RequiresPermissions("aupipes:pump:add")
    @Log(title = "泵房", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupPump aupPump) {
        return toAjax(aupPumpService.insertAupPump(aupPump));
    }

    /**
     * 修改泵房
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        AupPump aupPump = aupPumpService.selectAupPumpById(id);
        mmap.put("aupPump", aupPump);
        return prefix + "/edit";
    }

    /**
     * 修改保存泵房
     */
    @RequiresPermissions("aupipes:pump:edit")
    @Log(title = "泵房", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupPump aupPump) {
        return toAjax(aupPumpService.updateAupPump(aupPump));
    }

    /**
     * 新增保存泵房
     */
    @Log(title = "泵房", businessType = BusinessType.INSERT)
    @PostMapping("/addList")
    @ResponseBody
    public AjaxResult text(@RequestBody List<AupPump> strList) {
        return toAjax(1);
        // return toAjax(aupPumpService.insertAupPump(aupPump));
    }

}