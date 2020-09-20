package com.feather.website.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.website.domain.CmsArticle;
import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.IArticleService;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.StringUtils;

/**
 * @author nothing
 * @date 2019-10-31 9:35
 */
@Service
public class ArticleServiceImpl extends BaseService<CmsArticle> implements IArticleService {

    @Autowired
    IArticleColumnService articleColumnService;

    @Override
    public CmsArticle findById(Long id) {
        CmsArticle article = this.fetch(Cnd.where("delFlag", "=", 0).and("id", "=", id));
        return article;
    }

    @Override
    public List<CmsArticle> findByArticleColumnAlias(String articleColumnAlias, Integer pageSize) {
        return findByArticleColumnAlias(articleColumnAlias, 1, pageSize);
    }

    public List<CmsArticle> findByArticleColumnId(Long articleColumnId, Integer pageSize) {
        return findByArticleColumnId(articleColumnId, 1, pageSize);
    }

    @Override
    public List<CmsArticle> findByArticleColumnAlias(String articleColumnAlias, Integer pageNum, Integer pageSize) {
        List<CmsArticle> articles = new ArrayList<>();
        CmsArticleColumn articleColumn = articleColumnService.findByAlias(articleColumnAlias);
        if (Lang.isNotEmpty(articleColumn)) {
            articles = this.query(Cnd.where("delFlag", "=", "0").and("articleColumnId", "=", articleColumn.getId()),
                    pageNum, pageSize);
        }
        return articles;
    }

    @Override
    public List<CmsArticle> findByArticleColumnId(Long articleColumnId, Integer pageNum, Integer pageSize) {
        List<CmsArticle> articles = new ArrayList<>();
        articles = this.query(Cnd.where("delFlag", "=", "0").and("articleColumnId", "=", articleColumnId), pageNum,
                pageSize);
        return articles;
    }

    @Override
    public PageredData<CmsArticle> list(CmsArticle article, PageDomain pageDomain) {
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Cnd cnd = Cnd.where("delFlag", "=", 0);

        if (Strings.isNotBlank(article.getTitle())) {
            cnd.and(Cnd.exps("title", "like", article.getTitle() + "%"));
        }
        if (Lang.isNotEmpty(article.getArticleColumnId())) {
            List<Long> articleColumnIds = articleColumnService.findChildrenIds(article.getArticleColumnId());
            if (Lang.isNotEmpty(articleColumnIds) && articleColumnIds.size() > 0) {
                cnd.and("articleColumnId", "in", articleColumnIds);
            }
        }
        cnd.orderBy("createTime", "desc");
        PageredData<CmsArticle> pageredData = this.searchByPage(pageNum, pageSize, cnd);
        return pageredData;
    }

    @Override
    public List<CmsArticle> list(Integer pageNum, Integer pageSize, Long articleColumnId) {
        return this.query(Cnd.where("delFlag", "=", 0).and("articleColumnId", "=", articleColumnId), pageNum, pageSize);
    }

    @Override
    public AjaxResult insertAddDo(CmsArticle article) {
        try {
            String publishAtStr = article.getPublishAtStr();
            if (Strings.isNotBlank(publishAtStr)) {
                article.setPublishAt(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, publishAtStr));
            }
            this.save(article);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Transactional
    @Override
    public AjaxResult updateEditDo(CmsArticle article) {
        try {
            updateIgnoreNull(article);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public void updateWithArticleColumn(CmsArticleColumn articleColumn) {
        this.update(Chain.make("articleColumnName", articleColumn.getName()),
                Cnd.where("articleColumnId", "=", articleColumn.getId()));
    }

    @Override
    public AjaxResult deleteByIds(String idStr) {
        try {
            Long[] ids = StringUtils.StringToLong(idStr);
            deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult deleteByIds(Long[] ids) {
        try {
            this.clear(Cnd.where("id", "in", ids));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult deleteByIdsWithLogic(String idStr) {
        Long[] ids = StringUtils.StringToLong(idStr);
        return deleteByIdsWithLogic(ids);
    }

    @Override
    public AjaxResult deleteByIdsWithLogic(Long[] ids) {
        try {
            this.update(Chain.make("delFlag", 2), Cnd.where("id", "in", ids));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }
}
