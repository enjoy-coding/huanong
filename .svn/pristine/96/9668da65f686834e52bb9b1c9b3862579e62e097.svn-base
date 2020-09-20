package com.feather.aupipes.controller;

import java.util.List;

import com.feather.aupipes.domain.NTAccountCategory;
import com.feather.aupipes.service.NTAccountCategoryService;
import com.feather.common.core.domain.Ztree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/aupipes/ntcategory")
public class NTAccountCategoryController {
    @Autowired
    NTAccountCategoryService categoryService;
    /**
     * 加载用能性质列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = categoryService.selectCategoryTree(new NTAccountCategory());
        return ztrees;
    }
}