package com.feather.website.service;

import java.util.List;

import org.nutz.plugin.spring.boot.service.entity.PageredData;

import com.feather.website.domain.CmsTemplateAttr;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;

/**
 * @author nothing
 * @date 2019-11-14 10:06
 */
public interface ITemplateAttrService {
    /**
     * @param id
     * @return
     */
    public CmsTemplateAttr findById(Long id);

    /**
     * 查找一组模板属性
     * 
     * @param ids
     * @return
     */
    public List<CmsTemplateAttr> findByIds(Long[] ids);

    /**
     * 根据模板查找模板属性
     * 
     * @param tplId
     * @return
     */
    public List<CmsTemplateAttr> findByTplId(Long tplId);

    /**
     * 根据name进行查询，name可以为id
     *
     * @param name
     * @return
     */
    public CmsTemplateAttr findByName(String name);

    /**
     * 分页查询
     *
     * @param templateAttr
     * @return
     */
    public PageredData<CmsTemplateAttr> list(CmsTemplateAttr templateAttr, PageDomain pageDomain);

    /**
     * 获取最大序列号
     * 
     * @return
     */
    public Integer getMaxOrderNum();

    /**
     * 添加模板属性
     *
     * @param templateAttr
     * @return
     */
    public AjaxResult insertAddDo(CmsTemplateAttr templateAttr);

    /**
     * 编辑站点
     *
     * @param templateAttr
     * @return
     */
    public AjaxResult updateEditDo(CmsTemplateAttr templateAttr);

    /**
     * 删除
     * 
     * @param idStr
     * @return
     */
    public AjaxResult deleteByIds(String idStr);

    /**
     * 删除一组模板
     *
     * @param ids
     * @return
     */
    public AjaxResult deleteByIds(Long[] ids);
}
