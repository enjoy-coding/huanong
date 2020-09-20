package com.feather.website.service;

import java.util.List;

import org.nutz.plugin.spring.boot.service.entity.PageredData;

import com.feather.website.domain.CmsTemplateInst;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;

/**
 * @author nothing
 * @date 2019-11-13 17:21
 */
public interface ITemplateInstService {
    /**
     * @param id
     * @return
     */
    public CmsTemplateInst findById(Long id);

    /**
     * 查找一组站点
     * 
     * @param ids
     * @return
     */
    public List<CmsTemplateInst> findByIds(Long[] ids);

    /**
     * 根据name进行查询，name可以为id
     *
     * @param name
     * @return
     */
    public CmsTemplateInst findByName(String name);

    /**
     * 分页查询
     *
     * @param templateInst
     * @return
     */
    public PageredData<CmsTemplateInst> list(CmsTemplateInst templateInst, PageDomain pageDomain);

    /**
     * 添加站点
     *
     * @param templateInst
     * @return
     */
    public AjaxResult insertAddDo(CmsTemplateInst templateInst);

    /**
     * 编辑模板实例
     *
     * @param templateInst
     * @return
     */
    public AjaxResult updateEditDo(CmsTemplateInst templateInst);

    /**
     * 修改模板实例的站点名称根据站点id
     * 
     * @param siteId
     * @param siteName
     * @return
     */
    public AjaxResult updateTemplateInstBySiteId(Long siteId, String siteName);

    /**
     * 修改模板实例的模板名称根据模板id
     * 
     * @param tplId
     * @param tplName
     * @return
     */
    public AjaxResult updateTmplateInstByTplId(Long tplId, String tplName);

    /**
     * 更新新模板实例值
     * 
     * @param id
     * @param value
     */
    public void updateTemplateInstValue(Long id, String value);

    /**
     * 删除
     * 
     * @param idStr
     * @return
     */
    public AjaxResult deleteByIds(String idStr);

    /**
     * 删除一组模板实例
     *
     * @param ids
     * @return
     */
    public AjaxResult deleteByIds(Long[] ids);

    /**
     * 根据模板id删除模板实例
     * 
     * @param tplIds
     * @return
     */
    public AjaxResult deleteByTplIds(Long[] tplIds);

    /**
     * 根据站点id删除模板实例
     * 
     * @param siteIds
     * @return
     */
    public AjaxResult deleteBySiteIds(Long[] siteIds);
}
