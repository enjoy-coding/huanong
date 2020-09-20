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
@RequestMapping("cms/template")
public class TemplateController extends NutzBaseController {
    private String prefix = "cms/template";

    @Autowired
    ITemplateService templateService;

    @GetMapping
    public String list() {
        return prefix + "/list.html";
    }

    @GetMapping("/dialogList/{templateId}")
    public String dialogList(@PathVariable("templateId") Long templateId, ModelMap mmap) {
        CmsTemplate template = templateService.findById(templateId);
        if (Lang.isEmpty(template)) {
            template = new CmsTemplate();
        }
        mmap.put("template", template);
        return prefix + "/dialogList.html";
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        CmsTemplate template = CmsTemplate.builder().indexTpl("index.html").listTpl("list.html")
                .contentTpl("content.html").build();
        mmap.put("template", template);
        return prefix + "/add.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CmsTemplate template = templateService.findById(id);
        mmap.put("template", template);
        return prefix + "/edit.html";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        CmsTemplate template = templateService.findById(id);
        mmap.put("template", template);
        return prefix + "/detail.html";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CmsTemplate template) {
        PageDomain pageDomain = getPageDomain();
        PageredData<CmsTemplate> pageredData = templateService.list(template, pageDomain);
        return getDataTable(pageredData);
    }

    @Log(title = "增加模板", businessType = BusinessType.INSERT)
    @PostMapping("/addDo")
    @ResponseBody
    public AjaxResult addDo(CmsTemplate template) {
        return templateService.insertAddDo(template);
    }

    @Log(title = "编辑模板", businessType = BusinessType.UPDATE)
    @PostMapping("/editDo")
    @ResponseBody
    public AjaxResult editDo(CmsTemplate template) {
        return templateService.updateEditDo(template);
    }

    @Log(title = "删除模板", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return templateService.deleteByIds(ids);
    }
}
