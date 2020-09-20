package com.feather.website.service;

import java.util.List;

import org.nutz.plugin.spring.boot.service.entity.PageredData;

import com.feather.website.domain.CmsArticle;
import com.feather.website.domain.CmsArticleColumn;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;

/**
 * @author nothing
 * @date 2019-10-29 15:54
 */
public interface IArticleService {
    /**
     * @param id
     * @return
     */
    public CmsArticle findById(Long id);

    /**
     * 分页查询指定pageSize的文章
     * 
     * @param articleColumnAlias
     * @param pageSize
     * @return
     */
    public List<CmsArticle> findByArticleColumnAlias(String articleColumnAlias, Integer pageSize);

    /**
     * 分页查询pageSize的文章
     * 
     * @param articleColumnId
     * @param pageSize
     * @return
     */
    public List<CmsArticle> findByArticleColumnId(Long articleColumnId, Integer pageSize);

    /**
     * 分页查询指定pageSize的文章
     * 
     * @param articleColumnAlias
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<CmsArticle> findByArticleColumnAlias(String articleColumnAlias, Integer pageNum, Integer pageSize);

    /**
     * 分页查询pageSize的文章
     * 
     * @param articleColumnId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<CmsArticle> findByArticleColumnId(Long articleColumnId, Integer pageNum, Integer pageSize);

    /**
     * 分页查询
     *
     * @param article
     * @return
     */
    public PageredData<CmsArticle> list(CmsArticle article, PageDomain pageDomain);

    /**
     * 查询指定记录数据
     * 
     * @param pageNum
     * @param pageSize
     * @param articleColumnId
     * @return
     */
    public List<CmsArticle> list(Integer pageNum, Integer pageSize, Long articleColumnId);

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    public AjaxResult insertAddDo(CmsArticle article);

    /**
     * 编辑文章
     *
     * @param article
     * @return
     */
    public AjaxResult updateEditDo(CmsArticle article);

    /**
     * 更新文章的栏目名
     * 
     * @param articleColumn
     */
    public void updateWithArticleColumn(CmsArticleColumn articleColumn);

    /**
     * 删除 逻辑删除
     * 
     * @param idStr
     * @return
     */
    public AjaxResult deleteByIds(String idStr);

    /**
     * 删除一组文章 逻辑删除
     *
     * @param ids
     * @return
     */
    public AjaxResult deleteByIds(Long[] ids);

    /**
     * 逻辑删除文章
     * 
     * @param idStr
     * @return
     */
    public AjaxResult deleteByIdsWithLogic(String idStr);

    /**
     * 逻辑删除文章
     * 
     * @param ids
     * @return
     */
    public AjaxResult deleteByIdsWithLogic(Long[] ids);

}
