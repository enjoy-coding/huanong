package com.feather.smart.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.smart.domain.ZhsqCl;
import com.feather.smart.service.IZhsqClService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆Controller
 * 
 * @author fancy
 * @date 2020-05-15
 */
@Controller
@RequestMapping("/smartcommunity/CL")
public class ZhsqClController extends BaseController
{
    private String prefix = "smartcommunity/CL";

    @Autowired
    private IZhsqClService zhsqClService;

    @RequiresPermissions("smartcommunity:CL:view")
    @GetMapping()
    public String CL()
    {
        return prefix + "/CL";
    }

    /**
     * 查询车辆列表
     */
    @RequiresPermissions("smartcommunity:CL:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ZhsqCl zhsqCl)
    {
        startPage();
        List<ZhsqCl> list = zhsqClService.selectZhsqClList(zhsqCl);
        return getDataTable(list);
    }

    /**
     * 导出车辆列表
     */
    @RequiresPermissions("smartcommunity:CL:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhsqCl zhsqCl)
    {
        List<ZhsqCl> list = zhsqClService.selectZhsqClList(zhsqCl);
        ExcelUtil<ZhsqCl> util = new ExcelUtil<ZhsqCl>(ZhsqCl.class);
        return util.exportExcel(list, "CL");
    }

    /**
     * 新增车辆
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        ZhsqCl CL = new ZhsqCl();
        mmap.put("CL", CL);
        return prefix + "/add";
    }

    /**
     * 新增保存车辆
     */
    @RequiresPermissions("smartcommunity:CL:add")
    @Log(title = "车辆", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZhsqCl zhsqCl)
    {
        return toAjax(zhsqClService.insertZhsqCl(zhsqCl));
    }

    /**
     * 修改车辆
     */
    @GetMapping("/edit/{clid}")
    public String edit(@PathVariable("clid") String clid, ModelMap mmap)
    {
        ZhsqCl CL = zhsqClService.selectZhsqClById(clid);
        mmap.put("CL", CL);
        return prefix + "/edit";
    }

    /**
     * 修改保存车辆
     */
    @RequiresPermissions("smartcommunity:CL:edit")
    @Log(title = "车辆", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZhsqCl zhsqCl)
    {
        return toAjax(zhsqClService.updateZhsqCl(zhsqCl));
    }

    /**
     * 删除车辆
     */
    @RequiresPermissions("smartcommunity:CL:remove")
    @Log(title = "车辆", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zhsqClService.deleteZhsqClByIds(ids));
    }
}
