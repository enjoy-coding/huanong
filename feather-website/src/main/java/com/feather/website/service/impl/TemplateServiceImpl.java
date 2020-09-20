package com.feather.website.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.website.domain.CmsTemplate;
import com.feather.website.domain.CmsTemplateInst;
import com.feather.website.service.ITemplateInstService;
import com.feather.website.service.ITemplateService;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.utils.StringUtils;

/**
 * @author nothing
 * @date 2019-11-13 15:30
 */
@Service
public class TemplateServiceImpl extends BaseService<CmsTemplate> implements ITemplateService {
    @Autowired
    ITemplateInstService templateInstService;

    @Override
    public CmsTemplate findById(Long id) {
        return fetch(Cnd.where("delFlag", "=", 0).and("id", "=", id));
    }

    @Override
    public List<CmsTemplate> findByIds(Long[] ids) {
        return query(Cnd.where("delFlag", "=", 0).and("id", "in", ids));
    }

    @Override
    public CmsTemplate findByTemplateInstId(Long templateInstId) {
        CmsTemplateInst templateInst = templateInstService.findById(templateInstId);
        return findById(templateInst.getTplId());
    }

    @Override
    public CmsTemplate findByName(String name) {
        return fetch(Cnd.where("delFlag", "=", 0).and("name", "=", name));
    }

    @Override
    public PageredData<CmsTemplate> list(CmsTemplate template, PageDomain pageDomain) {
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Cnd cnd = Cnd.where("delFlag", "=", 0);

        if (Strings.isNotBlank(template.getName())) {
            cnd.and(Cnd.exps("name", "like", template.getName() + "%"));
        }
        cnd.orderBy("createTime", "desc");
        PageredData<CmsTemplate> pageredData = this.searchByPage(pageNum, pageSize, cnd);
        return pageredData;
    }

    @Override
    public AjaxResult insertAddDo(CmsTemplate template) {
        try {
            boolean isExist = checkTemplateRepeat(template);
            if (isExist)
                return AjaxResult.error("模板重复，请检查");
            template = this.save(template);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateEditDo(CmsTemplate template) {
        try {
            CmsTemplate dbCmsTemplate = this.findById(template.getId());
            if (Lang.isNotEmpty(dbCmsTemplate)) {
                // 名称发生改变,需要修改模板实例名称
                if (!dbCmsTemplate.getName().equals(template.getName())) {
                    templateInstService.updateTmplateInstByTplId(template.getId(), template.getName());
                }
            }
            this.updateIgnoreNull(template);
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

    @Transactional
    @Override
    public AjaxResult deleteByIds(Long[] ids) {
        try {
            // 首先删除模板实例
            templateInstService.deleteByTplIds(ids);
            this.clear(Cnd.where("id", "in", ids));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 检查模板是否重复
     *
     * @param template
     * @return
     */
    private boolean checkTemplateRepeat(CmsTemplate template) {
        boolean flag = false;
        CmsTemplate searchTemplate = this.findByName(template.getName());
        if (Lang.isNotEmpty(searchTemplate))
            flag = true;
        return flag;
    }
}
