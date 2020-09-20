package com.feather.smart.controller;

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
import com.feather.smart.domain.ZhsqGg;
import com.feather.smart.service.IZhsqGgService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.common.core.page.TableDataInfo;

/**
 * 公告Controller
 * 
 * @author fancy
 * @date 2020-05-17
 */
@Controller
@RequestMapping("/smartcommunity/GG")
public class ZhsqGgController extends BaseController
{
    private String prefix = "smartcommunity/GG";

    @Autowired
    private IZhsqGgService zhsqGgService;

    @RequiresPermissions("smartcommunity:GG:view")
    @GetMapping()
    public String GG()
    {
        return prefix + "/GG";
    }

    /**
     * 查询公告列表
     */
    @RequiresPermissions("smartcommunity:GG:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ZhsqGg zhsqGg)
    {
        startPage();
        List<ZhsqGg> list = zhsqGgService.selectZhsqGgList(zhsqGg);
        return getDataTable(list);
    }

    /**
     * 导出公告列表
     */
    @RequiresPermissions("smartcommunity:GG:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ZhsqGg zhsqGg)
    {
        List<ZhsqGg> list = zhsqGgService.selectZhsqGgList(zhsqGg);
        ExcelUtil<ZhsqGg> util = new ExcelUtil<ZhsqGg>(ZhsqGg.class);
        return util.exportExcel(list, "GG");
    }

    /**
     * 新增公告
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        ZhsqGg GG = new ZhsqGg();
        mmap.put("GG", GG);
        return prefix + "/edit";
    }

    /**
     * 新增保存公告
     */
    @RequiresPermissions("smartcommunity:GG:add")
    @Log(title = "公告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ZhsqGg zhsqGg)
    {
        return toAjax(zhsqGgService.insertZhsqGg(zhsqGg));
    }

    /**
     * 修改公告
     */
    @GetMapping("/edit/{ggid}")
    public String edit(@PathVariable("ggid") String ggid, ModelMap mmap)
    {
        ZhsqGg GG = zhsqGgService.selectZhsqGgById(ggid);
        mmap.put("GG", GG);
        return prefix + "/edit";
    }

    /**
     * 修改保存公告
     */
    @RequiresPermissions("smartcommunity:GG:edit")
    @Log(title = "公告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ZhsqGg zhsqGg)
    {
        return toAjax(zhsqGgService.updateZhsqGg(zhsqGg));
    }

    /**
     * 删除公告
     */
    @RequiresPermissions("smartcommunity:GG:remove")
    @Log(title = "公告", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(zhsqGgService.deleteZhsqGgByIds(ids));
    }
}
