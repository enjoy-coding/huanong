package com.feather.website.controller;

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Lang;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.website.domain.CmsTemplate;
import com.feather.website.domain.CmsTemplateInst;
import com.feather.website.service.IBuildService;
import com.feather.website.service.ITemplateInstService;
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
@RequestMapping("cms/templateInst")
public class TemplateInstController extends NutzBaseController {
    private String prefix = "cms/templateInst";

    @Autowired
    ITemplateService templateService;
    @Autowired
    ITemplateInstService templateInstService;
    @Autowired
    IBuildService buildService;

    @GetMapping
    public String list() {
        return prefix + "/list.html";
    }

    @GetMapping("/dialogList")
    public String dialogList(@RequestParam("id") Long id, @RequestParam("siteId") Long siteId, ModelMap mmap) {
        CmsTemplateInst templateInst = new CmsTemplateInst();
        if (Lang.isNotEmpty(id)) {
            templateInst.setId(id);
        }
        if (Lang.isNotEmpty(siteId)) {
            templateInst.setSiteId(siteId);
        }
        mmap.put("templateInst", templateInst);
        return prefix + "/dialogList.html";
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        CmsTemplateInst templateInst = CmsTemplateInst.builder().build();
        mmap.put("templateInst", templateInst);
        return prefix + "/add.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CmsTemplateInst templateInst = templateInstService.findById(id);
        if (Lang.isEmpty(templateInst)) {
            templateInst = new CmsTemplateInst();
        }
        mmap.put("templateInst", templateInst);
        return prefix + "/edit.html";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        CmsTemplateInst templateInst = templateInstService.findById(id);
        mmap.put("templateInst", templateInst);
        return prefix + "/detail.html";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CmsTemplateInst templateInst) {
        PageDomain pageDomain = getPageDomain();
        PageredData<CmsTemplateInst> pageredData = templateInstService.list(templateInst, pageDomain);
        return getDataTable(pageredData);
    }

    @Log(title = "增加模板实例", businessType = BusinessType.INSERT)
    @PostMapping("/addDo")
    @ResponseBody
    public AjaxResult addDo(CmsTemplateInst templateInst) {
        return templateInstService.insertAddDo(templateInst);
    }

    @Log(title = "编辑模板实例", businessType = BusinessType.UPDATE)
    @PostMapping("/editDo")
    @ResponseBody
    public AjaxResult editDo(CmsTemplateInst templateInst) {
        return templateInstService.updateEditDo(templateInst);
    }

    @Log(title = "删除模板实例", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return templateInstService.deleteByIds(ids);
    }

    @RequestMapping("/build")
    @ResponseBody
    public String build(@RequestParam("siteId") Long siteId, @RequestParam("tplId") Long tplId,
            @RequestParam("tplInstId") Long tplInstId, @RequestParam(value = "block", required = false) String block,
            @RequestParam(value = "blockValue", required = false) String blockValue, ModelMap mmap,
            HttpServletRequest req) {
        CmsTemplate template = templateService.findById(tplId);
        mmap.put("siteId", siteId);
        mmap.put("tplId", tplId);
        mmap.put("tplInstId", tplInstId);
        String dynamicHtml = buildService.build(siteId, template, tplInstId, block, blockValue);
        return dynamicHtml;
    }
}
