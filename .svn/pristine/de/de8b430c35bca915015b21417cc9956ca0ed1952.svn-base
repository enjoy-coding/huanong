package com.feather.napo.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.framework.util.ShiroUtils;
import com.feather.napo.domain.NpInfoReadLog;
import com.feather.napo.service.INpInfoReadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author nothing
 * @date 2020-07-29 11:26
 */
@Controller
@RequestMapping("/np/readLog")
public class NpInfoReadLogController extends BaseController {
    private String prefix = "napo/readLog";

    @Autowired
    INpInfoReadLogService npInfoReadLogService;

    @GetMapping()
    public String index(ModelMap mmap) {
        return prefix + "/list";
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        NpInfoReadLog npInfoReadLog = new NpInfoReadLog();
        mmap.put("npInfoReadLog", npInfoReadLog);
        return prefix + "/edit";
    }

    @GetMapping("/edit/{readId}")
    public String edit(@PathVariable("readId") Long readId, ModelMap mmap) {
        NpInfoReadLog npInfoReadLog = npInfoReadLogService.selectNpInfoReadLogById(readId);
        mmap.put("npInfoReadLog", npInfoReadLog);
        return prefix + "/edit";
    }

    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(NpInfoReadLog npInfoReadLog) {
        startPage();
        List<NpInfoReadLog> list = npInfoReadLogService.selectNpInfoReadLogList(npInfoReadLog);
        return getDataTable(list);
    }

    @Log(title = "信息阅读记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated NpInfoReadLog npInfoReadLog) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(npInfoReadLogService.insertNpInfoReadLog(npInfoReadLog), npInfoReadLog);
    }

    @Log(title = "信息阅读记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated NpInfoReadLog npInfoReadLog) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return AjaxResult.success(npInfoReadLogService.updateNpInfoReadLog(npInfoReadLog));
    }

    @Log(title = "信息阅读记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(npInfoReadLogService.deleteNpInfoReadLogByIds(ids));
    }
}
