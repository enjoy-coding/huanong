package com.feather.aupipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.service.IAupYjczService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;

/**
 * 区域树结构Controller
 *
 * @author fancy
 * @date 2019-12-11
 */
@Controller
@RequestMapping("api/aupipes/yjcz")
public class AupYjczController extends BaseController {
    private String prefix = "aupipes/yjcz";

    @Autowired
    private IAupYjczService iAupYjczService;

    @RequestMapping()
    public String yjcz() {
        return prefix + "/yjcz";
    }

    @RequestMapping("/list")
    @ResponseBody
    public AjaxResult list() {
        return iAupYjczService.getPubAlarm();
    }

}