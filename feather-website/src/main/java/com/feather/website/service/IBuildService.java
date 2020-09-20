package com.feather.website.service;

import org.nutz.lang.util.NutMap;

import com.feather.website.domain.CmsTemplate;

/**
 * @author nothing
 * @date 2019-11-15 8:55
 */
public interface IBuildService {
    String MODEL = "model";

    String CLASS = ".class";

    /**
     *
     * @param siteId
     * @param template
     * @param tplInstId
     * @param block
     * @param blockValue
     * @return
     */
    String build(Long siteId, CmsTemplate template, Long tplInstId, String block, String blockValue);

    /**
     * 构建动态HTML返回到前台
     * 
     * @param tplInstId
     * @param params
     * @return
     */
    String buildDynamicHtmlToFront(Long tplInstId, NutMap params) throws ClassNotFoundException;
}
