package com.feather.cms.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页面对象 cms_page
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmsPage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long pageId;
    /** 所属域 */
    private Long pageDomain;
    /** 名称 */
    @Excel(name = "名称")
    private String pageName;
    /** 标题 */
    @Excel(name = "标题")
    private String pageTitle;
    /** 摘要信息 */
    private String pageMeta;
    /** 参数 */
    private String pageParams;
    /** 状态 */
    @Excel(name = "状态")
    private String pageStatus;

    /** null */
    @Excel(name = "所属域名")
    private String domainName;
    /** null */
    @Excel(name = "所属域备注")
    private String domainRemark;

    public List<String> getPageParamsList() {
        String[] array = pageParams != null ? pageParams.split(",") : null;
        List<String> params = new ArrayList<>();
        for (String item : array) {
            if (StringUtils.isNotBlank(item)) {
                params.add(item.trim());
            }
        }
        return params;
    }
}
