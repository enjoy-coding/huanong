package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupRepair;
import com.feather.aupipes.service.IAupRepairService;
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

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 维修记录Controller
 * 
 * @author fancy
 * @date 2020-04-01
 */
@Controller
@RequestMapping("/aupipes/repair")
public class AupRepairController extends BaseController
{
    private String prefix = "aupipes/repair";

    @Autowired
    private IAupRepairService aupRepairService;

    @Autowired
    private UidWorker uidWorker;

    /** 查询巡检任务列表 */
//    @RequiresPermissions("aupipes:repair:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AupRepair aupRepair) {
        startPage();
        List<AupRepair> list = aupRepairService.selectAupRepairList(aupRepair);

        return getDataTable(list);
    }

    @PostMapping("/geojson")
    @ResponseBody
    public AjaxResult geojson()
    {
        return aupRepairService.selectAupRepairListOfGeoJson();
    }

    @RequiresPermissions("aupipes:repair:view")
    @GetMapping()
    public String repair()
    {
        return prefix + "/repair";
    }


    /**
     * 导出维修记录列表
     */
//    @RequiresPermissions("aupipes:repair:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupRepair aupRepair)
    {
        List<AupRepair> list = aupRepairService.selectAupRepairList(aupRepair);
        ExcelUtil<AupRepair> util = new ExcelUtil<AupRepair>(AupRepair.class);
        return util.exportExcel(list, "维修详情表");
    }


    /**
     * 新增保存维修记录
     */
//    @RequiresPermissions("aupipes:repair:add")
    @Log(title = "维修记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupRepair aupRepair, HttpSession session)
    {
        if(aupRepair.getId() == null) {
            aupRepair.setId(uidWorker.getNextId());
            int result = aupRepairService.insertAupRepair(aupRepair);
            return result > 0 ? toAjax(1, aupRepair.getId()) : toAjax(0);
        }
        return toAjax(aupRepairService.updateAupRepair(aupRepair));
    }

    /**
     * 修改维修记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AupRepair aupRepair = aupRepairService.selectAupRepairById(id);
        mmap.put("aupRepair", aupRepair);
        return prefix + "/edit";
    }

    /**
     * 修改保存维修记录
     */
//    @RequiresPermissions("aupipes:repair:edit")
    @Log(title = "维修记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupRepair aupRepair)
    {
        return toAjax(aupRepairService.updateAupRepair(aupRepair));
    }

    /**
     * 删除文件信息
     */
//    @RequiresPermissions("aupipes:repair:remove")
    @Log(title = "维修记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aupRepairService.deleteAupRepairByIds(ids));
    }
}
