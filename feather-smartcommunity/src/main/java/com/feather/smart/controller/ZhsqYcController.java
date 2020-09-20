package com.feather.smart.controller;

import java.util.List;
import java.util.Map;

import com.feather.smart.domain.ZhsqYc;
import com.feather.smart.service.IZhsqYcService;
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

import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.common.core.page.TableDataInfo;

/**
 * 异常信息Controller
 * 
 * @author fancy
 * @date 2020-05-14
 */
@Controller
@RequestMapping("/smartcommunity/YC")
public class ZhsqYcController extends BaseController
{
    private String prefix = "smartcommunity/YC";

    @Autowired
    private IZhsqYcService zhsqYcService;

    @RequiresPermissions("smartcommunity:YC:view")
    @GetMapping()
    public String YC()
    {
        return prefix + "/YC";
    }

    /**
     * 查询异常信息列表
     */
    @RequiresPermissions("smartcommunity:YC:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ZhsqYc zhsqYc)
    {
        startPage();
        List<ZhsqYc> list = zhsqYcService.selectZhsqYcList(zhsqYc);
        return getDataTable(list);
    }

    /**
     * 导出异常信息列表
     */
    @RequiresPermissions("smartcommunity:YC:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhsqYc zhsqYc)
    {
        List<ZhsqYc> list = zhsqYcService.selectZhsqYcList(zhsqYc);
        ExcelUtil<ZhsqYc> util = new ExcelUtil<ZhsqYc>(ZhsqYc.class);
        return util.exportExcel(list, "YC");
    }

    /**
     * 新增异常信息
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        ZhsqYc YC = new ZhsqYc();
        mmap.put("YC", YC);
        return prefix + "/edit";
    }

    /**
     * 新增保存异常信息
     */
    @RequiresPermissions("smartcommunity:YC:add")
    @Log(title = "异常信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZhsqYc zhsqYc)
    {
        return toAjax(zhsqYcService.insertZhsqYc(zhsqYc));
    }

    /**
     * 修改异常信息
     */
    @GetMapping("/edit/{ycid}")
    public String edit(@PathVariable("ycid") String ycid, ModelMap mmap)
    {
        ZhsqYc YC = zhsqYcService.selectZhsqYcById(ycid);
        mmap.put("YC", YC);
        return prefix + "/edit";
    }

    /**
     * 修改保存异常信息
     */
    @RequiresPermissions("smartcommunity:YC:edit")
    @Log(title = "异常信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZhsqYc zhsqYc)
    {
        return toAjax(zhsqYcService.updateZhsqYc(zhsqYc));
    }

    /**
     * 删除异常信息
     */
    @RequiresPermissions("smartcommunity:YC:remove")
    @Log(title = "异常信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zhsqYcService.deleteZhsqYcByIds(ids));
    }



}
