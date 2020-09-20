package com.feather.aupipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.service.IAupDailyInspectService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;

/**
 * @author sky
 * @date 2019/12/14 14:33
 */
@Controller
@RequestMapping("/aupipes/dailyInspect")
public class AupDailyInspectController extends BaseController {

    @Autowired
    private IAupDailyInspectService aupDailyInspectService;

    // 巡检概况
    @GetMapping("/situation")
    @ResponseBody
    public AjaxResult situation() {
        return aupDailyInspectService.situation();
    }

    @GetMapping("/personnelList")
    @ResponseBody
    public AjaxResult personnelList() {
        return aupDailyInspectService.personnelList();
    }

    @GetMapping("/siteList")
    @ResponseBody
    public AjaxResult siteList() {
        return aupDailyInspectService.siteList();
    }

    @GetMapping("/exceptionDetails")
    @ResponseBody
    public AjaxResult exceptionDetails() {
        return aupDailyInspectService.exceptionDetails();
    }

    @GetMapping("/inspectRecord")
    @ResponseBody
    public AjaxResult inspectRecord() {
        return aupDailyInspectService.inspectRecord();
    }

    @GetMapping("/deviceProportion")
    @ResponseBody
    public AjaxResult deviceProportion() {
        return aupDailyInspectService.deviceProportion();
    }

    @GetMapping("/areaProportion")
    @ResponseBody
    public AjaxResult areaProportion() {
        return aupDailyInspectService.areaProportion();
    }

    @GetMapping("/exceptionNum")
    @ResponseBody
    public AjaxResult exceptionNum() {
        return aupDailyInspectService.exceptionNum();
    }

    @GetMapping("/personnelStatistics")
    @ResponseBody
    public AjaxResult personnelStatistics() {
        return aupDailyInspectService.personnelStatistics();
    }

}