package com.feather.website.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.springframework.stereotype.Service;

import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.utils.StringUtils;
import com.feather.website.domain.CmsTemplateAttr;
import com.feather.website.service.ITemplateAttrService;

/**
 * @author nothing
 * @date 2019-11-14 10:08
 */
@Service
public class TemplateAttrServiceImpl extends BaseService<CmsTemplateAttr> implements ITemplateAttrService {
    @Override
    public CmsTemplateAttr findById(Long id) {
        return fetch(Cnd.where("delFlag", "=", 0).and("id", "=", id));
    }

    @Override
    public List<CmsTemplateAttr> findByIds(Long[] ids) {
        return query(Cnd.where("delFlag", "=", 0).and("id", "in", ids));
    }

    @Override
    public List<CmsTemplateAttr> findByTplId(Long tplId) {
        List<CmsTemplateAttr> list = query(Cnd.where("delFlag", "=", 0).and("tplId", "=", tplId));
        if (Lang.isEmpty(list)) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public CmsTemplateAttr findByName(String blockName) {
        CmsTemplateAttr templateAttr = this.fetch(Cnd.where("delFlag", "=", 0).and("blockName", "=", blockName));
        return templateAttr;
    }

    @Override
    public PageredData<CmsTemplateAttr> list(CmsTemplateAttr templateAttr, PageDomain pageDomain) {
        Integer pageNum = Lang.isEmpty(pageDomain.getPageNum()) ? 1 : pageDomain.getPageNum();
        Integer pageSize = Lang.isEmpty(pageDomain.getPageSize()) ? 10 : pageDomain.getPageSize();
        Cnd cnd = Cnd.where("delFlag", "=", 0);

        if (Lang.isNotEmpty(templateAttr.getTplId())) {
            cnd.and("tplId", "=", templateAttr.getTplId());
        }
        if (Strings.isNotBlank(templateAttr.getBlockName())) {
            cnd.and(Cnd.exps("name", "like", templateAttr.getBlockName() + "%"));
        }
        cnd.orderBy("createTime", "desc");
        PageredData<CmsTemplateAttr> pageredData = this.searchByPage(pageNum, pageSize, cnd);
        return pageredData;
    }

    @Override
    public Integer getMaxOrderNum() {
        int orderNum = 1;
        Sql sql = Sqls.fetchInt("SELECT MAX(orderNum) FROM website_template_attr WHERE del_flag=0");
        this.dao().execute(sql);
        orderNum += sql.getInt(0);
        return orderNum;
    }

    @Override
    public AjaxResult insertAddDo(CmsTemplateAttr templateAttr) {
        try {
            boolean isExist = checkTemplateAttrRepeat(templateAttr);
            if (isExist)
                return AjaxResult.error("模板属性重复，请检查");
            templateAttr = this.save(templateAttr);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateEditDo(CmsTemplateAttr templateAttr) {
        try {
            CmsTemplateAttr dbCmsTemplateAttr = this.findById(templateAttr.getId());
            if (Lang.isNotEmpty(dbCmsTemplateAttr)) {
            }
            this.updateIgnoreNull(templateAttr);
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

    /**
     * 检查模板属性是否重复
     *
     * @param templateAttr
     * @return
     */
    private boolean checkTemplateAttrRepeat(CmsTemplateAttr templateAttr) {
        boolean flag = false;
        CmsTemplateAttr searchTemplateAttr = this.findByName(templateAttr.getBlockName());
        if (Lang.isNotEmpty(searchTemplateAttr))
            flag = true;
        return flag;
    }
}
