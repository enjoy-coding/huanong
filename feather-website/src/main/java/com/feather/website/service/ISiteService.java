package com.feather.website.service;

import java.util.List;

import org.nutz.plugin.spring.boot.service.entity.PageredData;

import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.domain.CmsSite;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;

/**
 * @author nothing
 * @date 2019-10-23 15:59
 */
public interface ISiteService {
    /**
     * @param id
     * @return
     */
    public CmsSite findById(Long id);

    /**
     * 查找一组站点
     * 
     * @param ids
     * @return
     */
    public List<CmsSite> findByIds(Long[] ids);

    /**
     * 根据name进行查询，name可以为id
     *
     * @param name
     * @return
     */
    public CmsSite findByName(String name);

    /**
     * 根据栏目查找站点名称
     * 
     * @param articleColumn
     * @return
     */
    public String findSiteNameByArticleColumn(CmsArticleColumn articleColumn);

    /**
     * 根据部门id查询
     *
     * @param deptId
     * @return
     */
    public CmsSite findByDeptId(Long deptId);

    /**
     * 分页查询
     *
     * @param site
     * @return
     */
    public PageredData<CmsSite> list(CmsSite site, PageDomain pageDomain);

    /**
     * 获取最大序列号
     * 
     * @return
     */
    public Integer getMaxOrderNum();

    /**
     * 添加站点
     *
     * @param site
     * @return
     */
    public AjaxResult insertAddDo(CmsSite site);

    /**
     * 编辑站点
     *
     * @param site
     * @return
     */
    public AjaxResult updateEditDo(CmsSite site);

    /**
     * 删除
     * 
     * @param idStr
     * @return
     */
    public AjaxResult deleteByIds(String idStr);

    /**
     * 删除一组站点
     *
     * @param ids
     * @return
     */
    public AjaxResult deleteByIds(Long[] ids);

}
