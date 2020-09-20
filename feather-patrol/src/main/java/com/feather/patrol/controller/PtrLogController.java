package com.feather.patrol.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.patrol.domain.PtrLog;
import com.feather.patrol.service.IPtrLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 巡检日志Controller
 */
@Api(tags = "巡检日志")
@Controller
@RequestMapping("/patrol/log")
public class PtrLogController extends BaseController {
    private String prefix = "patrol/log";

    @Autowired
    private IPtrLogService ptrLogService;

    @RequiresPermissions("patrol:log:view")
    @GetMapping()
    public String log() {
        return prefix + "/log";
    }

    /**
     * 查询巡检日志列表
     */
    @RequiresPermissions("patrol:log:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(PtrLog ptrLog) {
        startPage();
        List<PtrLog> list = ptrLogService.selectPtrLogList(ptrLog);
        return getDataTable(list);
    }

    /**
     * 导出巡检日志列表
     */
    @RequiresPermissions("patrol:log:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PtrLog ptrLog) {
        List<PtrLog> list = ptrLogService.selectPtrLogList(ptrLog);
        ExcelUtil<PtrLog> util = new ExcelUtil<PtrLog>(PtrLog.class);
        return util.exportExcel(list, "log");
    }

    /**
     * 修改巡检日志
     */
    @GetMapping("/edit/{logId}")
    public String edit(@PathVariable("logId") Long logId, ModelMap mmap) {
        PtrLog log = ptrLogService.selectPtrLogById(logId);
        mmap.put("log", log);
        return prefix + "/edit";
    }

    /**
     * 修改保存巡检日志
     */
    @RequiresPermissions("patrol:log:edit")
    @Log(title = "巡检日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PtrLog ptrLog) {
        return toAjax(ptrLogService.updatePtrLog(ptrLog));
    }

    /**
     * 删除巡检日志
     */
    @RequiresPermissions("patrol:log:remove")
    @Log(title = "巡检日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(ptrLogService.deletePtrLogByIds(ids));
    }

    /**
     * 新增保存巡检日志
     */
    @Log(title = "巡检日志", businessType = BusinessType.INSERT)
    @ApiOperation("新增巡检日志")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "qrcode", value = "二维码", required = true), //
            @ApiImplicitParam(name = "coordinate", value = "gps坐标", required = true), //
            @ApiImplicitParam(name = "altitude", value = "gps高程", required = false), //
            @ApiImplicitParam(name = "address", value = "地址名称", required = false), //
            @ApiImplicitParam(name = "issue", value = "问题说明", required = false), //
            @ApiImplicitParam(name = "standards", value = "异常点", required = false), //
            @ApiImplicitParam(name = "facades", value = "现场照片", required = false) //
    })
    @PostMapping("/api/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("qrcode") String qrcode, @RequestParam("coordinate") String coordinate,
            @RequestParam("altitude") String altitude, @RequestParam("address") String address,
            @RequestParam("issue") String issue, @RequestParam("standards") Long[] standards,
            @RequestParam("facades") MultipartFile[] facades) {
        ptrLogService.insertPtrLog(qrcode, coordinate, altitude, address, issue, standards, facades);
        return AjaxResult.success();
    }
}
