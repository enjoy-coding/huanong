package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupLeak;
import com.feather.aupipes.service.IAupLeakService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 探漏列Controller
 *
 * @author fancy
 * @date 2020-01-04
 */
@Controller
@RequestMapping("/aupipes/leak")
public class AupLeakController extends BaseController {
    private String prefix = "aupipes/leak";

    @Autowired
    private IAupLeakService aupLeakService;

    @RequiresPermissions("aupipes:leak:view")
    @GetMapping()
    public String leak() {
        return prefix + "/leak";
    }

    /**
     * 查询探漏列列表
     */
    @RequiresPermissions("aupipes:leak:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupLeak aupLeak) {
        startPage();
        List<AupLeak> list = aupLeakService.selectAupLeakList(aupLeak);
        return getDataTable(list);
    }

}