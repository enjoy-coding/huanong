package com.feather.aupipes.controller;

import java.util.List;
import java.util.UUID;

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
import com.feather.common.enums.BusinessType;
import com.feather.aupipes.domain.AupYjtables;
import com.feather.aupipes.service.IAupYjtablesService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.common.core.page.TableDataInfo;

/**
 * 预警信息统计Controller
 * 
 * @author fancy
 * @date 2020-01-06
 */
@Controller
@RequestMapping("/aupipes/yjtables")
public class AupYjtablesController extends BaseController
{
    private String prefix = "aupipes/yjtables";

    @Autowired
    private IAupYjtablesService aupYjtablesService;

    @GetMapping()
    public String yjtables()
    {
        return prefix + "/yjtables";
    }

    /**
     * 查询预警信息统计列表
     */

    @RequestMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupYjtables aupYjtables)
    {
        startPage();
        List<AupYjtables> list = aupYjtablesService.selectAupYjtablesList(aupYjtables);
        return getDataTable(list);
    }

    /**
     * 导出预警信息统计列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupYjtables aupYjtables)
    {
        List<AupYjtables> list = aupYjtablesService.selectAupYjtablesList(aupYjtables);
        ExcelUtil<AupYjtables> util = new ExcelUtil<AupYjtables>(AupYjtables.class);
        return util.exportExcel(list, "yjtables");
    }

    /**
     * 新增预警信息统计
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存预警信息统计
     */
    @RequiresPermissions("aupipes:yjtables:add")
    @Log(title = "预警信息统计", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupYjtables aupYjtables)
    {
        String tid = UUID.randomUUID().toString().replaceAll("-","");
        aupYjtables.setTid(tid);
        return toAjax(aupYjtablesService.insertAupYjtables(aupYjtables));
    }

    /**
     * 修改预警信息统计
     */
    @GetMapping("/edit/{tid}")
    public String edit(@PathVariable("tid") String tid, ModelMap mmap)
    {
        AupYjtables aupYjtables = aupYjtablesService.selectAupYjtablesById(tid);
        mmap.put("aupYjtables", aupYjtables);
        return prefix + "/edit";
    }

    /**
     * 修改保存预警信息统计
     */
    @RequiresPermissions("aupipes:yjtables:edit")
    @Log(title = "预警信息统计", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupYjtables aupYjtables)
    {
        return toAjax(aupYjtablesService.updateAupYjtables(aupYjtables));
    }

    /**
     * 删除预警信息统计
     */
    @RequiresPermissions("aupipes:yjtables:remove")
    @Log(title = "预警信息统计", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aupYjtablesService.deleteAupYjtablesByIds(ids));
    }
}
