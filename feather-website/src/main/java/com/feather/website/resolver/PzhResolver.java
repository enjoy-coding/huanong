package com.feather.website.resolver;

import com.feather.website.domain.CmsArticle;
import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.IArticleService;
import com.feather.website.service.ISiteService;
import com.feather.common.utils.DateUtils;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.*;
import org.thymeleaf.spring5.context.SpringContextUtils;

import java.util.Date;
import java.util.List;

/**
 * @author nothing
 * @date 2019-11-05 14:46
 * 攀枝花模板解析器
 */
public class PzhResolver extends AbstractResolver {

    @Override
    public IModel navBar(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        IArticleColumnService articleColumnService = applicationContext.getBean(IArticleColumnService.class);

        String articleColumnAlias = tag.getAttributeValue("articleColumnAlias");
        List<CmsArticleColumn> articleColumns = articleColumnService.findByParentAlias(articleColumnAlias);

        model.add(modelFactory.createOpenElementTag("ul"));
        if (Lang.isNotEmpty(articleColumns) && articleColumns.size() > 0) {
            for (CmsArticleColumn articleColumn : articleColumns) {
                String articleColumnName = articleColumn.getName();
                IOpenElementTag liOpenElementTag = modelFactory.createOpenElementTag("li");
                model.add(liOpenElementTag);
                IOpenElementTag aOpenElementTag = modelFactory.createOpenElementTag("a");
                modelFactory.setAttribute(aOpenElementTag, "href", "#");
                model.add(aOpenElementTag);
                model.add(modelFactory.createText(articleColumnName));
                ICloseElementTag aCloseElementTag = modelFactory.createCloseElementTag("a");
                model.add(aCloseElementTag);
                ICloseElementTag liCloseElementTag = modelFactory.createCloseElementTag("li");
                model.add(liCloseElementTag);
            }
        }
        model.add(modelFactory.createCloseElementTag("ul"));
        return model;
    }

    @Override
    public IModel carousel(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {

        final IArticleService articleService = applicationContext.getBean(IArticleService.class);

        String articleColumnAlias = tag.getAttributeValue("articleColumnAlias");
        Integer pageSize = Strings.isBlank(tag.getAttributeValue("pageSize")) ? 4 : Integer.parseInt(tag.getAttributeValue("pageSize"));

        model.add(modelFactory.createText("<div id=\"slider-wrap\"><ul id=\"slider\">"));
        List<CmsArticle> articles = articleService.findByArticleColumnAlias(articleColumnAlias, pageSize);
        if (Lang.isNotEmpty(articles) && articles.size() > 0) {
            for (CmsArticle article : articles) {
                model.add(modelFactory.createText("<li data-color=\"#e74c3c\"><image style=\"width:100%;height:100%\" src=\"" + article.getCoverImage() + "\"></image></li>"));
            }
        }

        model.add(modelFactory.createText("</ul>"));
        model.add(modelFactory.createText("<div class=\"btns\" id=\"next\"><i class=\"fa fa-arrow-right\"></i></div><div class=\"btns\" id=\"previous\"><i class=\"fa fa-arrow-left\"></i></div><div id=\"counter\"></div><div id=\"pagination-wrap\"><ul></ul></div></div>"));
        return model;
    }

    /**
     * <div class="w453 fr  overflow">
     * <div class="w435 overflow mt10">
     * <div class="w435_top">
     * <h5>分局动态</h5><span><a href="#">更多></a></span>
     * </div>
     * <div class="winf_bot w100 overflow clearfix">
     *
     * <dl>
     * <dt>
     * <p>
     * 近日<br>要闻<br><em>11-27</em>
     * </p>
     * </dt>
     * <dd>
     * <h5><a href="#">攀枝花市政法系统学习贯彻党的十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></h5>
     * <p>全市政法系统学习贯彻党的十九大和习近平总书记来川
     * 视察重要讲市局大礼堂...<a href="#">[查看详情]</a></p>
     * </dd>
     * </dl>
     * <ul>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * </ul>
     *
     *
     * </div>
     * </div>
     * </div>
     *
     * @param applicationContext
     * @param model
     * @param context
     * @param tag
     * @param modelFactory
     * @return
     */
    @Override
    public IModel topNews(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        final IArticleColumnService articleColumnService = applicationContext.getBean(IArticleColumnService.class);
        final IArticleService articleService = applicationContext.getBean(IArticleService.class);

        String classStr = tag.getAttributeValue("class");
        String articleColumnAlias = tag.getAttributeValue("articleColumnAlias");
        Integer pageSize = Strings.isBlank(tag.getAttributeValue("pageSize")) ? 6 : Integer.parseInt(tag.getAttributeValue("pageSize"));

        CmsArticleColumn articleColumn = articleColumnService.findByAlias(articleColumnAlias);
        if (Lang.isNotEmpty(articleColumn)) {
            if (Strings.isNotBlank(classStr)) {
                model.add(modelFactory.createText(" <div class=\"" + classStr + "\">"));
            } else {
                model.add(modelFactory.createText(" <div class=\"w655 overflow mt10\">"));
            }
            StringBuffer headerText = new StringBuffer();
            List<CmsArticle> articles = articleService.findByArticleColumnAlias(articleColumnAlias, pageSize);
            if (Lang.isNotEmpty(articles) && articles.size() > 0) {
                CmsArticle mainArticle = articles.get(0);
                headerText.append("<div class=\"w655_top\"><h5>").append(articleColumn.getName()).append("</h5><span><a href=\"#\">更多></a></span></div><div class=\"winf_bot w100 overflow clearfix\">");
                headerText.append("<dl><dt><p>近日<br>要闻<br><em>").append(DateUtils.parseDateToStr(DateUtils.MM_DD, mainArticle.getPublishAt())).append("</em></p></dt><dd><h5><a href=\"#\">").append(mainArticle.getTitle()).append("</a></h5><p>").append(mainArticle.getTitle()).append("<a href=\"#\">[查看详情]</a></p></dd></dl>");
                headerText.append("<ul>");
                if (articles.size() > 1) {
                    for (int i = 1; i < articles.size(); i++) {
                        CmsArticle article = articles.get(i);
                        String title = article.getTitle();
                        String publishAt = DateUtils.parseDateToStr(DateUtils.MM_DD, article.getPublishAt());
                        headerText.append(" <li> <ol><a href=\"#\">").append(title).append("</a> <span>").append(publishAt).append("</span></ol></li>");
                    }
                }
                headerText.append("</ul></div>");
                model.add(modelFactory.createText(headerText));
            }
            model.add(modelFactory.createText("</div>"));
        }
        return model;
    }

    @Override
    public IModel articleColumn(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        final ISiteService siteService = applicationContext.getBean(ISiteService.class);
        final IArticleColumnService articleColumnService = applicationContext.getBean(IArticleColumnService.class);
        final IArticleService articleService = applicationContext.getBean(IArticleService.class);

        String classStr = tag.getAttributeValue("class");
        String articleColumnAlias = tag.getAttributeValue("articleColumnAlias");
        Integer pageSize = Strings.isBlank(tag.getAttributeValue("pageSize")) ? 6 : Integer.parseInt(tag.getAttributeValue("pageSize"));
        CmsArticleColumn articleColumn = articleColumnService.findByAlias(articleColumnAlias);
        if (Lang.isNotEmpty(articleColumn)) {
            if (Strings.isNotBlank(classStr)) {
                model.add(modelFactory.createText(" <div class=\"" + classStr + "\">"));
            } else {
                model.add(modelFactory.createText(" <div class=\"w392 overflow fl\">"));
            }
            StringBuffer headerText = new StringBuffer();
            headerText.append("<div class=\"w392_top overflow\">").append(" <h5>").append(articleColumn.getName()).append("</h5><span><a href=\"#\">更多></a></span></div>");
            model.add(modelFactory.createText(headerText));
            model.add(modelFactory.createText("<div class=\"w392_bot overflow\"><ul>"));
            List<CmsArticle> articles = articleService.findByArticleColumnAlias(articleColumnAlias, pageSize);
            if (Lang.isNotEmpty(articles) && articles.size() > 0) {
                for (CmsArticle article : articles) {
                    StringBuffer infoText = new StringBuffer();
                    Date publishAt = article.getCreateTime();
                    infoText.append(" <li><ol><a href=\"#\">").append(article.getTitle()).append("</a></ol>")
                            .append("<span>[").append(DateUtils.parseDateToStr(DateUtils.MM_DD, publishAt)).append("]</span></li>");
                    model.add(modelFactory.createText(infoText));
                }
            } else {

            }
            model.add(modelFactory.createText("</ul></div></div>"));

        }
        return model;
    }

    /**
     * <div class="w392 overflow fl ml10">
     * <div id="Tab63" >
     * <div class="Menubox63">
     * <ul>
     * <h5 style="width:124px">警情分析</h5>
     * <li id="three1" onMouseOver="setTab('three',1,3)" class="hover">每日</li>
     * <li id="three2" onMouseOver="setTab('three',2,3)" class="">每周 </li>
     * <li id="three3" onMouseOver="setTab('three',3,3)" class="" style="background:none">重点分析 </li>
     * <span><a href="#">更多></a></span>
     * </ul>
     * </div>
     * <div class="Contentbox63">
     * <div id="con_three_1" class="hover" style="display: block;">
     * <div class="w392_bot overflow">
     * <ul>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * </ul>
     * </div>
     * </div>
     * <div id="con_three_2" style="display: none;">
     * <div class="w392_bot overflow">
     * <ul>
     * <li>
     * <ol><a href="#">12市政法r系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * </ul>
     * </div>
     * </div>
     * <div id="con_three_3" style="display: none;">
     * <div class="w392_bot overflow">
     * <ul>
     * <li>
     * <ol><a href="#">12市政法系r统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * <li>
     * <ol><a href="#">12市政法系统12十九大和习攀枝花市政法系统学习贯彻党的十九大和习</a></ol>
     * <span>[01-20]</span>
     * </li>
     * </ul>
     * </div>
     * </div>
     * </div>
     * </div>
     *
     * </div>
     *
     * @param applicationContext
     * @param model
     * @param context
     * @param tag
     * @param modelFactory
     * @return
     */
    @Override
    public IModel multiArticleColumns(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        IArticleService articleService = applicationContext.getBean(IArticleService.class);
        IArticleColumnService articleColumnService = applicationContext.getBean(IArticleColumnService.class);

        String classStr = tag.getAttributeValue("class");
        String articleColumnAlias = tag.getAttributeValue("articleColumnAlias");
        Integer pageSize = Strings.isBlank(tag.getAttributeValue("pageSize")) ? 6 : Integer.parseInt(tag.getAttributeValue("pageSize"));
        CmsArticleColumn articleColumn = articleColumnService.findByAlias(articleColumnAlias);
        List<CmsArticleColumn> articleColumns = articleColumnService.findByParentAlias(articleColumnAlias);

        if (Lang.isNotEmpty(articleColumn)) {
            String articleColumnName = articleColumn.getName();
            if (Strings.isNotBlank(classStr)) {
                model.add(modelFactory.createText(" <div class=\"" + classStr + "\">"));
            } else {
                model.add(modelFactory.createText(" <div class=\"w392 overflow fl ml10\">"));
            }

            model.add(modelFactory.createText("<div id=\"Tab" + 63 + "\" ><div class=\"Menubox" + 63 + "\"><ul>"));
            model.add(modelFactory.createText("<h5 style=\"width:124px\">" + articleColumn.getName() + "</h5>"));
            //构建子栏目
            if (Lang.isNotEmpty(articleColumns) && articleColumns.size() > 0) {
                int len = articleColumns.size();
                for (int i = 0; i < len; i++) {
                    CmsArticleColumn aColumn = articleColumns.get(i);
                    if (i == 0) {
                        model.add(modelFactory.createText("<li id=\"" + articleColumnAlias + (i + 1) + "\" onMouseOver=\"setTab('" + articleColumnAlias + "'," + (i + 1) + "," + len + ")\" class=\"hover\">" + aColumn.getName() + "</li>"));
                    } else if (i == len - 1) {
                        model.add(modelFactory.createText("<li id=\"" + articleColumnAlias + (i + 1) + "\" onMouseOver=\"setTab('" + articleColumnAlias + "'," + (i + 1) + "," + len + ")\" class=\"\" style=\"background:none\">" + aColumn.getName() + "</li>"));
                    } else {
                        model.add(modelFactory.createText("<li id=\"" + articleColumnAlias + (i + 1) + "\" onMouseOver=\"setTab('" + articleColumnAlias + "'," + (i + 1) + "," + len + ")\" class=\"\">" + aColumn.getName() + "</li>"));
                    }
                }
            }
            model.add(modelFactory.createText("<span><a href=\"#\">更多></a></span></ul></div>"));
            model.add(modelFactory.createText("<div class=\"Contentbox63\">"));

            if (Lang.isNotEmpty(articleColumns) && articleColumns.size() > 0) {
                int len = articleColumns.size();
                for (int i = 0; i < len; i++) {
                    CmsArticleColumn aColumn = articleColumns.get(i);
                    if (i == 0) {
                        model.add(modelFactory.createText("<div id=\"con_" + articleColumnAlias + "_" + (i + 1) + "\" class=\"hover\" style=\"display: block;\"><div class=\"w392_bot overflow\"><ul>"));
                    } else {
                        model.add(modelFactory.createText("<div id=\"con_" + articleColumnAlias + "_" + (i + 1) + "\" class=\"hover\" style=\"display: none;\"><div class=\"w392_bot overflow\"><ul>"));
                    }
                    List<CmsArticle> articles = articleService.findByArticleColumnId(aColumn.getId(), pageSize);
                    if (Lang.isNotEmpty(articles) && articles.size() > 0) {
                        for (CmsArticle article : articles) {
                            model.add(modelFactory.createText("<li> <ol><a href=\"#\">" + article.getTitle() + "</a></ol> <span>[01-20]</span></li>"));
                        }

                    }
                    model.add(modelFactory.createText("</ul></div></div>"));
                }
            }
            model.add(modelFactory.createText("</div></div></div>"));
        }
        return model;
    }
}
