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
import com.feather.aupipes.domain.AupElectricitySide;
import com.feather.aupipes.domain.NTAccountCategory;
import com.feather.aupipes.service.IAupElectricitySideService;
import com.feather.aupipes.service.NTAccountCategoryService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.common.core.page.TableDataInfo;

/**
 * 用电平衡分区Controller
 *
 * @author fancy
 * @date 2020-07-30
 */
@Controller
@RequestMapping("/aupipes/electricity")
public class AupElectricitySideController extends BaseController
{
    private String prefix = "aupipes/electricity";
    private String fragment = "/electricity::electricityFragment";

    @Autowired
    private IAupElectricitySideService aupElectricitySideService;

    @Autowired
    private NTAccountCategoryService categorySevice;
    @RequiresPermissions("aupipes:electricity:view")
    @GetMapping()
    public String electricity(ModelMap mmap)
    {
        AupElectricitySide electricity = aupElectricitySideService.selectAupElectricitySideById(1L);
        electricity.setParentName("根节点");
        mmap.put("electricity", electricity);
        return prefix + "/electricity";
    }

    /**
     * 查询用电平衡分区列表
     */
    @RequiresPermissions("aupipes:electricity:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupElectricitySide aupElectricitySide)
    {
        startPage();
        List<AupElectricitySide> list = aupElectricitySideService.selectAupElectricitySideList(aupElectricitySide);
        return getDataTable(list);
    }

    @RequiresPermissions("aupipes:electricity:list")
    @GetMapping("/ztree/list")
    @ClearPage
    @ResponseBody
    public List<Ztree> listZtree(@Param("pid") Long pid){
        if(pid ==null ){
            pid = 0L;
        }
        return aupElectricitySideService.selectZtreeAupElectricitySide(pid);
    }

    /**
     * 选中用能性质树
     */
    @GetMapping("/selectCategoryTree/{categoryId}")
    public String selectCategoryTree(@PathVariable("categoryId") String categoryId, ModelMap mmap) {
        NTAccountCategory category = categorySevice.selectCategoryById(categoryId);
        mmap.put("category", category != null ? category : new NTAccountCategory());
        return   "/aupipes/ntCategory/categoryTree";
    }

    /**
     * 局部刷新内容回显
     */
    @RequiresPermissions("aupipes:electricity:edit")
    @GetMapping("/edit/fragment")
    public String showEditAupWaterSideFragment(@Param("id") Long id,ModelMap mmap)
    {
        AupElectricitySide electricitySide = aupElectricitySideService.selectAupElectricitySideById(id==null?1L:id);
        if(electricitySide.getPid()>0){
            AupElectricitySide electricitySideParent = aupElectricitySideService.selectAupElectricitySideById(electricitySide.getPid());
            electricitySide.setParentName(electricitySideParent.getCachename());
        }else{
            electricitySide.setParentName("根节点");
        }
        //设置多表号
        List<AupElectricitySide> aupElectricitySides = aupElectricitySideService.selectAupElectricitySideList(new AupElectricitySide(electricitySide.getCacheid()));
        if(aupElectricitySides.size()>1){
            StringBuilder tableIdSb = new StringBuilder(16);
            for (AupElectricitySide aupElectricitySide : aupElectricitySides) {
                tableIdSb.append(aupElectricitySide.getTableid()).append(",");
            }
            electricitySide.setTableid(tableIdSb.toString());
        }
        mmap.put("electricity", electricitySide);
        return prefix + fragment;
    }

     /**
     * 局部刷新内容回显
     */
    @RequiresPermissions("aupipes:electricity:edit")
    @GetMapping("/add/fragment")
    public String showAddAupWaterSideFragment(@Param("id") Long pid,ModelMap mmap)
    {
        AupElectricitySide parentElectricitySide = aupElectricitySideService.selectAupElectricitySideById(pid);
        AupElectricitySide electricitySide =new AupElectricitySide();
        electricitySide.setParentName(parentElectricitySide.getCachename());
        electricitySide.setPid(pid);
        mmap.put("electricity", electricitySide);
        return prefix + fragment;
    }

    /**
     * 导出用电平衡分区列表
     */
    @RequiresPermissions("aupipes:electricity:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupElectricitySide aupElectricitySide)
    {
        List<AupElectricitySide> list = aupElectricitySideService.selectAupElectricitySideList(aupElectricitySide);
        ExcelUtil<AupElectricitySide> util = new ExcelUtil<>(AupElectricitySide.class);
        return util.exportExcel(list, "electricity");
    }

    /**
     * 新增用电平衡分区
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        AupElectricitySide electricity = new AupElectricitySide();
        mmap.put("electricity", electricity);
        return prefix + "/edit";
    }

    /**
     * 新增保存用电平衡分区
     */
    @RequiresPermissions("aupipes:electricity:add")
    @Log(title = "用电平衡分区", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupElectricitySide aupElectricitySide)
    {
        return toAjax(aupElectricitySideService.insertAupElectricitySide(aupElectricitySide), aupElectricitySide);
    }

    /**
     * 修改用电平衡分区
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AupElectricitySide electricity = aupElectricitySideService.selectAupElectricitySideById(id);
        mmap.put("electricity", electricity);
        return prefix + "/edit";
    }

    /**
     * 修改保存用电平衡分区
     */
    @RequiresPermissions("aupipes:electricity:edit")
    @Log(title = "用电平衡分区", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupElectricitySide aupElectricitySide)
    {
        return toAjax(aupElectricitySideService.updateAupElectricitySide(aupElectricitySide),aupElectricitySide);
    }

    /**
     * 删除用电平衡分区
     */
    @RequiresPermissions("aupipes:electricity:remove")
    @Log(title = "删除用电平衡分区", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(AupElectricitySide aupElectricitySide)
    {
        if(aupElectricitySideService.checkAupElectricitySideHasChildren(aupElectricitySide.getId())){
            return AjaxResult.error("删除失败，当前节点存在子节点");
        }
        return toAjax(aupElectricitySideService.deleteAupElectricitySide(aupElectricitySide));
    }
}