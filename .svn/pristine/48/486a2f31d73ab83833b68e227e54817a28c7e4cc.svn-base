package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupInspectService;
import com.feather.aupipes.service.IAupInspectServiceService;
import com.feather.common.annotation.ClearPage;
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
 * 巡检记录设备Controller
 * 
 * @author fancy
 * @date 2020-06-08
 */
@Controller
@RequestMapping("/aupipes/inspectservice")
public class AupInspectServiceController extends BaseController
{
    private String prefix = "aupipes/inspectservice";

    @Autowired
    private IAupInspectServiceService aupInspectServiceService;

//    @RequiresPermissions("aupipes:service:view")
    @GetMapping()
    public String service()
    {
        return prefix + "/service";
    }

    /**
     * 查询巡检记录设备列表
     */
//    @RequiresPermissions("aupipes:service:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupInspectService aupInspectService)
    {
        startPage();
        List<AupInspectService> list = aupInspectServiceService.selectAupInspectServiceList(aupInspectService);
        return getDataTable(list);
    }

    /**
     * 导出巡检记录设备列表
     */
//    @RequiresPermissions("aupipes:service:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupInspectService aupInspectService)
    {
        List<AupInspectService> list = aupInspectServiceService.selectAupInspectServiceList(aupInspectService);
        ExcelUtil<AupInspectService> util = new ExcelUtil<AupInspectService>(AupInspectService.class);
        return util.exportExcel(list, "service");
    }

    /**
     * 新增巡检记录设备
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        AupInspectService service = new AupInspectService();
        mmap.put("service", service);
        return prefix + "/edit";
    }

    /**
     * 新增保存巡检记录设备
     */
//    @RequiresPermissions("aupipes:service:add")
    @Log(title = "巡检记录设备", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupInspectService aupInspectService)
    {

        if(aupInspectService.getTaskId() == null || aupInspectService.getDetailId() == null){
            return AjaxResult.error("不允许插入空值");
        }

        return toAjax(aupInspectServiceService.insertAupInspectService(aupInspectService), aupInspectService);
    }

    /**
     * 修改巡检记录设备
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AupInspectService service = aupInspectServiceService.selectAupInspectServiceById(id);
        mmap.put("service", service);
        return prefix + "/edit";
    }

    /**
     * 修改保存巡检记录设备
     */
//    @RequiresPermissions("aupipes:service:edit")
    @Log(title = "巡检记录设备", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupInspectService aupInspectService)
    {
        return toAjax(aupInspectServiceService.updateAupInspectService(aupInspectService));
    }

    /**
     * 删除巡检记录设备
     */
//    @RequiresPermissions("aupipes:service:remove")
    @Log(title = "巡检记录设备", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aupInspectServiceService.deleteAupInspectServiceByIds(ids));
    }

    /**
     * app修改保存巡检记录设备
     */
//    @RequiresPermissions("aupipes:service:edit")
    @PostMapping("/appEdit")
    @ResponseBody
    public AjaxResult editAppSave(AupInspectService aupInspectService)
    {
        return toAjax(aupInspectServiceService.updateAppAupInspectService(aupInspectService));
    }

}
