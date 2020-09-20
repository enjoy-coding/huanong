package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupBuildingtype;
import com.feather.aupipes.service.IAupBuildingtypeService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 楼栋类型Controller
 * 
 * @author fancy
 * @date 2020-07-03
 */
@Controller
@RequestMapping("/aupipes/buildingType")
public class AupBuildingtypeController extends BaseController
{
    private String prefix = "aupipes/buildingType";

    @Autowired
    private IAupBuildingtypeService aupBuildingtypeService;

    @RequiresPermissions("aupipes:buildingType:view")
    @GetMapping()
    public String buildingType()
    {
        return prefix + "/buildingtype";
    }

    /**
     * 查询楼栋类型列表
     */
    @RequiresPermissions("aupipes:buildingType:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupBuildingtype aupBuildingtype)
    {
        startPage();
        List<AupBuildingtype> list = aupBuildingtypeService.selectAupBuildingtypeList(aupBuildingtype);
        return getDataTable(list);
    }

    /**
     * 导出楼栋类型列表
     */
    @RequiresPermissions("aupipes:buildingType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupBuildingtype aupBuildingtype)
    {
        List<AupBuildingtype> list = aupBuildingtypeService.selectAupBuildingtypeList(aupBuildingtype);
        ExcelUtil<AupBuildingtype> util = new ExcelUtil<>(AupBuildingtype.class);
        return util.exportExcel(list, "buildingType");
    }

    /**
     * 新增楼栋类型
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        AupBuildingtype buildingType = new AupBuildingtype();
        mmap.put("buildingType", buildingType);
        return prefix + "/add";
    }

    /**
     * 新增保存楼栋类型
     */
    @RequiresPermissions("aupipes:buildingType:add")
    @Log(title = "楼栋类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupBuildingtype aupBuildingtype)
    {
        return toAjax(aupBuildingtypeService.insertAupBuildingtype(aupBuildingtype), aupBuildingtype);
    }

    /**
     * 修改楼栋类型
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AupBuildingtype buildingType = aupBuildingtypeService.selectAupBuildingtypeById(id);
        mmap.put("buildingType", buildingType);
        return prefix + "/edit";
    }

    /**
     * 修改保存楼栋类型
     */
    @RequiresPermissions("aupipes:buildingType:edit")
    @Log(title = "楼栋类型", businessType = BusinessType.UPDATE)
    @RequestMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupBuildingtype aupBuildingtype)
    {
        return toAjax(aupBuildingtypeService.updateAupBuildingtype(aupBuildingtype));
    }

    /**
     * 删除楼栋类型
     */
    @RequiresPermissions("aupipes:buildingType:remove")
    @Log(title = "楼栋类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aupBuildingtypeService.deleteAupBuildingtypeByIds(ids));
    }
}
