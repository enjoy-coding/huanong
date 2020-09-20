package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupInspectLog;
import com.feather.aupipes.service.IAupInspectLogService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 巡检任务微信消息推送日志Controller
 * 
 * @author fancy
 * @date 2020-06-20
 */
@Controller
@RequestMapping("/aupipes/log")
public class AupInspectLogController extends BaseController
{
    private String prefix = "aupipes/log";

    @Autowired
    private IAupInspectLogService aupInspectLogService;

    @RequiresPermissions("aupipes:log:view")
    @GetMapping()
    public String log()
    {
        return prefix + "/log";
    }

    /**
     * 查询巡检任务微信消息推送日志列表
     */
    @RequiresPermissions("aupipes:log:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupInspectLog aupInspectLog)
    {
        startPage();
        List<AupInspectLog> list = aupInspectLogService.selectAupInspectLogList(aupInspectLog);
        return getDataTable(list);
    }

    /**
     * 导出巡检任务微信消息推送日志列表
     */
    @RequiresPermissions("aupipes:log:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupInspectLog aupInspectLog)
    {
        List<AupInspectLog> list = aupInspectLogService.selectAupInspectLogList(aupInspectLog);
        ExcelUtil<AupInspectLog> util = new ExcelUtil<AupInspectLog>(AupInspectLog.class);
        return util.exportExcel(list, "log");
    }

    /**
     * 新增巡检任务微信消息推送日志
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        AupInspectLog log = new AupInspectLog();
        mmap.put("log", log);
        return prefix + "/edit";
    }

    /**
     * 新增保存巡检任务微信消息推送日志
     */
    @RequiresPermissions("aupipes:log:add")
    @Log(title = "巡检任务微信消息推送日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupInspectLog aupInspectLog)
    {
        return toAjax(aupInspectLogService.insertAupInspectLog(aupInspectLog), aupInspectLog);
    }

    /**
     * 修改巡检任务微信消息推送日志
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AupInspectLog log = aupInspectLogService.selectAupInspectLogById(id);
        mmap.put("log", log);
        return prefix + "/edit";
    }

    /**
     * 修改保存巡检任务微信消息推送日志
     */
    @RequiresPermissions("aupipes:log:edit")
    @Log(title = "巡检任务微信消息推送日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupInspectLog aupInspectLog)
    {
        return toAjax(aupInspectLogService.updateAupInspectLog(aupInspectLog));
    }

    /**
     * 删除巡检任务微信消息推送日志
     */
    @RequiresPermissions("aupipes:log:remove")
    @Log(title = "巡检任务微信消息推送日志", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aupInspectLogService.deleteAupInspectLogByIds(ids));
    }
}
