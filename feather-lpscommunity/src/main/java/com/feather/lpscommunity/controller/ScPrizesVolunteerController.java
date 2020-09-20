package com.feather.lpscommunity.controller;

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

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScPrizesVolunteer;
import com.feather.lpscommunity.service.IScPrizesVolunteerService;

/**
 * 志愿者领奖Controller
 * 
 * @author fancy
 * @date 2019-11-22
 */
@Controller
@RequestMapping("/sc/prizesVolunteer")
public class ScPrizesVolunteerController extends BaseController {
    private String prefix = "lpscommunity/prizesVolunteer";

    @Autowired
    private IScPrizesVolunteerService scPrizesVolunteerService;

    @RequiresPermissions("sc:prizesVolunteer:view")
    @GetMapping()
    public String prizesVolunteer() {
        return prefix + "/prizesVolunteer";
    }

    /**
     * 查询志愿者领奖列表
     */
    @RequiresPermissions("sc:prizesVolunteer:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScPrizesVolunteer scPrizesVolunteer) {
        startPage();
        List<ScPrizesVolunteer> list = scPrizesVolunteerService.selectScPrizesVolunteerList(scPrizesVolunteer);
        return getDataTable(list);
    }

    /**
     * 导出志愿者领奖列表
     */
    @RequiresPermissions("sc:prizesVolunteer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScPrizesVolunteer scPrizesVolunteer) {
        List<ScPrizesVolunteer> list = scPrizesVolunteerService.selectScPrizesVolunteerList(scPrizesVolunteer);
        ExcelUtil<ScPrizesVolunteer> util = new ExcelUtil<ScPrizesVolunteer>(ScPrizesVolunteer.class);
        return util.exportExcel(list, "prizesVolunteer");
    }

    /**
     * 新增志愿者领奖
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存志愿者领奖
     */
    @RequiresPermissions("sc:prizesVolunteer:add")
    @Log(title = "志愿者领奖", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScPrizesVolunteer scPrizesVolunteer) {
        return toAjax(scPrizesVolunteerService.insertScPrizesVolunteer(scPrizesVolunteer));
    }

    /**
     * 修改志愿者领奖
     */
    @GetMapping("/edit/{pvId}")
    public String edit(@PathVariable("pvId") Long pvId, ModelMap mmap) {
        ScPrizesVolunteer scPrizesVolunteer = scPrizesVolunteerService.selectScPrizesVolunteerById(pvId);
        mmap.put("scPrizesVolunteer", scPrizesVolunteer);
        return prefix + "/edit";
    }

    /**
     * 修改保存志愿者领奖
     */
    @RequiresPermissions("sc:prizesVolunteer:edit")
    @Log(title = "志愿者领奖", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScPrizesVolunteer scPrizesVolunteer) {
        return toAjax(scPrizesVolunteerService.updateScPrizesVolunteer(scPrizesVolunteer));
    }

    /**
     * 删除志愿者领奖
     */
    @RequiresPermissions("sc:prizesVolunteer:remove")
    @Log(title = "志愿者领奖", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scPrizesVolunteerService.deleteScPrizesVolunteerByIds(ids));
    }

}