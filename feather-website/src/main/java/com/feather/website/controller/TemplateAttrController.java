package com.feather.website.controller;

import org.nutz.lang.Lang;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.website.domain.CmsTemplate;
import com.feather.website.domain.CmsTemplateAttr;
import com.feather.website.service.ITemplateAttrService;
import com.feather.website.service.ITemplateService;
import com.feather.common.annotation.Log;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common4nutz.controller.NutzBaseController;

/**
 * @author nothing
 * @date 2019-11-13 15:27
 */
@Controller
@RequestMapping("cms/templateAttr")
public class TemplateAttrController extends NutzBaseController {
    private String prefix = "cms/templateAttr";

    @Autowired
    ITemplateService templateService;
    @Autowired
    ITemplateAttrService templateAttrService;

    @GetMapping("/{tplId}")
    public String list(@PathVariable("tplId") Long tplId, ModelMap mmap) {
        CmsTemplateAttr templateAttr = CmsTemplateAttr.builder().tplId(tplId).build();
        mmap.put("templateAttr", templateAttr);
        return prefix + "/list.html";
    }

    @GetMapping("/dialogList/{tplId}")
    public String dialogList(@PathVariable("tplId") Long tplId, ModelMap mmap) {
        CmsTemplate template = templateService.findById(tplId);
        if (Lang.isEmpty(template)) {
            template = new CmsTemplate();
        }
        mmap.put("template", template);
        return prefix + "/dialogList.html";
    }

    @GetMapping("/add/{tplId}")
    public String add(@PathVariable("tplId") Long tplId, ModelMap mmap) {
        String tplName = "";
        CmsTemplate template = templateService.findById(tplId);
        if (Lang.isNotEmpty(template)) {
            tplName = template.getName();
        }
        int orderNum = templateAttrService.getMaxOrderNum();
        CmsTemplateAttr templateAttr = CmsTemplateAttr.builder().tplId(tplId).tplName(tplName).frontSize(15)
                .textLength(18).orderNum(orderNum).build();
        mmap.put("templateAttr", templateAttr);
        return prefix + "/add.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CmsTemplateAttr templateAttr = templateAttrService.findById(id);
        if (Lang.isNotEmpty(templateAttr)) {
            Long tplId = templateAttr.getTplId();
            if (Lang.isNotEmpty(tplId)) {
                CmsTemplate template = templateService.findById(tplId);
                templateAttr.setTplName(template.getName());
            }

        } else {
            templateAttr = new CmsTemplateAttr();
        }
        mmap.put("templateAttr", templateAttr);
        return prefix + "/edit.html";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CmsTemplateAttr templateAttr) {
        PageDomain pageDomain = getPageDomain();
        PageredData<CmsTemplateAttr> pageredData = templateAttrService.list(templateAttr, pageDomain);
        return getDataTable(pageredData);
    }

    @Log(title = "增加模板属性", businessType = BusinessType.INSERT)
    @PostMapping("/addDo")
    @ResponseBody
    public AjaxResult addDo(CmsTemplateAttr templateAttr) {
        return templateAttrService.insertAddDo(templateAttr);
    }

    @Log(title = "编辑模板属性", businessType = BusinessType.UPDATE)
    @PostMapping("/editDo")
    @ResponseBody
    public AjaxResult editDo(CmsTemplateAttr templateAttr) {
        return templateAttrService.updateEditDo(templateAttr);
    }

    @Log(title = "删除模板属性", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return templateAttrService.deleteByIds(ids);
    }
}
