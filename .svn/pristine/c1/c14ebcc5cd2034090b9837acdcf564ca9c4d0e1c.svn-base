package com.feather.smart.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.smart.domain.ZhsqZc;
import com.feather.smart.service.IZhsqZcService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 社区资产Controller
 *
 * @author fancy
 * @date 2020-05-14
 */
@Controller
@RequestMapping("/smartcommunity/ZC")
public class ZhsqZcController extends BaseController
{
    private String prefix = "smartcommunity/ZC";

    @Autowired
    private IZhsqZcService zhsqZcService;

    @RequiresPermissions("smartcommunity:ZC:view")
    @GetMapping()
    public String ZC()
    {
        return prefix + "/ZC";
    }

    /**
     * 查询社区资产列表
     */
    @RequiresPermissions("smartcommunity:ZC:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ZhsqZc zhsqZc)
    {
        startPage();
        List<ZhsqZc> list = zhsqZcService.selectZhsqZcList(zhsqZc);
        return getDataTable(list);
    }

    /**
     * 导出社区资产列表
     */
    @RequiresPermissions("smartcommunity:ZC:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhsqZc zhsqZc)
    {
        List<ZhsqZc> list = zhsqZcService.selectZhsqZcList(zhsqZc);
        ExcelUtil<ZhsqZc> util = new ExcelUtil<ZhsqZc>(ZhsqZc.class);
        return util.exportExcel(list, "ZC");
    }

    /**
     * 新增社区资产
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        ZhsqZc ZC = new ZhsqZc();
        mmap.put("ZC", ZC);
        return prefix + "/edit";
    }

    /**
     * 新增保存社区资产
     */
    @RequiresPermissions("smartcommunity:ZC:add")
    @Log(title = "社区资产", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZhsqZc zhsqZc)
    {
        return toAjax(zhsqZcService.insertZhsqZc(zhsqZc),zhsqZc);
    }

    /**
     * 修改社区资产
     */
    @GetMapping("/edit/{zcid}")
    public String edit(@PathVariable("zcid") String zcid, ModelMap mmap)
    {
        ZhsqZc ZC = zhsqZcService.selectZhsqZcById(zcid);
        mmap.put("ZC", ZC);
        return prefix + "/edit";
    }

    /**
     * 修改保存社区资产
     */
    @RequiresPermissions("smartcommunity:ZC:edit")
    @Log(title = "社区资产", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZhsqZc zhsqZc)
    {
        return toAjax(zhsqZcService.updateZhsqZc(zhsqZc),zhsqZc);
    }

    /**
     * 删除社区资产
     */
    @RequiresPermissions("smartcommunity:ZC:remove")
    @Log(title = "社区资产", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zhsqZcService.deleteZhsqZcByIds(ids));
    }







}