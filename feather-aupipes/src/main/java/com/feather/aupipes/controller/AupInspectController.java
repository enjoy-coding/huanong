package com.feather.aupipes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.domain.AupInspect;
import com.feather.aupipes.service.IAupInspectService;
import com.feather.aupipes.utils.InspectExcelUtil;
import com.feather.aupipes.utils.vo.AupInspectVo;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.framework.util.ShiroUtils;
import com.feather.system.domain.SysUser;

/**
 * 巡检任务Controller
 * 
 * @author fancy
 * @date 2020-06-03
 */
@Controller
@RequestMapping("/aupipes/inspect")
public class AupInspectController extends BaseController {
    private String prefix = "aupipes/inspect";

    private String prefix1 = "aupipes/mobile/html";

    @Autowired
    private IAupInspectService aupInspectService;

    // @RequiresPermissions("aupipes:inspect:view")
    @GetMapping()
    public String inspect() {
        return prefix + "/task";
    }

    @RequestMapping("/selectAupInspectCountByStatus")
    @ResponseBody
    public AjaxResult selectAupInspectCountByStatus(AupInspect aupInspect) {

        return aupInspectService.selectAupInspectCount(aupInspect);
    }

    /**
     * 查询巡检任务列表
     */
    // @RequiresPermissions("aupipes:inspect:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupInspect aupInspect) {
        startPage();
        List<AupInspect> list = aupInspectService.selectAupInspectList(aupInspect);
        return getDataTable(list);
    }

    /**
     * 导出巡检任务列表
     */
    // @RequiresPermissions("aupipes:inspect:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupInspect aupInspect) {
        List<AupInspect> list = aupInspectService.selectAupInspectList(aupInspect);
        ExcelUtil<AupInspect> util = new ExcelUtil<AupInspect>(AupInspect.class);
        return util.exportExcel(list, "inspect");
    }

    @PostMapping("/customExport")
    @ResponseBody
    public AjaxResult customExport(String ids) {
        List<AupInspectVo> aupInspectVoList = aupInspectService.selectAupInspectVoListByIds(ids);
        InspectExcelUtil util = new InspectExcelUtil();
        return util.exportInspectExcel(aupInspectVoList, "巡检任务表");
    }

    /**
     * 新增巡检任务
     */
    @RequiresRoles("distribute_man")
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        AupInspect inspect = new AupInspect();
        mmap.put("inspect", inspect);
        mmap.put("loginId", ShiroUtils.getSysUser().getUserId());
        mmap.put("loginName", ShiroUtils.getSysUser().getUserName());
        return prefix + "/edit";
    }

    /**
     * 新增保存巡检任务
     */
    // @RequiresPermissions("aupipes:inspect:add")
    @RequiresRoles("distribute_man")
    @Log(title = "巡检任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupInspect aupInspect) {
        return toAjax(aupInspectService.insertAupInspect(aupInspect), aupInspect);
    }

    /**
     * 修改巡检任务
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        AupInspect inspect = aupInspectService.selectAupInspectById(id);
        mmap.put("inspect", inspect);
        return prefix + "/edit";
    }

    /**
     * 修改保存巡检任务
     */
    // @RequiresPermissions("aupipes:inspect:edit")
    @Log(title = "巡检任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupInspect aupInspect) {
        return toAjax(aupInspectService.updateAupInspect(aupInspect));
    }

    /**
     * 删除巡检任务
     */
    // @RequiresPermissions("aupipes:inspect:remove")
    @RequiresRoles("distribute_man")
    @Log(title = "巡检任务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(aupInspectService.deleteAupInspectByIds(ids));
    }

    /**
     * 手机端下发任务
     */
    @RequiresRoles("distribute_man")
    @GetMapping("/appAddTask")
    public String appAddTask(ModelMap mmap) {
        mmap.put("null", "");
        return prefix1 + "/appAddTask";
    }

    /**
     * 获取登录发布人员信息
     */
    @GetMapping("/getFbTaskWorker")
    @ResponseBody
    public AjaxResult getFbTaskWorker() {
        SysUser sysUser = ShiroUtils.getSysUser();
        ArrayList<Object> list = new ArrayList<>();
        list.add(sysUser.getUserName());
        list.add(sysUser.getUserId());
        return AjaxResult.success(list);
    }

}
