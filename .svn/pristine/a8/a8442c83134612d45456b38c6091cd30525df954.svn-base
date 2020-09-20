package com.feather.website.analysis;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.feather.website.domain.CmsArticle;
import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.domain.CmsSite;
import com.feather.website.domain.CmsTemplateAttr;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.ISiteService;
import org.jsoup.nodes.Element;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;

/**
 * 模板2的html解析类
 *
 * @author nothing
 */
public class Model2Analysis extends AbstractModelAnalysis {

    @Override
    public String nav(NutMap params) {
        Element element = (Element) params.get("element");
        String blockValue = params.getString("blockValue");
        ISiteService siteService = (ISiteService) params.get("siteService");
        IArticleColumnService articleColumnService = (IArticleColumnService) params.get("articleColumnService");
        StringBuilder htmlPart = new StringBuilder();
        Element editBox = element.child(0);
        htmlPart.append(editBox.outerHtml());
        htmlPart.append("<ul>");
        if (Strings.isNotBlank(blockValue)) {
            String[] ids = blockValue.split(";");
            List<CmsArticleColumn> articleColumns = articleColumnService.findByIds(blockValue);
            if (!Lang.isEmpty(articleColumns) && articleColumns.size() > 0) {
                String siteName = siteService.findSiteNameByArticleColumn(articleColumns.get(0));
                Map<Long, CmsArticleColumn> map = articleColumns.stream().collect(Collectors.toMap(CmsArticleColumn::getId, Function.identity()));

                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    CmsArticleColumn articleColumn = map.get(id);
                    String articleColumnName = articleColumn.getName();
                    if ("首页".equals(articleColumnName)) {
                        htmlPart.append("<li><a href=\"/front/" + siteName + "\">").append(articleColumnName).append("</a></li>");
                    } else {
                        htmlPart.append("<li><a href=\"/front/" + siteName + "/" + articleColumn.getId() + "/list/1\">").append(articleColumnName).append("</a></li>");
                    }
                    if (i != ids.length - 1) {
                        htmlPart.append("<li class=\"line\">|</li>");
                    }
                }
            }
            htmlPart.append("</ul>");
        }
        return htmlPart.toString();
    }

    /**
     * 左侧室内人员轮播图1
     *
     * @param params
     * @return
     */
    @Override
    public String carousel1(NutMap params) {
        String htmlPart = commonCarouselList(params, true, true, true);
        return htmlPart;
    }

    /**
     * 中间轮播图2
     *
     * @param params
     * @return
     */
    @Override
    public String carousel2(NutMap params) {
        String htmlPart = commonCarouselList(params, false, false, false);
        return htmlPart;
    }

    /**
     * 一个栏目的链接
     */
    public String oneCategoryLink(NutMap params) {
        CmsSite site = (CmsSite) params.get("site");
        Element element = (Element) params.get("element");
        Long blockValue = params.getLong("blockValue");
        IArticleColumnService articleColumnService = (IArticleColumnService) params.get("categoryService");
        StringBuilder htmlPart = new StringBuilder();
        Element editBox = element.child(0);
        htmlPart.append(editBox.outerHtml());
        // 位置为1的代表真正需要进行修改的模板原型
        Element protoElement = element.child(1);
        if (Lang.isNotEmpty(blockValue)) {
            CmsArticleColumn articleColumn = articleColumnService.findById(blockValue);
            Element link = protoElement.select("a[href]").first();
            link.attr("href", "/front/" + site.getId() + "/" + articleColumn.getId()
                    + "/list/1");
            htmlPart.append(link.outerHtml());
        }
        return htmlPart.toString();
    }

    /**
     * <li>
     * <span class="date" style="float:right; padding-right:5px;">2019-06-12</span>
     * <a href="/front/content/6F08100A18164CE8B89C0A801C7C0A49/70021D091A26259C3799C121C0124424/508783e24cfd4b6e95add75571d86bec" title="" target="_blank">
     * <img src="/templates/model/model2/images/listdisc.jpg" height="6" width="4">eeeeeeeee
     * <img src="/templates/model/model2/images/new.gif">
     * </a>
     * </li>
     */
    @Override
    protected String buildListLiTag(CmsSite site, CmsArticleColumn articleColumn,
                                    CmsArticle article, CmsTemplateAttr templateAttr,
                                    boolean needNewGif, boolean needDate) {
        Integer textLength = templateAttr.getTextLength();
        String title = article.getTitle();
        String showTitle = title;
        String hrefStr = "";
        StringBuilder liFormatBuilder = new StringBuilder("<li>");
        if (needDate) {
            liFormatBuilder
                    .append("<span class=\"date\" style=\"float:right; padding-right:5px;\">2019-11-15</span>");
        }
        liFormatBuilder
                .append("<a href=\"%s\" title=\"%s\" target=\"_blank\">");
        liFormatBuilder.append(
                "<img src=\"/templates/model/statics/images/listdisc.jpg\" height=\"6\" width=\"4\">%s");
       /* if (needNewGif && DateUtil.isNew(content.getPublishAt())) {
            liFormatBuilder.append(
                    "<img src=\"/templates/model/statics/images/new.gif\"/>");
        }*/
        liFormatBuilder.append("</a></li>");
        if (!Lang.isEmpty(textLength) && textLength > 0) {
            if (article.getTitle().length() > textLength) {
                showTitle = article.getTitle().substring(0, textLength) + "...";
            }
            hrefStr = "/front/content/" + site.getId() + "/" + article.getId()
                    + "/" + article.getId();
            return String.format(liFormatBuilder.toString(), hrefStr, title,
                    showTitle);
        }
        return "";
    }
}
