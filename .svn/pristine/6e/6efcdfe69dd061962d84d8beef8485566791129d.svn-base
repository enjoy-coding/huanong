package com.feather.website.controller;

import com.feather.website.domain.CmsArticle;
import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.domain.CmsSite;
import com.feather.website.domain.CmsTemplate;
import com.feather.website.service.*;
//import com.feather.common.view.HtmlView;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.thymeleaf.spring5.view.ThymeleafView;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.List;

/**
 * @author nothing
 * @date 2019-11-04 11:34
 */
@Controller
@RequestMapping("front/")
public class IndexController {
    private String prefix = "front";

    private final String errorPage = "error.html";

    @Autowired
    ISiteService siteService;
    @Autowired
    IArticleColumnService articleColumnService;
    @Autowired
    ITemplateService templateService;
    @Autowired
    ITemplateInstService templateInstService;
//    @Autowired
//    IBuildService buildService;

    @RequestMapping(value = {"/{siteAlias}", "/"})
    public ModelAndView index(@PathVariable(value = "siteAlias", required = false) String siteAlias, ModelMap mmap) throws ClassNotFoundException {
        String uri = "/main/index.html";
        NutMap params = new NutMap();
        ModelAndView modelAndView = new ModelAndView();
        if(Strings.isBlank(siteAlias)){}
        if (Strings.isBlank(siteAlias)) {
            //查询站点
            siteAlias = "main";
        }
        CmsSite site = siteService.findByName(siteAlias);
        if (Lang.isEmpty(site)) {
            modelAndView.setViewName(errorPage);
            return modelAndView;
        }

        Long tplInstId = site.getTplInstId();
        /*if (Lang.isNotEmpty(tplInstId)) {
            modelAndView.setView(new ThymeleafView());
            CmsTemplate template = templateService.findByTemplateInstId(tplInstId);
            params.addv("site", site).addv("template", template)
                    .addv("useHtml", template.getIndexTpl());
//            String html = buildService.buildDynamicHtmlToFront(tplInstId, params);
            String html = "";
            modelAndView.addObject(html);
            modelAndView.setViewName(html);
            return modelAndView;
        }*/

        List<CmsArticleColumn> articleColumns = articleColumnService.findBySiteId(site.getId());
        mmap.put("site", site);
        mmap.put("articleColumns", articleColumns);
        modelAndView.setViewName(prefix + uri);
        StringTemplateResolver s = null;
        return modelAndView;
    }
}
