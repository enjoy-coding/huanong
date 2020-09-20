package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupPlansType;
import com.feather.aupipes.service.IAupPlansTypeService;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预案类型Controller
 *
 * @author fancy
 * @date 2020-01-02
 */
@Controller
@RequestMapping("/aupipes/type")
public class AupPlansTypeController extends BaseController {
    private String prefix = "aupipes/type";

    @Autowired
    private IAupPlansTypeService aupPlansTypeService;

    @RequiresPermissions("aupipes:type:view")
    @GetMapping()
    public String type() {
        return prefix + "/type";
    }


    /**
     * 导出预案类型列表
     */
    @RequiresPermissions("aupipes:type:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupPlansType aupPlansType) {
        List<AupPlansType> list = aupPlansTypeService.selectAupPlansTypeList(aupPlansType);
        ExcelUtil<AupPlansType> util = new ExcelUtil<AupPlansType>(AupPlansType.class);
        return util.exportExcel(list, "type");
    }


    /**
     * 新增保存预案类型
     */
    @RequiresPermissions("aupipes:type:add")
    @Log(title = "预案类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupPlansType aupPlansType) {
        return toAjax(aupPlansTypeService.insertAupPlansType(aupPlansType));
    }

    /**
     * 修改预案类型
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        AupPlansType aupPlansType = aupPlansTypeService.selectAupPlansTypeById(id);
        mmap.put("aupPlansType", aupPlansType);
        return prefix + "/edit";
    }

    /**
     * 修改保存预案类型
     */
    @RequiresPermissions("aupipes:type:edit")
    @Log(title = "预案类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupPlansType aupPlansType) {
        return toAjax(aupPlansTypeService.updateAupPlansType(aupPlansType));
    }

    /**
     * 加载预警列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = aupPlansTypeService.selectWarringCategoryTree();
        return ztrees;
    }

    /**
     * 打开选择用户界面
     */
    @GetMapping("/getJcbzUser")
    public String getJcbzUser( ModelMap mmap){

        mmap.put("null", null);
        return "aupipes/plansManage/jcbzAddUser";
    }
}
