package com.feather.smart.controller;

import java.util.List;
import java.util.Map;

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
import com.feather.smart.domain.ZhsqTsjy;
import com.feather.smart.service.IZhsqTsjyService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.common.core.page.TableDataInfo;

/**
 * 投诉建议Controller
 * 
 * @author fancy
 * @date 2020-05-14
 */
@Controller
@RequestMapping("/smartcommunity/TSJY")
public class ZhsqTsjyController extends BaseController
{
    private String prefix = "smartcommunity/TSJY";

    @Autowired
    private IZhsqTsjyService zhsqTsjyService;

    @RequiresPermissions("smartcommunity:TSJY:view")
    @GetMapping()
    public String TSJY()
    {
        return prefix + "/TSJY";
    }

    /**
     * 查询投诉建议列表
     */
    @RequiresPermissions("smartcommunity:TSJY:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ZhsqTsjy zhsqTsjy)
    {
        startPage();
        List<ZhsqTsjy> list = zhsqTsjyService.selectZhsqTsjyList(zhsqTsjy);
        return getDataTable(list);
    }

    /**
     * 导出投诉建议列表
     */
    @RequiresPermissions("smartcommunity:TSJY:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhsqTsjy zhsqTsjy)
    {
        List<ZhsqTsjy> list = zhsqTsjyService.selectZhsqTsjyList(zhsqTsjy);
        ExcelUtil<ZhsqTsjy> util = new ExcelUtil<ZhsqTsjy>(ZhsqTsjy.class);
        return util.exportExcel(list, "TSJY");
    }

    /**
     * 新增投诉建议
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        ZhsqTsjy TSJY = new ZhsqTsjy();
        mmap.put("TSJY", TSJY);
        return prefix + "/edit";
    }

    /**
     * 新增保存投诉建议
     */
    @RequiresPermissions("smartcommunity:TSJY:add")
    @Log(title = "投诉建议", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZhsqTsjy zhsqTsjy)
    {
        return toAjax(zhsqTsjyService.insertZhsqTsjy(zhsqTsjy));
    }

    /**
     * 修改投诉建议
     */
    @GetMapping("/edit/{tsjyid}")
    public String edit(@PathVariable("tsjyid") String tsjyid, ModelMap mmap)
    {
        ZhsqTsjy TSJY = zhsqTsjyService.selectZhsqTsjyById(tsjyid);
        mmap.put("TSJY", TSJY);
        return prefix + "/edit";
    }

    /**
     * 修改保存投诉建议
     */
    @RequiresPermissions("smartcommunity:TSJY:edit")
    @Log(title = "投诉建议", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZhsqTsjy zhsqTsjy)
    {
        return toAjax(zhsqTsjyService.updateZhsqTsjy(zhsqTsjy));
    }

    /**
     * 删除投诉建议
     */
    @RequiresPermissions("smartcommunity:TSJY:remove")
    @Log(title = "投诉建议", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zhsqTsjyService.deleteZhsqTsjyByIds(ids));
    }

}
