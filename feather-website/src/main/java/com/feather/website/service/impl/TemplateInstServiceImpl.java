package com.feather.website.service.impl;

import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.springframework.stereotype.Service;

import com.feather.website.domain.CmsTemplateInst;
import com.feather.website.service.ITemplateInstService;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.utils.StringUtils;

/**
 * @author nothing
 * @date 2019-11-13 17:23
 */
@Service
public class TemplateInstServiceImpl extends BaseService<CmsTemplateInst> implements ITemplateInstService {

    @Override
    public CmsTemplateInst findById(Long id) {
        return fetch(Cnd.where("delFlag", "=", 0).and("id", "=", id));
    }

    @Override
    public List<CmsTemplateInst> findByIds(Long[] ids) {
        return query(Cnd.where("delFlag", "=", 0).and("id", "in", ids));
    }

    @Override
    public CmsTemplateInst findByName(String name) {
        return fetch(Cnd.where("delFlag", "=", 0).and("name", "=", name));
    }

    @Override
    public PageredData<CmsTemplateInst> list(CmsTemplateInst templateInst, PageDomain pageDomain) {
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Cnd cnd = Cnd.where("delFlag", "=", 0);

        if (Strings.isNotBlank(templateInst.getName())) {
            cnd.and(Cnd.exps("name", "like", templateInst.getName() + "%"));
        }

        if (Lang.isNotEmpty(templateInst.getSiteId())) {
            cnd.and("siteId", "=", templateInst.getSiteId());
        }
        cnd.orderBy("createTime", "desc");
        PageredData<CmsTemplateInst> pageredData = this.searchByPage(pageNum, pageSize, cnd);
        return pageredData;
    }

    @Override
    public AjaxResult insertAddDo(CmsTemplateInst templateInst) {
        try {
            boolean isExist = checkTemplateInstRepeat(templateInst);
            if (isExist)
                return AjaxResult.error("模板实例重复，请检查");
            templateInst = this.save(templateInst);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateEditDo(CmsTemplateInst templateInst) {
        try {
            CmsTemplateInst dbTemplateInst = this.findById(templateInst.getId());
            this.updateIgnoreNull(templateInst);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateTemplateInstBySiteId(Long siteId, String siteName) {
        try {
            this.update(Chain.make("siteName", siteName), Cnd.where("siteId", "=", siteId));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateTmplateInstByTplId(Long tplId, String tplName) {
        try {
            this.update(Chain.make("tplName", tplName), Cnd.where("tplId", "=", tplId));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public void updateTemplateInstValue(Long id, String value) {
        update(Chain.make("value", value), Cnd.where("id", "=", id));
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
    public AjaxResult deleteByTplIds(Long[] tplIds) {
        try {
            this.clear(Cnd.where("tplId", "in", tplIds));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult deleteBySiteIds(Long[] siteIds) {
        try {
            this.clear(Cnd.where("siteId", "in", siteIds));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 检查模板实例是否重复
     *
     * @param templateInst
     * @return
     */
    private boolean checkTemplateInstRepeat(CmsTemplateInst templateInst) {
        boolean flag = false;
        CmsTemplateInst searchTemplateInst = this.findByName(templateInst.getName());
        if (Lang.isNotEmpty(searchTemplateInst))
            flag = true;
        return flag;
    }
}
