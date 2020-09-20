package com.feather.website.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Element;
import org.nutz.lang.Lang;
import org.nutz.lang.Stopwatch;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.feather.website.domain.CmsArticle;
import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.domain.CmsSite;
import com.feather.website.domain.CmsTemplate;
import com.feather.website.domain.CmsTemplateAttr;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.IArticleService;
import com.feather.common.utils.DateUtils;
import com.feather.common4nutz.utils.HtmlRegexpUtil;

/**
 * @author nothing
 * @ClassName: AbstractModelAnalysis
 * @Description: 抽象模板解析类
 * @date 2019年9月4日 下午4:07:08
 */
public abstract class AbstractModelAnalysis implements IModelAnalysis {
    private static final Log log = Logs.get();

    /**
     * logo
     */
    @Override
    public String logo(NutMap params) {
        Element element = (Element) params.get("element");
        String blockValue = params.getString("blockValue");
        StringBuilder htmlPart = new StringBuilder();
        Element editBox = element.child(0);
        htmlPart.append(editBox.outerHtml());
        htmlPart.append("<img src=\"/media" + blockValue + "\">");
        return htmlPart.toString();
    }

    @Override
    public String list(NutMap params) {
        return commonList(params, true, true);
    }

    @Override
    public String list1(NutMap params) {
        return commonList(params, true, false);
    }

    protected String commonList(NutMap params, boolean needNewGif, boolean needDate) {
        CmsSite site = (CmsSite) params.get("site");
        Element element = (Element) params.get("element");
        Long blockValue = params.getLong("blockValue");
        CmsTemplateAttr templateAttr = (CmsTemplateAttr) params.get("templateAttr");
        IArticleColumnService articleColumnService = (IArticleColumnService) params.get("articleColumnService");
        IArticleService articleService = (IArticleService) params.get("articleService");
        StringBuilder htmlPart = new StringBuilder();
        Integer pageSize = templateAttr.getFrontSize();
        if (Lang.isNotEmpty(blockValue)) {
            CmsArticleColumn articleColumn = articleColumnService.findById(blockValue);

            Element editBox = element.child(0);
            Element headerElement = element.child(1);
            Element detailElement = element.child(2);
            htmlPart.append(editBox.outerHtml());
            Element link = headerElement.select("a[href]").first();
            link.attr("href", "/front/" + site.getId() + "/" + articleColumn.getId() + "/list/1");
            String resultHtml = HtmlRegexpUtil.filterAndReplaceHtml(headerElement, articleColumn.getName());
            htmlPart.append(resultHtml);
            List<Element> uls = detailElement.select("ul");
            Stopwatch sw = Stopwatch.begin();
            List<CmsArticle> articles = articleService.list(1, pageSize, blockValue);
            sw.stop();
            // 标签中只有一个ul标签
            if (!Lang.isEmpty(articles) && articles.size() > 0) {
                if (uls.size() == 1) {
                    Element ul = uls.get(0);
                    for (CmsArticle article : articles) {
                        String liStr = buildListLiTag(site, articleColumn, article, templateAttr, needNewGif, needDate);
                        ul.append(liStr);
                    }
                    detailElement.html(ul.outerHtml());
                    htmlPart.append(detailElement.outerHtml());
                } else {
                    /**
                     * fromIndex
                     * toIndex计算方式，假设contentList的总数是20，uls的长度为2，那么0~9的文章在ul0中，10~19为ul1中
                     * 如果总数为19，因为不能被整除，所以0~9的文章在ul0中，10~18的文章在ul1中
                     */
                    int blockSize = articles.size() / uls.size();
                    List<List<CmsArticle>> subArticleList = splitList(articles, blockSize);
                    StringBuilder ulStrBuilder = new StringBuilder();
                    for (int i = 0, len = uls.size(); i < len; i++) {
                        Element ul = uls.get(i);
                        List<CmsArticle> subArticle = subArticleList.get(i);
                        for (CmsArticle article : subArticle) {
                            String liStr = buildListLiTag(site, articleColumn, article, templateAttr, needNewGif,
                                    needDate);
                            ul.append(liStr);
                        }
                        ulStrBuilder.append(ul.outerHtml());
                    }
                    detailElement.html(ulStrBuilder.toString());
                    htmlPart.append(detailElement.outerHtml());
                }
            }
        }
        return htmlPart.toString();
    }

    /**
     * @param params
     * @param hasCarouselTitle
     *            轮播图是否有标题，主要用于查找元素的时候做定位用的
     * @param needTitle
     * @param needSubTitle
     * @return
     */
    protected String commonCarouselList(NutMap params, boolean hasCarouselTitle, boolean needTitle,
            boolean needSubTitle) {
        Element editBox, carouselDetail;
        Element carouselTitle = null;
        StringBuilder htmlPart = new StringBuilder();
        CmsSite site = (CmsSite) params.get("site");
        Element element = (Element) params.get("element");
        Long blockValue = params.getLong("blockValue");
        CmsTemplateAttr templateAttr = (CmsTemplateAttr) params.get("templateAttr");
        IArticleService articleService = (IArticleService) params.get("articleService");
        Integer pageSize = templateAttr.getFrontSize();
        params.clear();
        params.addv("pageNum", 1).addv("pageSize", pageSize).addv("categoryId", blockValue);
        List<CmsArticle> articles = articleService.list(1, pageSize, blockValue);
        // 第一个元素肯定为编辑框
        editBox = element.child(0);
        if (hasCarouselTitle) {
            carouselTitle = element.child(1);
            carouselDetail = element.child(2);
        } else {
            carouselDetail = element.child(1);
        }
        // 说明要修改标题
        if (!Lang.isEmpty(carouselTitle) && carouselTitle.hasClass("checkMemberTitle")) {
            carouselTitle.text(site.getAlias() + "成员");
        }
        Element parentEle = carouselDetail.selectFirst("ul");
        // 判断是不是由ul构建的轮播图，轮播图分两类，一类是div类型的，一类是ul类型，两类构建的方式是不一样的
        if (Lang.isEmpty(parentEle)) {
            parentEle = carouselDetail.selectFirst("div[class='swiper-wrapper']");
        }
        // 这里做元素的清空的时候需要增加验证contentList不为空
        if (Lang.isNotEmpty(articles) && articles.size() > 0) {
            parentEle.empty();
            boolean isDiv = false;
            if (parentEle.hasClass("swiper-wrapper")) {
                isDiv = true;
            }
            for (CmsArticle article : articles) {
                String liStr = buildCarouselLiTag(site, templateAttr, blockValue, article, needTitle, needSubTitle,
                        isDiv);

                parentEle.append(liStr);
            }
        }
        htmlPart.append(editBox.outerHtml());
        if (!Lang.isEmpty(carouselTitle)) {
            htmlPart.append(carouselTitle.outerHtml());
        }
        htmlPart.append(carouselDetail.outerHtml());
        return htmlPart.toString();
    }

    /**
     * 构建列表页的li标签
     *
     * @param site
     * @param articleColumn
     * @param article
     * @param templateAttr
     * @param needNewGif
     * @return needDate
     */
    protected String buildListLiTag(CmsSite site, CmsArticleColumn articleColumn, CmsArticle article,
            CmsTemplateAttr templateAttr, boolean needNewGif, boolean needDate) {

        Integer textLength = templateAttr.getTextLength();
        String title = Strings.isBlank(article.getTitle()) ? "" : article.getTitle();
        String showTitle = title;
        String hrefStr;
        StringBuilder liFormatBuilder = new StringBuilder("<li><a href=\"%s\" title=\"%s\" target=\"_blank\">%s");
        if (needNewGif && DateUtils.differentDays(article.getPublishAt(), new Date()) == 0) {
            liFormatBuilder.append("<img src=\"/templates/model/statics/images/new.gif\"/>");
        }
        if (needDate) {
            liFormatBuilder.append("<span>2019-11-25</span>");
            /*
             * + DateUtil.getDate(content.getPublishAt(), "yyyy-MM-dd") + );
             */
        }
        liFormatBuilder.append("</a></li>");
        if (!Lang.isEmpty(textLength) && textLength > 0) {
            if (title.length() > textLength) {
                showTitle = article.getTitle().substring(0, textLength) + "...";
            }
            hrefStr = "/front/content/" + site.getId() + "/" + articleColumn.getId() + "/" + article.getId();
            return String.format(liFormatBuilder.toString(), hrefStr, title, showTitle);
        }
        return "";
    }

    /**
     * <li><div>
     * <h3>姓名：xxx</h3>
     * <h3>职务：主任</h3> </div>
     * <a href="#" title=""><img src="/templates/model/model2/images/img1.jpg"
     * /></a></li> 或者
     * <li><a href="#" title="信息中心组织开展谈心交流户外长走活动">
     * <img src="/templates/model/model2/images/newsimg2.jpg"> </a></li> 或者
     * <div class="swiper-slide">
     * <img src="/templates/model/model3/images/newsimg1.jpg" />
     * <p>
     * 湖北省关于消防工作会议湖北省关于消防工作会议湖北省关于消防工作会议
     * </p>
     * </div> 或者 <div class="swiper-slide">
     * <img src="/templates/model/model3/images/checkMemberImg1.jpg" /> </div>
     *
     * @param site
     * @param templateAttr
     * @param articleColumnId
     * @param article
     * @param needTitle
     * @param needSubTitle
     * @param isDiv
     * @return
     */
    protected String buildCarouselLiTag(CmsSite site, CmsTemplateAttr templateAttr, Long articleColumnId,
            CmsArticle article, boolean needTitle, boolean needSubTitle, boolean isDiv) {
        String titlePart, subTitlePart, detailPart;
        Element partElement = null;
        int textLength = templateAttr.getTextLength();
        String title = Strings.isBlank(article.getTitle()) ? "" : article.getTitle();
        String showTitle = (textLength > 0 && title.length() > textLength) ? title.substring(0, textLength) + "..."
                : title;
        String subTitle = Strings.isBlank(article.getSubTitle()) ? "" : article.getSubTitle();
        String linkUrl = "/front/content/" + site.getId() + "/" + articleColumnId + "/" + article.getId();
        String imgUrl = "/media" + article.getCoverImage();
        detailPart = "<a href=\"" + linkUrl + "\" title=\"" + title + "\">" + "<img src=\"" + imgUrl + "\" ></a>";
        if (isDiv) {
            partElement = new Element("div");
            partElement.addClass("swiper-slide");
            partElement.append(detailPart);
            if (needTitle) {
                titlePart = "<p><a href=\"" + linkUrl + "\" title=\"" + title + "\">" + showTitle + "</a></p>";
                partElement.append(titlePart);
            }
        } else {
            partElement = new Element("li");
            partElement.append(detailPart);
            Element titleElement = new Element("div");
            if (needTitle && Strings.isNotBlank(title)) {
                titlePart = "<h3>" + title + "</h3>";
                titleElement.append(titlePart);
            }
            if (needSubTitle && Strings.isNotBlank(subTitle)) {
                subTitlePart = "<h3>" + subTitle + "</h3>";
                titleElement.append(subTitlePart);
            }
            partElement.prepend(titleElement.outerHtml());
        }
        return partElement.outerHtml();
    }

    /**
     * <span>
     * <h4>部门简介</h4></span> 文本框只有唯一值的情况
     *
     * @param params
     * @return
     */
    public String singleText(NutMap params) {
        Element element = (Element) params.get("element");
        String blockValue = params.getString("blockValue");
        StringBuilder htmlPart = new StringBuilder();
        Element editBox = element.child(0);
        htmlPart.append(editBox.outerHtml());
        // 这里直接找到指定html的文本,并替换里面的文本
        Element protoElement = element.child(1);
        String resultHtml = HtmlRegexpUtil.filterAndReplaceHtml(protoElement, blockValue);
        htmlPart.append(resultHtml);
        return htmlPart.toString();
    }

    /**
     * 一个栏目的链接
     *
     * @return
     */
    public String oneCategoryLink(NutMap params) {
        CmsSite site = (CmsSite) params.get("site");
        Element element = (Element) params.get("element");
        Long blockValue = params.getLong("blockValue");
        IArticleColumnService articleColumnService = (IArticleColumnService) params.get("articleColumnService");
        StringBuilder htmlPart = new StringBuilder();
        Element editBox = element.child(0);
        htmlPart.append(editBox.outerHtml());
        // 位置为1的代表真正需要进行修改的模板原型
        Element protoElement = element.child(1);
        if (Lang.isNotEmpty(blockValue)) {
            CmsArticleColumn articleColumn = articleColumnService.findById(blockValue);
            Element link = protoElement.select("a[href]").first();
            link.attr("href", "/front/" + site.getId() + "/" + articleColumn.getId() + "/list/1");
            String resultHtml = HtmlRegexpUtil.filterAndReplaceHtml(protoElement, articleColumn.getName());
            htmlPart.append(resultHtml);
        }
        return htmlPart.toString();
    }

    /**
     * 底部信息
     */
    public String footerInfo(NutMap params) {
        Element element = (Element) params.get("element");
        String blockValue = params.getString("blockValue");
        StringBuilder htmlPart = new StringBuilder();
        Element editBox = element.child(0);
        htmlPart.append(editBox.outerHtml());
        String[] blockValueArr = blockValue.split("/");
        if (!Lang.isEmpty(blockValueArr) && blockValueArr.length > 0) {
            for (int i = 0; i < blockValueArr.length; i++) {
                htmlPart.append("<span>" + blockValueArr[i] + "</span><br/>");
            }
        }
        return htmlPart.toString();
    }

    /********************* 二级页面都是静态方法 **************************/

    /**
     * 面包屑导航
     * <ul>
     * <li><img src="/templates/model/model2/images/weiz2.png"/></li>
     * <li><span>当前位置:</span></li>
     * <li><a href="#">首页</a></li>
     * <li>—></li>
     * <li><a href="#">政策法规</a></li>
     * </ul>
     *
     * @param params
     * @return
     */
    public static String breadCrumbs(NutMap params) {
        StringBuilder htmlPart = new StringBuilder();
        Element element = (Element) params.get("element");
        CmsTemplate template = (CmsTemplate) params.get("template");
        CmsSite site = (CmsSite) params.get("site");
        CmsArticleColumn category = (CmsArticleColumn) params.get("articleColumn");
        String name = template.getName();
        if (element.hasClass("currentPosition")) {
            htmlPart.append("<p>当前位置：").append("<a href=\"/front/").append(site.getId()).append("\">首页</a>")
                    .append("<span>—></span><span>").append("<a href=\"/front/").append(site.getId()).append("/")
                    .append(category.getId()).append("/list/1").append("\">").append(category.getName()).append("</a>")
                    .append("</span></p>");
        }
        if (element.hasClass("listnowLocation")) {
            htmlPart.append("<ul>");
            htmlPart.append("<li><img src=\"/templates/model/").append(name).append("/images/weiz2.png\"/></li>");
            htmlPart.append("<li><span>当前位置:</span></li>");
            htmlPart.append("<li><a href=\"/front/").append(site.getId()).append("\">首页</a></li>");
            htmlPart.append("<li>—></li>");
            htmlPart.append("<li><a href=\"/front/").append(site.getId()).append("/").append(category.getId())
                    .append("/list/1").append("\">").append(category.getName()).append("</a></li>");
            htmlPart.append("</ul>");
        }
        return htmlPart.toString();
    }

    @SuppressWarnings("unchecked")
    /*
     * public static String buildListContents(NutMap params) { Element element =
     * (Element) params.get("element"); Page<CmsContent> contentPage =
     * (Page<CmsContent>) params .get("contentPage"); CmsSite site = (CmsSite)
     * params.get("site"); CmsCategory category = (CmsCategory)
     * params.get("category"); CmsTemplate template = (CmsTemplate)
     * params.get("template"); String siteId = site.getId(); String categoryId =
     * category.getId(); StringBuilder htmlPart = new StringBuilder("<ul>"); if
     * (!Lang.isEmpty(contentPage) && contentPage.getRows().size() > 0) { if
     * (element.hasClass("listContents")) { for (CmsContent content :
     * contentPage.getRows()) { htmlPart.append("<li>");
     * htmlPart.append("<a href=\"/front/content/").append(siteId)
     * .append("/").append(categoryId).append("/") .append(content.getId())
     * .append("\" target=\"_blank\" >");
     * htmlPart.append("<img src=\"/templates/model/")
     * .append(template.getName()) .append("/images/list_img.jpg\"/>");
     * htmlPart.append(content.getTitle()); htmlPart.append("</a>");
     * htmlPart.append("<span>").append(DateUtil
     * .getDate(content.getPublishAt(), "yyyy-MM-dd")) .append("</span>");
     * htmlPart.append("</li>"); } } if (element.hasClass("detailList")) { for
     * (CmsContent content : contentPage.getRows()) { htmlPart.append("<li>");
     * htmlPart.append("<a href=\"/front/content/").append(siteId)
     * .append("/").append(categoryId).append("/") .append(content.getId())
     * .append("\" target=\"_blank\" >"); htmlPart.append(content.getTitle());
     * htmlPart.append("</a>"); htmlPart.append("<span>").append(DateUtil
     * .getDate(content.getPublishAt(), "yyyy-MM-dd")) .append("</span>");
     * htmlPart.append("</li>"); } } } htmlPart.append("</ul>"); return
     * htmlPart.toString(); }
     */

    /*
     * public static String buildSearchContents(NutMap params) {
     * 
     * Element element = (Element) params.get("element"); Page<V_SearchDataList>
     * contentPage = (Page<V_SearchDataList>) params .get("contentPage");
     * CmsSite site = (CmsSite) params.get("site"); CmsTemplate template =
     * (CmsTemplate) params.get("template"); String siteId = site.getId();
     * StringBuilder htmlPart = new StringBuilder("<ul>"); if
     * (!Lang.isEmpty(contentPage) && contentPage.getRows().size() > 0) { if
     * (element.hasClass("listContents")) { for (V_SearchDataList content :
     * contentPage.getRows()) { String categoryId = content.getCategoryId();
     * htmlPart.append("<li>");
     * htmlPart.append("<a href=\"/front/content/").append(siteId)
     * .append("/").append(categoryId).append("/") .append(content.getId())
     * .append("\" target=\"_blank\" >");
     * htmlPart.append("<img src=\"/templates/model/")
     * .append(template.getName()) .append("/images/list_img.jpg\"/>");
     * htmlPart.append(content.getTitle()); htmlPart.append("</a>");
     * htmlPart.append("<span>").append(DateUtil
     * .getDate(content.getPublishAt(), "yyyy-MM-dd")) .append("</span>");
     * htmlPart.append("</li>"); } } if (element.hasClass("detailList")) { for
     * (V_SearchDataList content : contentPage.getRows()) { String categoryId =
     * content.getCategoryId(); htmlPart.append("<li>");
     * htmlPart.append("<a href=\"/front/content/").append(siteId)
     * .append("/").append(categoryId).append("/") .append(content.getId())
     * .append("\" target=\"_blank\" >"); htmlPart.append(content.getTitle());
     * htmlPart.append("</a>"); htmlPart.append("<span>").append(DateUtil
     * .getDate(content.getPublishAt(), "yyyy-MM-dd")) .append("</span>");
     * htmlPart.append("</li>"); } } } htmlPart.append("</ul>"); return
     * htmlPart.toString(); }
     */

    /*
     * public static String buildSearchPagination(NutMap params) {
     * Page<V_SearchDataList> contentPage = (Page<V_SearchDataList>) params
     * .get("contentPage"); CmsSite site = (CmsSite) params.get("site"); String
     * keyword = params.getString("keyword"); String siteId = site.getId(); int
     * pageNum = contentPage.getPageNo(); int totalPages =
     * contentPage.getTotalPages(); int index = 1; int showPageNum = 9;
     * StringBuilder htmlPart = new StringBuilder("<ul class=\"pagination\">");
     * int pervious = (pageNum - 1) > 1 ? pageNum - 1 : 1;
     * htmlPart.append("<li><a href=\"/front/").append(siteId)
     * .append("/search?keyword=").append(keyword)
     * .append("&pageNumber=").append(pervious).append("&pageSize=15")
     * .append(" \">«</a></li>"); if (pageNum - 4 > 1) { int num = pageNum - 4 >
     * totalPages ? totalPages : pageNum - 4;
     * htmlPart.append("<li><a href=\"/front/").append(siteId)
     * .append("/search?keyword=").append(keyword)
     * .append("&pageNumber=").append(num).append("&pageSize=15")
     * .append(" \">···</a></li>"); } if (pageNum >= 5 && totalPages - pageNum
     * >= 5) { index = pageNum - 4; showPageNum = pageNum + 4; } else if
     * (totalPages - pageNum <= 5) { index = pageNum - (8 - (totalPages -
     * pageNum)); index = index > 0 ? index : 1; showPageNum = totalPages; }
     * else if (totalPages == pageNum) { index = pageNum - 9; showPageNum =
     * totalPages; } for (; index <= showPageNum; index++) { if (pageNum ==
     * index) { htmlPart.append("<li><a class=\"active\" href=\"/front/")
     * .append(siteId).append("/search?keyword=")
     * .append(keyword).append("&pageNumber=").append(index)
     * .append("&pageSize=15").append(" \">").append(index)
     * .append("</a></li>"); } else {
     * htmlPart.append("<li><a href=\"/front/").append(siteId)
     * .append("/search?keyword=").append(keyword)
     * .append("&pageNumber=").append(index)
     * .append("&pageSize=15").append(" \">").append(index)
     * .append("</a></li>"); } } if (totalPages - pageNum > 4) { int nums =
     * pageNum + 4 > totalPages ? totalPages : pageNum + 4;
     * htmlPart.append("<li><a href=\"/front/").append(siteId)
     * .append("/search?keyword=").append(keyword)
     * .append("&pageNumber=").append(nums).append("&pageSize=15")
     * .append(" \">···</a></li>"); } int next = pageNum + 1 > totalPages ?
     * totalPages : pageNum + 1;
     * htmlPart.append("<li><a href=\"/front/").append(siteId)
     * .append("/search?keyword=").append(keyword)
     * .append("&pageNumber=").append(next).append("&pageSize=15")
     * .append(" \">»</a></li>"); htmlPart.append("</li>"); return
     * htmlPart.toString(); }
     */

    /*
     * public static String buildPagination(NutMap params) { Page<CmsContent>
     * contentPage = (Page<CmsContent>) params .get("contentPage"); CmsSite site
     * = (CmsSite) params.get("site"); CmsCategory category = (CmsCategory)
     * params.get("category"); String siteId = site.getId(); String categoryId =
     * Lang.isEmpty(category) ? "" : category.getId(); int pageNum =
     * contentPage.getPageNo(); int totalPages = contentPage.getTotalPages();
     * int index = 1; int showPageNum = 9; StringBuilder htmlPart = new
     * StringBuilder("<ul class=\"pagination\">"); int pervious = (pageNum - 1)
     * > 1 ? pageNum - 1 : 1;
     * htmlPart.append("<li><a href=\"/front/").append(siteId).append("/")
     * .append(categoryId).append("/list/").append(pervious)
     * .append("\">«</a></li>"); if (pageNum - 4 > 1) { int num = pageNum - 4 >
     * totalPages ? totalPages : pageNum - 4;
     * htmlPart.append("<li><a href=\"/front/").append(siteId).append("/")
     * .append(categoryId).append("/list/").append(num)
     * .append("\">···</a></li>"); } if (pageNum >= 5 && totalPages - pageNum >=
     * 5) { index = pageNum - 4; showPageNum = pageNum + 4; } else if
     * (totalPages - pageNum <= 5) { index = pageNum - (8 - (totalPages -
     * pageNum)); index = index > 0 ? index : 1; showPageNum = totalPages; }
     * else if (totalPages == pageNum) { index = pageNum - 9; showPageNum =
     * totalPages; } for (; index <= showPageNum; index++) { if (pageNum ==
     * index) { htmlPart.append("<li><a class=\"active\" href=\"/front/")
     * .append(siteId).append("/").append(categoryId)
     * .append("/list/").append(index).append("\">")
     * .append(index).append("</a></li>"); } else {
     * htmlPart.append("<li><a href=\"/front/").append(siteId)
     * .append("/").append(categoryId).append("/list/")
     * .append(index).append("\">").append(index) .append("</a></li>"); } } if
     * (totalPages - pageNum > 4) { int nums = pageNum + 4 > totalPages ?
     * totalPages : pageNum + 4;
     * htmlPart.append("<li><a href=\"/front/").append(siteId).append("/")
     * .append(categoryId).append("/list/").append(nums)
     * .append("\">···</a></li>"); } int next = pageNum + 1 > totalPages ?
     * totalPages : pageNum + 1;
     * htmlPart.append("<li><a href=\"/front/").append(siteId).append("/")
     * .append(categoryId).append("/list/").append(next)
     * .append("\">»</a></li>"); htmlPart.append("</li>"); return
     * htmlPart.toString(); }
     */

    /*
     * public String buildContent(NutMap params) { Element element = (Element)
     * params.get("element"); CmsArticle curContent = (CmsArticle)
     * params.get("article"); String content =
     * Strings.isBlank(curContent.getContent()) ? "" : curContent.getContent();
     * StringBuilder htmlPart = new StringBuilder(); if
     * (element.hasClass("readContents")) {
     * htmlPart.append("<div class=\"readTitle\">");
     * htmlPart.append("<ul><li class=\"readL1\">")
     * .append(curContent.getTitle()) .append("</li><li class=\"readL9\">"); if
     * (Strings.isNotBlank(curContent.getSubTitle())) {
     * htmlPart.append(curContent.getSubTitle()); }
     * htmlPart.append("</li><li class=\"readL2\">"); htmlPart.append("发布时间:")
     * .append(DateUtil.getDate(curContent.getPublishAt()))
     * .append("&nbsp;&nbsp;&nbsp;录入人员:") .append(curContent.getPublisher())
     * .append("&nbsp;&nbsp;&nbsp;点击次数:") .append(curContent.getViewCount() +
     * 1); htmlPart.append("</li></ul></div>");
     * htmlPart.append("<div class=\"readContent\">")
     * .append(curContent.getContent()).append("</div>"); } if
     * (element.hasClass("detailContent")) {
     * htmlPart.append("<h4 class=\"heading\">")
     * .append(curContent.getTitle()).append("</h4>"); if
     * (Strings.isNotBlank(curContent.getSubTitle())) {
     * htmlPart.append("<h6 class=\"subTitle\">" + curContent.getSubTitle() +
     * "</h6>"); } htmlPart.append("<div class=\"time\">").append("<span>发布时间：")
     * .append(DateUtil.getDate(curContent.getPublishAt()))
     * .append("</span>").append("<span>录入人员：")
     * .append(curContent.getPublisher()).append("</span>")
     * .append("<span>总点击次数：") .append(curContent.getViewCount() + 1)
     * .append("</span></div>").append(content); } return htmlPart.toString(); }
     */

    protected <T> List<List<T>> splitList(List<T> list, int blockSize) {
        List<List<T>> lists = new ArrayList<List<T>>();
        if (blockSize == 1) {
            lists.add(list);
            return lists;
        }
        if (list != null && blockSize > 0) {
            int listSize = list.size();
            if (listSize <= blockSize) {
                lists.add(list);
                return lists;
            }
            int batchSize = listSize / blockSize;
            int remain = listSize % blockSize;
            for (int i = 0; i < batchSize; i++) {
                int fromIndex = i * blockSize;
                int toIndex = fromIndex + blockSize;
                lists.add(list.subList(fromIndex, toIndex));
            }
            if (remain > 0) {
                lists.add(list.subList(listSize - remain, listSize));
            }
        }
        return lists;
    }
}
