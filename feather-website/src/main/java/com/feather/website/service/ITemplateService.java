package com.feather.website.service;

import java.util.List;

import org.nutz.plugin.spring.boot.service.entity.PageredData;

import com.feather.website.domain.CmsTemplate;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;

/**
 * @author nothing
 * @date 2019-11-13 15:28
 */
public interface ITemplateService {
    /**
     * @param id
     * @return
     */
    public CmsTemplate findById(Long id);

    /**
     * 查找一组站点
     * 
     * @param ids
     * @return
     */
    public List<CmsTemplate> findByIds(Long[] ids);

    /**
     * 根据模板实例查找模板
     * 
     * @param tplInstId
     * @return
     */
    public CmsTemplate findByTemplateInstId(Long tplInstId);

    /**
     * 根据name进行查询，name可以为id
     *
     * @param name
     * @return
     */
    public CmsTemplate findByName(String name);

    /**
     * 分页查询
     *
     * @param template
     * @return
     */
    public PageredData<CmsTemplate> list(CmsTemplate template, PageDomain pageDomain);

    /**
     * 添加站点
     *
     * @param template
     * @return
     */
    public AjaxResult insertAddDo(CmsTemplate template);

    /**
     * 编辑站点
     *
     * @param template
     * @return
     */
    public AjaxResult updateEditDo(CmsTemplate template);

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
