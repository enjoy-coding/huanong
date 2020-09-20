package com.feather.website.controller;

import java.util.List;

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

import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.domain.CmsSite;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.ISiteService;
import com.feather.common.annotation.Log;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.page.PageDomain;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common4nutz.controller.NutzBaseController;

/**
 * @author nothing
 * @date 2019-10-31 9:29
 */
@Controller
@RequestMapping("cms/articlecolumn")
public class ArticleColumnController extends NutzBaseController {
    private String prefix = "cms/articlecolumn";

    @Autowired
    ISiteService siteService;
    @Autowired
    IArticleColumnService articleColumnService;

    @GetMapping
    public String list() {
        return prefix + "/list.html";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") Long parentId, ModelMap mmap) {
        CmsArticleColumn parentArticleColumn = articleColumnService.findById(parentId);
        CmsArticleColumn articleColumn = CmsArticleColumn.builder().type(0).publishAudit(0).shareAudit(0)
                .frontMaxSize(10).listTpl("/list").contentTpl("/content").build();
        if (Lang.isNotEmpty(parentArticleColumn)) {
            articleColumn.setParentId(parentArticleColumn.getId());
            articleColumn.setParentName(parentArticleColumn.getName());
            articleColumn.setSiteId(parentArticleColumn.getSiteId());
            CmsSite site = siteService.findById(parentArticleColumn.getSiteId());
            if (Lang.isNotEmpty(site)) {
                articleColumn.setSiteName(site.getName());
            }
            Integer orderNum = articleColumnService.getMaxOrderNum(parentArticleColumn.getId());
            articleColumn.setOrderNum(orderNum);
        }

        mmap.put("articleColumn", articleColumn);
        return prefix + "/add.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CmsArticleColumn articleColumn = articleColumnService.findById(id);
        if (Lang.isNotEmpty(articleColumn) && Lang.isNotEmpty(articleColumn.getSiteId())) {
            CmsSite site = siteService.findById(articleColumn.getSiteId());
            if (Lang.isNotEmpty(site)) {
                articleColumn.setSiteName(site.getName());
            }
        } else {
            articleColumn = new CmsArticleColumn();
        }
        mmap.put("articleColumn", articleColumn);
        return prefix + "/edit.html";
    }

    /**
     * 选择栏目树
     */
    @GetMapping("/selectArticleColumnTree/{id}")
    public String selectArticleColumnTree(@PathVariable("id") Long id, ModelMap mmap) {
        CmsArticleColumn articleColumn = articleColumnService.findById(id);
        mmap.put("articleColumn", articleColumn != null ? articleColumn : new CmsArticleColumn());
        return prefix + "/tree";
    }

    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = articleColumnService.selectArticleColumnTree(new CmsArticleColumn());
        return ztrees;
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CmsArticleColumn articleColumn) {
        PageDomain pageDomain = getPageDomain();
        PageredData<CmsArticleColumn> pageredData = articleColumnService.list(articleColumn, pageDomain);
        return getDataTable(pageredData);
    }

    @Log(title = "增加栏目", businessType = BusinessType.INSERT)
    @PostMapping("/addDo")
    @ResponseBody
    public AjaxResult addDo(CmsArticleColumn articleColumn) {
        return articleColumnService.insertAddDo(articleColumn);
    }

    @Log(title = "编辑栏目", businessType = BusinessType.UPDATE)
    @PostMapping("/editDo")
    @ResponseBody
    public AjaxResult editDo(CmsArticleColumn articleColumn) {
        return articleColumnService.updateEditDo(articleColumn);
    }

    @Log(title = "逻辑删除栏目", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return articleColumnService.deleteByIdsWithLogic(ids);
    }
}
