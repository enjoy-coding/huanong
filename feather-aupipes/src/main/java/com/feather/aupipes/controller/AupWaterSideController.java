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

import io.lettuce.core.dynamic.annotation.Param;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.enums.BusinessType;
import com.feather.aupipes.domain.AupWaterSide;
import com.feather.aupipes.service.IAupWaterSideService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.LayuiTreeResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 水分区Controller
 * 
 * @author fancy
 * @date 2020-07-08
 */
@Controller
@RequestMapping("/aupipes/waterSide")
public class AupWaterSideController extends BaseController
{
    private String prefix = "aupipes/waterSide";
    private String fragment = "waterSide::aupWaterSideFragment";

    @Autowired
    private IAupWaterSideService aupWaterSideService;

    @RequiresPermissions("aupipes:waterSide:view")
    @GetMapping()
    public String waterSide(ModelMap mmap)
    {
        AupWaterSide waterSide = aupWaterSideService.selectAupWaterSideById(1L);
        waterSide.setParentName("根节点");
        mmap.put("waterSide", waterSide);
        return prefix + "/waterSide";
    }

    /**
     * 查询水分区列表
     */
    @RequiresPermissions("aupipes:waterSide:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupWaterSide aupWaterSide)
    {
        startPage();
        List<AupWaterSide> list = aupWaterSideService.selectAupWaterSideList(aupWaterSide);
        return getDataTable(list);
    }

    /**
     * 查询用水分区异步请求
     */
    @RequiresPermissions("aupipes:waterSide:list")
    @GetMapping("/tree/list")
    @ResponseBody
    public AjaxResult treeList(AupWaterSide aupWaterSide)
    {
        List<LayuiTreeResult> list = aupWaterSideService.selectAupWaterSideByTree(aupWaterSide);
        return AjaxResult.success(list);
    }
    /**
     * 导出水分区列表
     */
    @RequiresPermissions("aupipes:waterSide:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupWaterSide aupWaterSide)
    {
        List<AupWaterSide> list = aupWaterSideService.selectAupWaterSideList(aupWaterSide);
        ExcelUtil<AupWaterSide> util = new ExcelUtil<>(AupWaterSide.class);
        return util.exportExcel(list, "waterSide");
    }
    /**
     * 局部刷新内容回显
     */
    @RequiresPermissions("aupipes:waterSide:edit")
    @GetMapping("/edit/fragment")
    public String showEditAupWaterSideFragment(@Param("id") Long id,ModelMap mmap)
    {
        AupWaterSide waterSide = aupWaterSideService.selectAupWaterSideById(id==null?1L:id);
        if(waterSide.getPid()>0){
            AupWaterSide waterSideParent = aupWaterSideService.selectAupWaterSideById(waterSide.getPid());
            waterSide.setParentName(waterSideParent.getName());
        }else{
            waterSide.setParentName("根节点");
        }
        mmap.put("waterSide", waterSide);
        return prefix + "/" + fragment;
    }

    /**
     * 局部刷新内容回显
     */
    @RequiresPermissions("aupipes:waterSide:edit")
    @GetMapping("/add/fragment")
    public String showAddAupWaterSideFragment(@Param("pid") Long pid,ModelMap mmap)
    {
        AupWaterSide parentWaterSide = aupWaterSideService.selectAupWaterSideById(pid);
        AupWaterSide waterSide = new AupWaterSide();
        waterSide.setParentName(parentWaterSide.getName());
        waterSide.setPid(pid);
        mmap.put("waterSide", waterSide);
        return prefix + "/" + fragment;
    }

    /**
     * 新增水分区
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        AupWaterSide waterSide = new AupWaterSide();
        mmap.put("waterSide", waterSide);
        return prefix + "/edit";
    }

    /**
     * 新增保存水分区
     */
    @RequiresPermissions("aupipes:waterSide:add")
    @Log(title = "水分区", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupWaterSide aupWaterSide)
    {
        if (aupWaterSideService.checkAupWaterSideTypeNoUnque(aupWaterSide)) {
            return AjaxResult.error("新增'" + aupWaterSide.getName() + "'失败，行政编码已存在");
        }
        return toAjax(aupWaterSideService.insertAupWaterSide(aupWaterSide), aupWaterSide);
    }

    /**
     * 修改水分区
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        
        AupWaterSide waterSide = aupWaterSideService.selectAupWaterSideById(id);
        mmap.put("waterSide", waterSide);
        return prefix + "/edit";
    }

    /**
     * 修改保存水分区
     */
    @RequiresPermissions("aupipes:waterSide:edit")
    @Log(title = "编辑水分区", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupWaterSide aupWaterSide)
    {
        if (aupWaterSideService.checkAupWaterSideTypeNoUnque(aupWaterSide)) {
            return AjaxResult.error("编辑'" + aupWaterSide.getName() + "'失败，行政编码已存在");
        }
        return toAjax(aupWaterSideService.updateAupWaterSide(aupWaterSide),aupWaterSide);
    }

    @RequiresPermissions("aupipes:waterSide:remove")
    @Log(title = "删除水分区", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(AupWaterSide aupWaterSide) {
        if(aupWaterSideService.checkAupWaterSideHasChildren(aupWaterSide.getId())){
            return AjaxResult.error("删除失败，当前节点存在子节点");
        }
        return toAjax(aupWaterSideService.deleteAupWaterSide(aupWaterSide));
    }

}