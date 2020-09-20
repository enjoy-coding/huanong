package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupPumpPoint;
import com.feather.aupipes.service.IAupPumpPointService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.config.UidWorker;
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
 * 泵房点位Controller
 *
 * @author fancy
 * @date 2020-04-17
 */
@Controller
@RequestMapping("/aupipes/point")
public class AupPumpPointController extends BaseController
{
    private String prefix = "aupipes/point";

    @Autowired
    private IAupPumpPointService aupPumpPointService;

    @Autowired
    private UidWorker uidWorker;

    @RequiresPermissions("aupipes:point:view")
    @GetMapping()
    public String point()
    {
        return prefix + "/point";
    }


    /**
     * 导出泵房点位列表
     */
    @RequiresPermissions("aupipes:point:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupPumpPoint aupPumpPoint)
    {
        List<AupPumpPoint> list = aupPumpPointService.selectAupPumpPointList(aupPumpPoint);
        ExcelUtil<AupPumpPoint> util = new ExcelUtil<AupPumpPoint>(AupPumpPoint.class);
        return util.exportExcel(list, "point");
    }

    /**
     * 查询探漏列列表
     */
    @RequiresPermissions("aupipes:point:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupPumpPoint aupPumpPoint) {
        startPage();
        List<AupPumpPoint> list =aupPumpPointService.selectAupPumpPointList(aupPumpPoint);
        return getDataTable(list);
    }

    /**
     * 新增用户
     */
    @GetMapping("/add/{pumpId}")
    public String add(@PathVariable("pumpId") String pumpId,ModelMap mmap) {
        AupPumpPoint aupPumpPoint = new AupPumpPoint();
        aupPumpPoint.setPumpId(Long.parseLong(pumpId));
        mmap.put("aupPumpPoint", aupPumpPoint);
        return   "aupipes/pump/pointEdit";
    }
    /**
     * 新增保存泵房点位
     */
    @RequiresPermissions("aupipes:point:add")
    @Log(title = "泵房点位", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupPumpPoint aupPumpPoint)
    {
        aupPumpPoint.setId(uidWorker.getNextId());
        return toAjax(aupPumpPointService.insertAupPumpPoint(aupPumpPoint),aupPumpPoint);
    }

    /**
     * 修改泵房点位
     */
    @GetMapping("/edit/{pumpId}")
    public String edit(@PathVariable("pumpId") Long pumId, ModelMap mmap)
    {
        AupPumpPoint aupPumpPoint = aupPumpPointService.selectAupPumpPointById(pumId);
        mmap.put("aupPumpPoint", aupPumpPoint);
        return "aupipes/pump/pointEdit";
    }

    /**
     * 修改保存泵房点位
     */
    @RequiresPermissions("aupipes:point:edit")
    @Log(title = "泵房点位", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupPumpPoint aupPumpPoint)
    {
        return toAjax(aupPumpPointService.updateAupPumpPoint(aupPumpPoint),aupPumpPoint);
    }

    /**
     * 删除泵房点位
     */
    @RequiresPermissions("aupipes:point:remove")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(aupPumpPointService.deleteAupPumpPointByIds(ids));
    }



}