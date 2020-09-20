package com.feather.website.controller;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
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

import com.feather.website.domain.CmsArticle;
import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.IArticleService;
import com.feather.common.annotation.Log;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.DateUtils;
import com.feather.common4nutz.controller.NutzBaseController;
import com.feather.system.domain.SysUser;

/**
 * @author nothing
 * @date 2019-10-31 9:29
 */
@Controller
@RequestMapping("cms/article")
public class ArticleController extends NutzBaseController {
    private String prefix = "cms/article";

    @Autowired
    IArticleService articleService;

    @Autowired
    IArticleColumnService articleColumnService;

    @GetMapping
    public String list() {
        return prefix + "/list.html";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") Long articleColumnId, ModelMap mmap) {
        Date now = new Date();
        CmsArticle article = CmsArticle.builder().type(0).articleColumnId(articleColumnId).showCoverImage(0)
                .canDownload(true).publishAt(now).publishAtStr(DateUtils.getTime(now)).viewCount(0).build();
        CmsArticleColumn articleColumn = articleColumnService.findById(articleColumnId);
        if (Lang.isNotEmpty(articleColumn)) {
            article.setArticleColumnName(articleColumn.getName());
        }
        SysUser user = getUser();
        if (Lang.isNotEmpty(user)) {
            article.setPublisherId(user.getUserId());
            article.setPublisher(user.getUserName());
        }
        mmap.put("article", article);
        return prefix + "/add.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CmsArticle article = articleService.findById(id);
        if (Lang.isEmpty(article)) {
            article = new CmsArticle();
        } else {
            Date publishAt = article.getPublishAt();
            if (Lang.isNotEmpty(publishAt)) {
                article.setPublishAtStr(DateUtils.getTime(publishAt));
            }
            article.setShowCoverImage(0);
        }
        mmap.put("article", article);
        return prefix + "/edit.html";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CmsArticle article) {
        PageDomain pageDomain = getPageDomain();
        PageredData<CmsArticle> pageredData = articleService.list(article, pageDomain);
        return getDataTable(pageredData);
    }

    @Log(title = "增加文章", businessType = BusinessType.INSERT)
    @PostMapping("/addDo")
    @ResponseBody
    public AjaxResult addDo(CmsArticle article) {
        return articleService.insertAddDo(article);
    }

    @Log(title = "编辑文章", businessType = BusinessType.UPDATE)
    @PostMapping("/editDo")
    @ResponseBody
    public AjaxResult editDo(CmsArticle article) {
        return articleService.updateEditDo(article);
    }

    @Log(title = "删除文章", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return articleService.deleteByIdsWithLogic(ids);
    }

    private SysUser getUser() {
        SysUser user = null;
        Object obj = SecurityUtils.getSubject().getPrincipal();
        if (obj instanceof SysUser) {
            user = (SysUser) obj;
        }
        return user;
    }
}
