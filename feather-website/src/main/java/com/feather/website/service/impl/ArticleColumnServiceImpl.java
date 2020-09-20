package com.feather.website.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.page.PageDomain;
import com.feather.common.utils.StringUtils;
import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.domain.CmsSite;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.IArticleService;
import com.feather.website.service.ISiteService;

/**
 * @author nothing
 * @date 2019-10-31 9:38
 */
@Service
public class ArticleColumnServiceImpl extends BaseService<CmsArticleColumn> implements IArticleColumnService {
    @Autowired
    ISiteService siteService;
    @Autowired
    IArticleService articleService;

    @Override
    public CmsArticleColumn findById(Long id) {
        return this.fetch(Cnd.where("delFlag", "=", 0).and("id", "=", id));
    }

    @Override
    public List<CmsArticleColumn> findByIds(Long[] ids) {
        return query(Cnd.where("delFlag", "=", 0).and("id", "in", ids));
    }

    @Override
    public List<CmsArticleColumn> findByIds(String idStr) {
        String[] idArr = idStr.split(";");
        Long[] ids = new Long[idArr.length];
        if (Lang.isNotEmpty(idArr) && idArr.length > 0) {
            for (int i = 0; i < idArr.length; i++) {
                ids[i] = Long.parseLong(idArr[i]);
            }
        }
        return findByIds(ids);
    }

    @Override
    public List<CmsArticleColumn> findByParentId(Long parentId) {
        return this.query(Cnd.where("delFlag", "=", 0).and("parentId", "=", parentId));
    }

    @Override
    public CmsArticleColumn findByName(String name) {
        return this.fetch(Cnd.where("delFlag", "=", 0).and(Cnd.exps("name", "=", name).or("alias", "=", name)));
    }

    @Override
    public List<CmsArticleColumn> findByNames(List<String> names, boolean isRoot) {
        String[] strings = new String[names.size()];
        names.toArray(strings);
        Cnd cnd = Cnd.where("delFlag", "=", 0).and(Cnd.exps("name", "in", strings).or("alias", "in", strings));
        if (isRoot) {
            cnd.and("level", "=", 1);
        }
        return this.query(cnd);
    }

    @Override
    public CmsArticleColumn findByAlias(String alias) {
        return this.fetch(Cnd.where("delFlag", "=", 0).and("alias", "=", alias));
    }

    @Override
    public List<CmsArticleColumn> findBySiteId(Long siteId) {
        return this.query(Cnd.where("delFlag", "=", 0).and("siteId", "=", siteId));
    }

    @Override
    public List<CmsArticleColumn> selectArticleColumList(CmsArticleColumn articleColumn) {
        Cnd cnd = Cnd.where("delFlag", "=", 0);
        if (Lang.isNotEmpty(articleColumn) && Lang.isNotEmpty(articleColumn.getParentId())) {
            cnd.and("paretnId", "=", articleColumn.getParentId());
        }
        return this.query(cnd);
    }

    @Override
    public List<Long> findChildrenIds(Long parentId) {
        List<CmsArticleColumn> articleColumns = Daos
                .ext(this.dao(), FieldFilter.create(this.getEntityClass(), "^id|parentId$"))
                .query(this.getEntityClass(), Cnd.where("delFlag", "=", 0));
        List<Long> ids = new ArrayList<Long>();
        ids.add(parentId);
        ids = treeList(ids, articleColumns, parentId);
        return ids;
    }

    @Override
    public List<CmsArticleColumn> findByParentAlias(String alias) {
        List<CmsArticleColumn> articleColumns = new ArrayList<>();
        CmsArticleColumn articleColumn = findByAlias(alias);
        if (Lang.isNotEmpty(articleColumn)) {
            articleColumns = this.query(Cnd.where("delFlag", "=", 0).and("parentId", "=", articleColumn.getId()));
        }
        return articleColumns;
    }

    @Override
    public List<Ztree> selectArticleColumnTree(CmsArticleColumn articleColumn) {
        List<CmsArticleColumn> articleColumList = selectArticleColumList(articleColumn);
        List<Ztree> ztrees = initZtree(articleColumList);
        return ztrees;
    }

    @Override
    public Integer getMaxOrderNum(Long parentId) {
        int orderNum = 1;
        StringBuffer sqlStr = new StringBuffer("SELECT MAX(orderNum) FROM website_article_column WHERE del_flag=0");
        if (Lang.isNotEmpty(parentId)) {
            sqlStr.append(" and parentId=@parentId");
        }
        Sql sql = Sqls.fetchInt(sqlStr.toString());
        sql.setParam("parentId", parentId);
        this.dao().execute(sql);
        orderNum += sql.getInt(0);
        return orderNum;
    }

    @Override
    public PageredData<CmsArticleColumn> list(CmsArticleColumn articleColumn, PageDomain pageDomain) {
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Cnd cnd = Cnd.where("delFlag", "=", 0);

        if (Strings.isNotBlank(articleColumn.getName())) {
            cnd.and(Cnd.exps("name", "like", articleColumn.getName() + "%").or("alias", "like",
                    articleColumn.getName() + "%"));
        }
        if (Lang.isNotEmpty(articleColumn.getParentId())) {
            cnd.and(Cnd.exps("id", "=", articleColumn.getParentId()).or("parentId", "=", articleColumn.getParentId()));
        }
        cnd.orderBy("level", "asc").orderBy("createTime", "desc");
        PageredData<CmsArticleColumn> pageredData = this.searchByPage(pageNum, pageSize, cnd);
        return pageredData;
    }

    @Transactional
    @Override
    public AjaxResult insertAddDo(CmsArticleColumn articleColumn) {
        try {
            if (!Strings.isBlank(articleColumn.getAlias())) {
                CmsArticleColumn c = this.findByAlias(articleColumn.getAlias());
                if (!Lang.isEmpty(c))
                    return AjaxResult.error("栏目编码重复，请重新输入！");
            }
            Long pid = articleColumn.getParentId();
            Long level = this.getLevelByParentId(pid);
            articleColumn.setLevel(level);
            // 添加的时候还需要向acl中插入一条数据，代表该用户拥有这个栏目的权限
            this.save(articleColumn);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success();
    }

    @Override
    public void insertWithSite(CmsSite site) {
        Long siteId = site.getId();
        String siteName = site.getName();
        String siteAlias = site.getAlias();
        String listTpl = site.getListTpl();
        String contentTpl = site.getContentTpl();
        CmsArticleColumn articleColumn = CmsArticleColumn.builder().type(0).name(siteName).alias(siteAlias).level(1L)
                .parentId(0L).frontMaxSize(0).siteId(siteId).siteName(siteName).listTpl(listTpl).contentTpl(contentTpl)
                .build();
        articleColumn.setOrderNum(site.getOrderNum());
        articleColumn.setDelFlag(site.getDelFlag());
        articleColumn.setCreateBy(site.getCreateBy());
        articleColumn.setCreateTime(site.getCreateTime());
        articleColumn.setUpdateBy(site.getUpdateBy());
        articleColumn.setUpdateTime(site.getUpdateTime());
        articleColumn.setRemark(site.getRemark());
        save(articleColumn);
    }

    @Transactional
    @Override
    public AjaxResult updateEditDo(CmsArticleColumn articleColumn) {
        try {
            Long articleColumnId = articleColumn.getId();
            Long parentArticleColumnId = articleColumn.getParentId();
            String articleColumnName = articleColumn.getName();
            CmsArticleColumn dbArticleColumn = findById(articleColumnId);
            // 说明栏目层级发生变化，这个时候需要更新该栏目下面子栏目的层级
            if (Lang.isNotEmpty(dbArticleColumn)
                    && !dbArticleColumn.getParentId().equals(articleColumn.getParentId())) {
                CmsArticleColumn parentArticleColumn = findById(parentArticleColumnId);
                if (Lang.isNotEmpty(parentArticleColumn)) {
                    Long level = parentArticleColumn.getLevel();
                    articleColumn.setLevel(level + 1);
                    updateLevelCascade(articleColumn);
                }
            }
            if (!dbArticleColumn.getName().equals(articleColumnName)) {
                // 更新子栏目的父栏目名称
                update(Chain.make("parentName", articleColumn.getName()),
                        Cnd.where("parentId", "=", articleColumn.getId()));
                articleService.updateWithArticleColumn(dbArticleColumn);
            }
            updateIgnoreNull(articleColumn);
        } catch (Exception e) {
            e.printStackTrace();
            AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateWithSite(CmsSite site) {
        String siteName = site.getName();
        try {
            CmsArticleColumn articleColumn = this
                    .fetch(Cnd.where("delFlag", "=", 0).and("name", "=", siteName).and("level", "=", 1));
            if (Lang.isNotEmpty(articleColumn)) {
                this.update(Chain.make("name", siteName), Cnd.where("id", "=", articleColumn.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
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
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult deleteByIdsWithLogic(String idStr) {
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
    public AjaxResult deleteByIdsWithLogic(List<Long> ids) {
        Long[] idArr = new Long[ids.size()];
        ids.toArray(idArr);
        return deleteByIdsWithLogic(idArr);
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

    public List<Ztree> initZtree(List<CmsArticleColumn> articleColumnList) {
        List<Ztree> ztrees = new ArrayList<>();
        for (CmsArticleColumn articleColumn : articleColumnList) {
            Ztree ztree = new Ztree();
            ztree.setId(articleColumn.getId());
            ztree.setpId(articleColumn.getParentId());
            ztree.setName(articleColumn.getName());
            ztree.setTitle(articleColumn.getName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    private Long getLevelByParentId(Long parentId) {
        Long level = 0L;
        if (Lang.isNotEmpty(parentId)) {
            CmsArticleColumn articleColumn = this.findById(parentId);
            if (Lang.isNotEmpty(articleColumn)) {
                level = articleColumn.getLevel() + 1L;
            }
        }
        return level;
    }

    /**
     * 根栏目是否可以编辑
     *
     * @param articleColumn
     * @return
     */
    private boolean canEditRoot(CmsArticleColumn articleColumn) {
        boolean flag = true;
        if (Lang.isNotEmpty(articleColumn)) {
            Long id = articleColumn.getId();
            String name = articleColumn.getName();
            String alias = articleColumn.getAlias();
            CmsArticleColumn dbArticleColumn = findById(id);
            // 顶级目录不能修改
            if (dbArticleColumn.getLevel() == 1)
                flag = false;
        }
        return flag;
    }

    @Transactional
    @Override
    public AjaxResult deleteRootArticleColumnsWithLogic(Long[] ids) {
        List<String> names = new ArrayList<>();
        List<Long> articleColumnIds = new ArrayList<>();
        List<CmsSite> sites = siteService.findByIds(ids);
        try {
            if (Lang.isNotEmpty(sites) && sites.size() > 0) {
                for (CmsSite site : sites) {
                    String name = site.getName();
                    names.add(name);
                }
            }
            List<CmsArticleColumn> articleColumns = findByNames(names, true);
            if (Lang.isNotEmpty(articleColumns) && articleColumns.size() > 0) {
                for (CmsArticleColumn articleColumn : articleColumns) {
                    articleColumnIds.add(articleColumn.getId());
                }
                deleteByIdsWithLogic(articleColumnIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    private List<Long> treeList(List<Long> ids, List<CmsArticleColumn> articleColumns, Long parentId) {
        for (CmsArticleColumn articleColumn : articleColumns) {
            if (articleColumn.getParentId().equals(parentId)) {
                treeList(ids, articleColumns, articleColumn.getId());
                ids.add(articleColumn.getId());
            }
        }
        return ids;
    }

    /**
     * 级联更新栏目的层级
     *
     * @param articleColumn
     */
    private void updateLevelCascade(CmsArticleColumn articleColumn) {
        Long id = articleColumn.getId();
        Long level = articleColumn.getLevel();
        List<CmsArticleColumn> articleColumns = findByParentId(id);
        if (Lang.isNotEmpty(articleColumns) && articleColumns.size() > 0) {
            for (CmsArticleColumn aColumn : articleColumns) {
                update(Chain.make("level", (level + 1)).add("parentName", aColumn.getName()),
                        Cnd.where("id", "=", aColumn.getId()));
                aColumn = findById(aColumn.getId());
                if (Lang.isNotEmpty(aColumn)) {
                    updateLevelCascade(aColumn);
                }
            }
        }
    }
}
