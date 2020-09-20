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

import com.feather.aupipes.domain.AupWarring;
import com.feather.aupipes.service.IAupWarringService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 预警记录信息Controller
 * 
 * @author fancy
 * @date 2020-01-15
 */
@Controller
@RequestMapping("/aupipes/warring")
public class AupWarringController extends BaseController {
    private String prefix = "aupipes/warring";

    @Autowired
    private IAupWarringService aupWarringService;

    @RequiresPermissions("aupipes:warring:view")
    @GetMapping()
    public String warring() {
        return prefix + "/warring";
    }

    /**
     * 查询预警记录信息列表
     */
    @RequiresPermissions("aupipes:warring:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupWarring aupWarring) {
        startPage();
        List<AupWarring> list = aupWarringService.selectAupWarringList(aupWarring);
        return getDataTable(list);
    }

    /**
     * 导出预警记录信息列表
     */
    @RequiresPermissions("aupipes:warring:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupWarring aupWarring) {
        List<AupWarring> list = aupWarringService.selectAupWarringList(aupWarring);
        ExcelUtil<AupWarring> util = new ExcelUtil<AupWarring>(AupWarring.class);
        return util.exportExcel(list, "warring");
    }

    /**
     * 新增预警记录信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存预警记录信息
     */
    @RequiresPermissions("aupipes:warring:add")
    @Log(title = "预警记录信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupWarring aupWarring) {
        return toAjax(aupWarringService.insertAupWarring(aupWarring));
    }

    /**
     * 修改预警记录信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        AupWarring aupWarring = aupWarringService.selectAupWarringById(id);
        mmap.put("aupWarring", aupWarring);
        return prefix + "/edit";
    }

    /**
     * 修改保存预警记录信息
     */
    @RequiresPermissions("aupipes:warring:edit")
    @Log(title = "预警记录信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupWarring aupWarring) {
        return toAjax(aupWarringService.updateAupWarring(aupWarring));
    }

    /**
     * 删除预警记录信息
     */
    @RequiresPermissions("aupipes:warring:remove")
    @Log(title = "预警记录信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(aupWarringService.deleteAupWarringByIds(ids));
    }

    @RequestMapping("/getAupWarringList")
    @ResponseBody
    public List<AupWarring> getAupWarringList(AupWarring aupWarring) {
        List<AupWarring> aupWarrings = aupWarringService.selectAupWarringList(aupWarring);
        return aupWarrings;
    }
}
