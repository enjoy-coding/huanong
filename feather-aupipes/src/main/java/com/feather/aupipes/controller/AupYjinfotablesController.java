package com.feather.aupipes.controller;

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
import com.feather.common.enums.BusinessType;
import com.feather.aupipes.domain.AupYjinfotables;
import com.feather.aupipes.service.IAupYjinfotablesService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.common.core.page.TableDataInfo;

/**
 * 预警信息详情Controller
 * 
 * @author fancy
 * @date 2020-01-07
 */
@Controller
@RequestMapping("/aupipes/yjinfotables")
public class AupYjinfotablesController extends BaseController
{
    private String prefix = "aupipes/yjinfotables";

    @Autowired
    private IAupYjinfotablesService aupYjinfotablesService;


    @GetMapping()
    public String yjinfotables()
    {
        return prefix + "/yjinfotables";
    }

    /**
     * 查询预警信息详情列表
     */

    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupYjinfotables aupYjinfotables)
    {
        startPage();
        List<AupYjinfotables> list = aupYjinfotablesService.selectAupYjinfotablesList(aupYjinfotables);
        return getDataTable(list);
    }

    /**
     * 导出预警信息详情列表
     */

    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupYjinfotables aupYjinfotables)
    {
        List<AupYjinfotables> list = aupYjinfotablesService.selectAupYjinfotablesList(aupYjinfotables);
        ExcelUtil<AupYjinfotables> util = new ExcelUtil<AupYjinfotables>(AupYjinfotables.class);
        return util.exportExcel(list, "yjinfotables");
    }

    /**
     * 新增预警信息详情
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存预警信息详情
     */
    @RequiresPermissions("aupipes:yjinfotables:add")
    @Log(title = "预警信息详情", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupYjinfotables aupYjinfotables)
    {
        return toAjax(aupYjinfotablesService.insertAupYjinfotables(aupYjinfotables));
    }

    /**
     * 修改预警信息详情
     */
    @GetMapping("/edit/{itid}")
    public String edit(@PathVariable("itid") String itid, ModelMap mmap)
    {
        AupYjinfotables aupYjinfotables = aupYjinfotablesService.selectAupYjinfotablesById(itid);
        mmap.put("aupYjinfotables", aupYjinfotables);
        return prefix + "/edit";
    }

    /**
     * 修改保存预警信息详情
     */
    @RequiresPermissions("aupipes:yjinfotables:edit")
    @Log(title = "预警信息详情", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupYjinfotables aupYjinfotables)
    {
        return toAjax(aupYjinfotablesService.updateAupYjinfotables(aupYjinfotables));
    }

    /**
     * 删除预警信息详情
     */
    @RequiresPermissions("aupipes:yjinfotables:remove")
    @Log(title = "预警信息详情", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aupYjinfotablesService.deleteAupYjinfotablesByIds(ids));
    }
}
