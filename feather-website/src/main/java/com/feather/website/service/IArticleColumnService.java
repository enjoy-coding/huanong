package com.feather.website.service;

import java.util.List;

import org.nutz.plugin.spring.boot.service.entity.PageredData;

import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.domain.CmsSite;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.page.PageDomain;

public interface IArticleColumnService {
    /**
     * @param id
     * @return
     */
    public CmsArticleColumn findById(Long id);

    /**
     * 根据一组id查找栏目
     * 
     * @param ids
     * @return
     */
    public List<CmsArticleColumn> findByIds(Long[] ids);

    /**
     *
     * @param idStr
     * @return
     */
    public List<CmsArticleColumn> findByIds(String idStr);

    /**
     * 查询子栏目
     * 
     * @param parentId
     * @return
     */
    public List<CmsArticleColumn> findByParentId(Long parentId);

    /**
     * 根据name进行查询，name可以为id,也可以为title
     *
     * @param name
     * @return
     */
    public CmsArticleColumn findByName(String name);

    /**
     * 找到一组栏目，一般用于根节点查找
     * 
     * @param names
     *            名称列表
     * @param isRoot
     *            是否是根节点
     * @return
     */
    public List<CmsArticleColumn> findByNames(List<String> names, boolean isRoot);

    /**
     * 根据别名查找
     * 
     * @param alias
     * @return
     */
    public CmsArticleColumn findByAlias(String alias);

    /**
     * 根据站点id查找
     * 
     * @param siteId
     * @return
     */
    public List<CmsArticleColumn> findBySiteId(Long siteId);

    /**
     * 树形菜单显示栏目
     * 
     * @param articleColumn
     * @return
     */
    public List<Ztree> selectArticleColumnTree(CmsArticleColumn articleColumn);

    /**
     *
     * @param parentId
     * @return
     */
    public List<Long> findChildrenIds(Long parentId);

    /**
     * 根据父级alias查询子的栏目
     * 
     * @param alias
     * @return
     */
    public List<CmsArticleColumn> findByParentAlias(String alias);

    /**
     * 查询栏目列表
     * 
     * @param articleColumn
     * @return
     */
    public List<CmsArticleColumn> selectArticleColumList(CmsArticleColumn articleColumn);

    /**
     * 获取当前最大序号
     * 
     * @param parentId
     * @return
     */
    public Integer getMaxOrderNum(Long parentId);

    /**
     * 分页查询
     *
     * @param articleColumn
     * @return
     */
    public PageredData<CmsArticleColumn> list(CmsArticleColumn articleColumn, PageDomain pageDomain);

    /**
     * 添加栏目
     *
     * @param articleColumn
     * @return
     */
    public AjaxResult insertAddDo(CmsArticleColumn articleColumn);

    /**
     * 增加根栏目，当增加站点的时候需要调用此方法，为栏目增加一个根栏目 根栏目不可删除，名称随着站点名称的修改而进行更改
     * 
     * @param site
     */
    public void insertWithSite(CmsSite site);

    /**
     * 编辑文章
     *
     * @param articleColumn
     * @return
     */
    public AjaxResult updateEditDo(CmsArticleColumn articleColumn);

    /**
     * 更新站点的时候，如果站点的名称发生改变了，代表站点的栏目的名称也需要改变, 需要更改level为1的栏目名称
     * 
     * @param site
     * @return
     */
    public AjaxResult updateWithSite(CmsSite site);

    /**
     * 删除
     * 
     * @param idStr
     * @return
     */
    public AjaxResult deleteByIds(String idStr);

    /**
     * 删除一组栏目
     *
     * @param ids
     * @return
     */
    public AjaxResult deleteByIds(Long[] ids);

    /**
     * 逻辑删除一组栏目
     * 
     * @param ids
     * @return
     */
    public AjaxResult deleteByIdsWithLogic(List<Long> ids);

    /**
     * 逻辑删除栏目
     * 
     * @param idStr
     * @return
     */
    public AjaxResult deleteByIdsWithLogic(String idStr);

    /**
     * 逻辑删除栏目
     * 
     * @param ids
     * @return
     */
    public AjaxResult deleteByIdsWithLogic(Long[] ids);

    /**
     * 逻辑删除一组栏目根节点
     * 
     * @param ids
     *            站点的ids
     * @return
     */
    public AjaxResult deleteRootArticleColumnsWithLogic(Long[] ids);
}
