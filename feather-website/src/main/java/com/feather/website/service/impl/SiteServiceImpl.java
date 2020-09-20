package com.feather.website.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.nutz.plugin.spring.boot.service.entity.PageredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.PageDomain;
import com.feather.common.utils.StringUtils;
import com.feather.system.domain.SysDept;
import com.feather.system.service.ISysDeptService;
import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.domain.CmsSite;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.ISiteService;
import com.feather.website.service.ITemplateInstService;

/**
 * @author nothing
 * @date 2019-10-29 15:59
 */
@Service
public class SiteServiceImpl extends BaseService<CmsSite> implements ISiteService {
    @Autowired
    ISysDeptService sysDeptService;
    @Autowired
    IArticleColumnService articleColumnService;
    @Autowired
    ITemplateInstService templateInstService;

    @Override
    public CmsSite findById(Long id) {
        CmsSite site = this.fetch(Cnd.where("delFlag", "=", 0).and("id", "=", id));
        if (Lang.isNotEmpty(site)) {
            Long deptId = site.getDeptId();
            SysDept dept = sysDeptService.selectDeptById(deptId);
            if (Lang.isNotEmpty(dept)) {
                site.setDeptName(dept.getDeptName());
            }
        }
        return site;
    }

    @Override
    public List<CmsSite> findByIds(Long[] ids) {
        return this.query(Cnd.where("delFlag", "=", 0).and("id", "in", ids));
    }

    @Override
    public CmsSite findByName(String name) {
        CmsSite site = this.fetch(Cnd.where("delFlag", "=", 0).and(Cnd.exps("name", "=", name).or("alias", "=", name)));
        if (Lang.isEmpty(site) && StringUtils.isNumeric(name)) {
            site = findById(Long.parseLong(name));
        }
        return site;
    }

    @Override
    public String findSiteNameByArticleColumn(CmsArticleColumn articleColumn) {
        String siteName = "";
        Long siteId = articleColumn.getSiteId();
        CmsSite site = findById(siteId);
        if (Lang.isNotEmpty(site)) {
            siteName = site.getName();
        }
        return siteName;
    }

    @Override
    public CmsSite findByDeptId(Long deptId) {
        return null;
    }

    @Override
    public PageredData<CmsSite> list(CmsSite site, PageDomain pageDomain) {
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Cnd cnd = Cnd.where("delFlag", "=", 0);

        if (Strings.isNotBlank(site.getName())) {
            cnd.and(Cnd.exps("name", "like", site.getName() + "%").or("alias", "like", site.getName() + "%"));
        }
        cnd.orderBy("createTime", "desc");
        PageredData<CmsSite> pageredData = this.searchByPage(pageNum, pageSize, cnd);
        return pageredData;
    }

    @Override
    public Integer getMaxOrderNum() {
        int orderNum = 1;
        Sql sql = Sqls.fetchInt("SELECT MAX(orderNum) FROM website_site WHERE del_flag=0");
        this.dao().execute(sql);
        orderNum += sql.getInt(0);
        return orderNum;
    }

    @Transactional
    @Override
    public AjaxResult insertAddDo(CmsSite site) {
        try {
            boolean isExist = checkSiteRepeat(site);
            if (isExist)
                return AjaxResult.error("站点名称或者别名重复，请检查");
            site = this.save(site);
            articleColumnService.insertWithSite(site);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Transactional
    @Override
    public AjaxResult updateEditDo(CmsSite site) {
        try {
            CmsSite dbSite = this.findById(site.getId());
            // 修改站点名后需要修改模板实例的栏目名
            if (Lang.isNotEmpty(dbSite)) {
                if (!dbSite.getName().equals(site.getName())) {
                    templateInstService.updateTemplateInstBySiteId(site.getId(), site.getName());
                }
            }
            this.updateIgnoreNull(site);
            if (Lang.isNotEmpty(dbSite) && !dbSite.getName().equals(site.getName())) {
                articleColumnService.updateWithSite(site);
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

    @Transactional
    @Override
    public AjaxResult deleteByIds(Long[] ids) {
        try {
            /**
             * 删除站点之前，需要将对应站点根栏目 （该栏目名称和别名和站点相同，看上去就是指代的站点，这样做的目的是保持栏目树和站点的统一）
             * 删除 不能将该步骤放在删除站点之后，因为站点删除之后再找站点是找不到的
             */
            articleColumnService.deleteRootArticleColumnsWithLogic(ids);
            templateInstService.deleteBySiteIds(ids);
            this.clear(Cnd.where("id", "in", ids));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 检查站点是否重复
     *
     * @param site
     * @return
     */
    private boolean checkSiteRepeat(CmsSite site) {
        boolean flag = false;
        CmsSite searchSite = this.findByName(site.getName());
        if (Lang.isNotEmpty(searchSite))
            flag = true;
        return flag;
    }
}
