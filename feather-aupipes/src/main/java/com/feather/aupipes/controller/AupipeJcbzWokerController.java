package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.aupipes.domain.AupipeJcbzWoker;
import com.feather.aupipes.service.IAupDecisionSecurityService;
import com.feather.aupipes.service.IAupipeJcbzWokerService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.framework.util.RedisUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 决策保障管理员Controller
 * Create by NieCheng
 * Time   2020/7/6 14:06
 */
@Controller
@RequestMapping("/aupipes/manager")
public class AupipeJcbzWokerController extends BaseController
{
    private String prefix = "aupipes/workToUser";
    //private String prefix = "aupipes/managerToUser";

    @Autowired
    private IAupipeJcbzWokerService aupipeJcbzWokerService;

    @Autowired
    private IAupDecisionSecurityService aupDecisionSecurityService;

    @RequiresPermissions("aupipes:manager:view")
    @GetMapping()
    public String manager()
    {
        //return prefix + "/manager";
        return prefix + "/jcbzWoker";
    }

    /**
     * 查询决策保障管理员列表
     */
    @RequiresPermissions("aupipes:manager:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupipeJcbzWoker aupJcbzManager)
    {
        startPage();
        List<AupipeJcbzWoker> list = aupipeJcbzWokerService.selectAupJcbzManagerList(aupJcbzManager);
        return getDataTable(list);
    }

    /**
     * 导出决策保障管理员列表
     */
    @RequiresPermissions("aupipes:manager:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupipeJcbzWoker aupJcbzManager)
    {
        List<AupipeJcbzWoker> list = aupipeJcbzWokerService.selectAupJcbzManagerList(aupJcbzManager);
        ExcelUtil<AupipeJcbzWoker> util = new ExcelUtil<AupipeJcbzWoker>(AupipeJcbzWoker.class);
        return util.exportExcel(list, "manager");
    }

    /**
     * 新增决策保障管理员
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存决策保障管理员
     */
    @RequiresPermissions("aupipes:manager:add")
    @Log(title = "决策保障管理员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupipeJcbzWoker aupJcbzManager)
    {
        return toAjax(aupipeJcbzWokerService.insertAupJcbzManager(aupJcbzManager));
    }

    /**
     * 修改决策保障管理员
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AupipeJcbzWoker aupJcbzManager = aupipeJcbzWokerService.selectAupJcbzManagerById(id);
        mmap.put("aupJcbzManager", aupJcbzManager);
        return prefix + "/edit";
    }

    /**
     * 修改保存决策保障管理员
     */
    @RequiresPermissions("aupipes:manager:edit")
    @Log(title = "决策保障管理员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupipeJcbzWoker aupJcbzManager)
    {
        return toAjax(aupipeJcbzWokerService.updateAupJcbzManager(aupJcbzManager));
    }

    /**
     * 删除决策保障管理员
     */
    @RequiresPermissions("aupipes:manager:remove")
    @Log(title = "决策保障管理员", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aupipeJcbzWokerService.deleteAupJcbzManagerByIds(ids));
    }


    /**
     * 加载区域列表树
     */
    @GetMapping("/buildingArea")
//    @Cacheable(key = "#root.methodName +':'+ #root.args[0]")
    @ResponseBody
    public List<Ztree> buildingArea(AupBuildingArea aupBuildingArea) {
        return aupipeJcbzWokerService.selectAupBuildingAreaTree(aupBuildingArea);
    }

    /**
     * 获取二级单位
     */
    @GetMapping("/selectOptions")
    @ResponseBody
    public AjaxResult getSelect(AupBuildingArea aupBuildingArea)
    {
        return aupipeJcbzWokerService.selectOptions(aupBuildingArea);
    }

    @RequestMapping("/updateData")
    @ResponseBody
    public void updateData(){
        aupipeJcbzWokerService.updateData();
    }

    /**
     * 更新模板
     */
    @RequestMapping("/updateTemplate")
    @ResponseBody
    public void updateTemplate(){
        RedisUtils.del("dept");
        String type="";
        aupDecisionSecurityService.selectLzxdUserTree(type);
    }


}
