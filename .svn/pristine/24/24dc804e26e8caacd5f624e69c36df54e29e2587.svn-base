package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupInspectCoordinate;
import com.feather.aupipes.service.IAupInspectCoordinateService;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 巡检坐标Controller
 * 
 * @author fancy
 * @date 2020-03-13
 */
@Controller
@RequestMapping("/aupipes/coordinate")
public class AupInspectCoordinateController extends BaseController
{
    private String prefix = "aupipes/coordinate";

    @Autowired
    private IAupInspectCoordinateService aupInspectCoordinateService;

//    @RequiresPermissions("aupipes:coordinate:view")
    @GetMapping()
    public String coordinate()
    {
        return prefix + "/coordinate";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AupInspectCoordinate aupInspectCoordinate)
    {
        startPage();
        List<AupInspectCoordinate> list = aupInspectCoordinateService.selectAupInspectCoordinateList(aupInspectCoordinate);
        return getDataTable(list);
    }

    @PostMapping("/geojson")
    @ResponseBody
    public AjaxResult geojson(AupInspectCoordinate aupInspectCoordinate)
    {
        return aupInspectCoordinateService.selectAupInspectCoordinateGeoJson(aupInspectCoordinate);
    }

    /**
     * 导出巡检坐标列表
     */
//    @RequiresPermissions("aupipes:coordinate:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupInspectCoordinate aupInspectCoordinate)
    {
        List<AupInspectCoordinate> list = aupInspectCoordinateService.selectAupInspectCoordinateList(aupInspectCoordinate);
        ExcelUtil<AupInspectCoordinate> util = new ExcelUtil<AupInspectCoordinate>(AupInspectCoordinate.class);
        return util.exportExcel(list, "coordinate");
    }


    /**
     * 新增保存巡检坐标
     */
//    @RequiresPermissions("aupipes:coordinate:add")
    @Log(title = "巡检坐标", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupInspectCoordinate aupInspectCoordinate)
    {
        if(aupInspectCoordinate.getTaskId() == null){
            return AjaxResult.error("不允许插入空值");
        }

        return toAjax(aupInspectCoordinateService.insertAupInspectCoordinate(aupInspectCoordinate));
    }

    /**
     * 修改巡检坐标
     */
    @GetMapping("/edit/{coordinateId}")
    public String edit(@PathVariable("coordinateId") Long coordinateId, ModelMap mmap)
    {
        AupInspectCoordinate aupInspectCoordinate = aupInspectCoordinateService.selectAupInspectCoordinateById(coordinateId);
        mmap.put("aupInspectCoordinate", aupInspectCoordinate);
        return prefix + "/edit";
    }

    /**
     * 修改保存巡检坐标
     */
//    @RequiresPermissions("aupipes:coordinate:edit")
    @Log(title = "巡检坐标", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupInspectCoordinate aupInspectCoordinate)
    {
        return toAjax(aupInspectCoordinateService.updateAupInspectCoordinate(aupInspectCoordinate));
    }

    /**
     * 删除巡检坐标
     */
//    @RequiresPermissions("aupipes:coordinate:remove")
    @Log(title = "巡检坐标", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aupInspectCoordinateService.deleteAupInspectCoordinateByIds(ids));
    }
}
