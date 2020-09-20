package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupPower;
import com.feather.aupipes.service.IAupPowerService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.LayuiTreeResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配电房Controller
 *
 * @author fancy
 * @date 2020-01-07
 */
@Controller
@RequestMapping("/aupipes/power")
public class AupPowerController extends BaseController
{
    private String prefix = "aupipes/power";
    private String fragment="power::powerFragment";

    @Autowired
    private IAupPowerService aupPowerService;

    @RequiresPermissions("aupipes:power:view")
    @GetMapping()
    public String power(ModelMap mmap)
    {
        AupPower power = aupPowerService.selectAupPowerById(1L);
        power.setParentName("根节点");
        mmap.put("power", power);
        return prefix + "/power";
    }

    @RequiresPermissions("aupipes:power:list")
    @GetMapping("/ztree/list")
    @ClearPage
    @ResponseBody
    public List<Ztree> listZtree(@Param("pid") Long pid){
        if(pid ==null ){
            pid = 0L;
        }
        return aupPowerService.selectAupPowerByPid(pid);
    }

    /**
     * 局部刷新内容回显
     */
    @RequiresPermissions("aupipes:power:edit")
    @GetMapping("/edit/fragment")
    public String showEditAupWaterSideFragment(@Param("id") Long id,ModelMap mmap)
    {
        AupPower aupPower = aupPowerService.selectAupPowerById(id==null?1L:id);
        if(aupPower.getPid()>0){
            AupPower aupPowerParent = aupPowerService.selectAupPowerById(aupPower.getPid());
            aupPower.setParentName(aupPowerParent.getName());
        }else{
            aupPower.setParentName("根节点");
        }
        //设置多表号
        mmap.put("power", aupPower);
        return prefix + "/" + fragment;
    }
    /**
     * 局部刷新内容回显
     */
    @RequiresPermissions("aupipes:power:edit")
    @GetMapping("/add/fragment")
    public String showAddAupWaterSideFragment(@Param("id") Long pid,ModelMap mmap)
    {
        AupPower parentAupPower = aupPowerService.selectAupPowerById(pid);
        AupPower aupPower =new AupPower();
        aupPower.setParentName(parentAupPower.getName());
        aupPower.setPid(pid);
        mmap.put("power", aupPower);
        return prefix + "/" + fragment;
    }

    /**
     * 查询配电房列表
     */
    @RequiresPermissions("aupipes:power:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public List<AupPower>  list(AupPower aupPower)
    {
       return aupPowerService.selectAupPowerList(aupPower);
    }

    /**
     * 导出配电房列表
     */
    @RequiresPermissions("aupipes:power:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupPower aupPower)
    {
        List<AupPower> list = aupPowerService.selectAupPowerList(aupPower);
        ExcelUtil<AupPower> util = new ExcelUtil<>(AupPower.class);
        return util.exportExcel(list, "power");
    }

    /**
     * 新增配电房
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        AupPower aupPower = new AupPower();
        mmap.put("power", aupPower);
        return prefix + "/edit";
    }

    /**
     * 新增保存配电房
     */
    @RequiresPermissions("aupipes:power:add")
    @Log(title = "配电房", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupPower aupPower)
    {

        if (aupPowerService.checkAupPowerBsmNoUnque(aupPower)) {
            return AjaxResult.error("新增'" + aupPower.getName() + "'失败，标识编码已存在");
        }

        return toAjax(aupPowerService.insertAupPower(aupPower),aupPower);
    }



    /**
     * 修改配电房
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AupPower aupPower = aupPowerService.selectAupPowerById(id);
        mmap.put("aupPower", aupPower);
        return prefix + "/edit";
    }

    /**
     * 修改保存配电房
     */
    @RequiresPermissions("aupipes:power:edit")
    @Log(title = "配电房", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupPower aupPower)
    {
        if (aupPowerService.checkAupPowerBsmNoUnque(aupPower)) {
            return AjaxResult.error("新增'" + aupPower.getName() + "'失败，标识编码已存在");
        }
        return toAjax(aupPowerService.updateAupPower(aupPower),aupPower);
    }

    @RequiresPermissions("aupipes:power:remove")
    @Log(title = "删除配电房", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(AupPower aupPower) {
        if(aupPowerService.selectAupPowerCountByPid(aupPower.getId())){
            return AjaxResult.error("删除失败，当前节点存在子节点");
        }
        return toAjax(aupPowerService.deleteAupPower(aupPower));
    }


    /**
     * 获取根节点配电房
     *
     */
    @RequiresPermissions("aupipes:power:list")
    @GetMapping("/root/list")
    @ResponseBody
    public AjaxResult rootlist(AupPower aupPower) {
        List<LayuiTreeResult> powerList = aupPowerService.selectAupPowerListForLayuiTree(aupPower);
        return AjaxResult.success(powerList);
    }

    /**
     * 根据节点id获取子节点列表
     */
    @GetMapping("/root/list/selectPowerById/{id}")
    @ResponseBody
    public AjaxResult listByMenuId(@PathVariable("id") Long id) {
        AupPower power = new AupPower();
        power.setPid(id);
        List<LayuiTreeResult> aupPowerChildrenByIdList = aupPowerService.selectAupPowerChildrenByIdList(power);

        return AjaxResult.success(aupPowerChildrenByIdList);
    }
}