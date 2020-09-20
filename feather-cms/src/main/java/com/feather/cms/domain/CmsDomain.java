package com.feather.cms.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * cms域对象 cms_domain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmsDomain extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long domainId;
    /** 名称 */
    @Excel(name = "名称")
    private String domainName;
    /** 图标 */
    @Excel(name = "图标")
    private Long domainIcon;
    /** 首页 */
    @Excel(name = "首页")
    private Long domainIndex;

    /** 是否主域 */
    @Excel(name = "是否主域")
    private String domainPrimary;

    /** 首页id */
    @Excel(name = "首页id")
    private Long indexPageId;
    /** 首页名称 */
    @Excel(name = "首页名称")
    private String indexPageName;
    /** 首页标题 */
    @Excel(name = "首页标题")
    private String indexPageTitle;

    public boolean isPrimaryDomain() {
        return "1".equals(domainPrimary);
    }
}
