package com.feather.smart.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.smart.domain.ZhsqJm;
import com.feather.smart.service.IZhsqJmService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 居民Controller
 * 
 * @author fancy
 * @date 2020-05-14
 */
@Controller
@RequestMapping("/smartcommunity/JM")
public class ZhsqJmController extends BaseController
{
    private String prefix = "smartcommunity/JM";

    @Autowired
    private IZhsqJmService zhsqJmService;

    @RequiresPermissions("smartcommunity:JM:view")
    @GetMapping()
    public String JM()
    {
        return prefix + "/JM";
    }

    /**
     * 查询居民列表
     */
    @RequiresPermissions("smartcommunity:JM:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ZhsqJm zhsqJm)
    {
        startPage();
        List<ZhsqJm> list = zhsqJmService.selectZhsqJmList(zhsqJm);
        return getDataTable(list);
    }

    /**
     * 导出居民列表
     */
    @RequiresPermissions("smartcommunity:JM:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhsqJm zhsqJm)
    {
        List<ZhsqJm> list = zhsqJmService.selectZhsqJmList(zhsqJm);
        ExcelUtil<ZhsqJm> util = new ExcelUtil<ZhsqJm>(ZhsqJm.class);
        return util.exportExcel(list, "JM");
    }

    /**
     * 新增居民
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        ZhsqJm JM = new ZhsqJm();
        mmap.put("JM", JM);
        return prefix + "/edit";
    }

    /**
     * 新增保存居民
     */
    @RequiresPermissions("smartcommunity:JM:add")
    @Log(title = "居民", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZhsqJm zhsqJm)
    {
        return toAjax(zhsqJmService.insertZhsqJm(zhsqJm));
    }

    /**
     * 修改居民
     */
    @GetMapping("/edit/{jmid}")
    public String edit(@PathVariable("jmid") String jmid, ModelMap mmap)
    {
        ZhsqJm JM = zhsqJmService.selectZhsqJmById(jmid);
        mmap.put("JM", JM);
        return prefix + "/edit";
    }

    /**
     * 修改保存居民
     */
    @RequiresPermissions("smartcommunity:JM:edit")
    @Log(title = "居民", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZhsqJm zhsqJm)
    {
        return toAjax(zhsqJmService.updateZhsqJm(zhsqJm));
    }

    /**
     * 删除居民
     */
    @RequiresPermissions("smartcommunity:JM:remove")
    @Log(title = "居民", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zhsqJmService.deleteZhsqJmByIds(ids));
    }


}
