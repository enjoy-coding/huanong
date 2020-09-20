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

import com.feather.aupipes.domain.AupWorker;
import com.feather.aupipes.service.IAupWorkerService;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;

/**
 * 人员Controller
 * 
 * @author fancy
 * @date 2020-01-06
 */
@Controller
@RequestMapping("/aupipes/worker")
public class AupWorkerController extends BaseController {
    private String prefix = "aupipes/worker";

    @Autowired
    private IAupWorkerService aupWorkerService;

    @RequiresPermissions("aupipes:worker:view")
    @GetMapping()
    public String worker() {
        return prefix + "/worker";
    }

    /**
     * 查询人员列表
     */
    @RequiresPermissions("aupipes:worker:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AupWorker aupWorker) {
        startPage();
        List<AupWorker> list = aupWorkerService.selectAupWorkerList(aupWorker);
        return getDataTable(list);
    }

    /**
     * 导出人员列表
     */
    @RequiresPermissions("aupipes:worker:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupWorker aupWorker) {
        List<AupWorker> list = aupWorkerService.selectAupWorkerList(aupWorker);
        ExcelUtil<AupWorker> util = new ExcelUtil<AupWorker>(AupWorker.class);
        return util.exportExcel(list, "worker");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("aupWorker", new AupWorker());
        return prefix + "/edit";
    }

    /**
     * 新增保存人员
     */
    @RequiresPermissions("aupipes:worker:add")
    @Log(title = "人员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupWorker aupWorker) {
        return toAjax(aupWorkerService.insertAupWorker(aupWorker));
    }

    /**
     * 修改人员
     */
    @GetMapping("/edit/{workerId}")
    public String edit(@PathVariable("workerId") Long workerId, ModelMap mmap) {
        AupWorker aupWorker = aupWorkerService.selectAupWorkerById(workerId);
        mmap.put("aupWorker", aupWorker);
        return prefix + "/edit";
    }

    /**
     * 修改保存人员
     */
    @RequiresPermissions("aupipes:worker:edit")
    @Log(title = "人员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupWorker aupWorker) {
        return toAjax(aupWorkerService.updateAupWorker(aupWorker));
    }

}
