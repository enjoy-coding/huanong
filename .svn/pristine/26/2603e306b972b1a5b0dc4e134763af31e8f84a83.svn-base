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

import com.feather.website.domain.CmsSite;
import com.feather.website.domain.CmsTemplateInst;
import com.feather.website.service.ISiteService;
import com.feather.website.service.ITemplateInstService;
import com.feather.common.annotation.Log;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common4nutz.controller.NutzBaseController;

/**
 * @author nothing
 * @date 2019-10-29 16:12
 */
@Controller
@RequestMapping("cms/site")
public class SiteController extends NutzBaseController {
    private String prefix = "cms/site";

    @Autowired
    ISiteService siteService;
    @Autowired
    ITemplateInstService templateInstService;

    @GetMapping
    public String list() {
        return prefix + "/list.html";
    }

    @GetMapping("/dialogList/{siteId}")
    public String dialogList(@PathVariable("siteId") Long siteId, ModelMap mmap) {
        CmsSite site = siteService.findById(siteId);
        if (Lang.isEmpty(site)) {
            site = new CmsSite();
        }
        mmap.put("site", site);
        return prefix + "/dialogList.html";
    }

    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Integer orderNum = siteService.getMaxOrderNum();
        CmsSite site = CmsSite.builder().state(0).folder("sjw/hb").indexTpl("index").listTpl("/list2")
                .contentTpl("/content").orderNum(orderNum).build();
        mmap.put("site", site);
        return prefix + "/add.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CmsSite site = siteService.findById(id);
        CmsTemplateInst templateInst = templateInstService.findById(site.getTplInstId());
        if (Lang.isNotEmpty(templateInst)) {
            site.setTplInstName(templateInst.getName());
        }
        mmap.put("site", site);
        return prefix + "/edit.html";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        CmsSite site = siteService.findById(id);
        mmap.put("site", site);
        return prefix + "/detail.html";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CmsSite site) {
        PageDomain pageDomain = getPageDomain();
        PageredData<CmsSite> pageredData = siteService.list(site, pageDomain);
        return getDataTable(pageredData);
    }

    @Log(title = "增加站点", businessType = BusinessType.INSERT)
    @PostMapping("/addDo")
    @ResponseBody
    public AjaxResult addDo(CmsSite site) {
        return siteService.insertAddDo(site);
    }

    @Log(title = "编辑站点", businessType = BusinessType.UPDATE)
    @PostMapping("/editDo")
    @ResponseBody
    public AjaxResult editDo(CmsSite site) {
        return siteService.updateEditDo(site);
    }

    @Log(title = "删除站点信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return siteService.deleteByIds(ids);
    }
}
