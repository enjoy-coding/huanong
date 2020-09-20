package com.feather.aupipes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.domain.AupRepairPipeline;
import com.feather.aupipes.service.IAupRepairPipelineService;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 【管线设备】Controller
 * 
 * @author fancy
 * @date 2020-04-16
 */
@Controller
@RequestMapping("/aupipes/pipeline")
public class AupRepairPipelineController extends BaseController {
    private String prefix = "aupipes/pipeline";

    @Autowired
    private IAupRepairPipelineService aupRepairPipelineService;

    // @RequiresPermissions("aupipes:pipeline:view")
    @GetMapping()
    public String pipeline() {
        return prefix + "/pipeline";
    }

    /**
     * 查询【管线设备】列表
     */
    // @RequiresPermissions("aupipes:info:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AupRepairPipeline aupRepairPipeline) {
        startPage();
        List<AupRepairPipeline> list = aupRepairPipelineService.selectAupRepairPipelineList(aupRepairPipeline);
        return getDataTable(list);
    }

    /**
     * 导出【管线设备】列表
     */
    // @RequiresPermissions("aupipes:pipeline:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupRepairPipeline aupRepairPipeline) {
        List<AupRepairPipeline> list = aupRepairPipelineService.selectAupRepairPipelineList(aupRepairPipeline);
        ExcelUtil<AupRepairPipeline> util = new ExcelUtil<AupRepairPipeline>(AupRepairPipeline.class);
        return util.exportExcel(list, "pipeline");
    }

    /**
     * 新增保存【管线设备】
     */
    // @RequiresPermissions("aupipes:pipeline:add")
    @Log(title = "【管线设备】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupRepairPipeline aupRepairPipeline) {
        return toAjax(aupRepairPipelineService.insertAupRepairPipeline(aupRepairPipeline));
    }

    // @RequiresPermissions("aupipes:pipeline:add")
    @Log(title = "【管线设备】", businessType = BusinessType.INSERT)
    @PostMapping("/addList")
    @ResponseBody
    public AjaxResult addListSave(@RequestBody List<AupRepairPipeline> aupRepairPipelines) {
        return toAjax(aupRepairPipelineService.insertAupRepairPipelines(aupRepairPipelines));
    }

    /**
     * 修改【管线设备】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        AupRepairPipeline aupRepairPipeline = aupRepairPipelineService.selectAupRepairPipelineById(id);
        mmap.put("aupRepairPipeline", aupRepairPipeline);
        return prefix + "/edit";
    }

    /**
     * 修改保存【管线设备】
     */
    // @RequiresPermissions("aupipes:pipeline:edit")
    @Log(title = "【管线设备】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupRepairPipeline aupRepairPipeline) {
        return toAjax(aupRepairPipelineService.updateAupRepairPipeline(aupRepairPipeline));
    }

    /**
     * 删除
     */
    // @RequiresPermissions("aupipes:pipeline:remove")
    @Log(title = "管线设备", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(aupRepairPipelineService.deleteAupRepairPipelineByIds(ids));
    }
}
