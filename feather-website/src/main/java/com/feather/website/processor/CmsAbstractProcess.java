package com.feather.website.processor;

import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

import com.feather.website.resolver.Resolver;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.IArticleService;
import com.feather.website.service.ISiteService;

/**
 * @author nothing
 * @date 2019-11-05 14:01
 */
public abstract class CmsAbstractProcess extends AbstractElementTagProcessor {
    /**
     * 主站解析类的包
     */
    public static final String RESOLVER_PACKAGE = "com.feather.website.resolver.";

    // 优先级
    protected static final int PRECEDENCE = 10000;

    protected ApplicationContext applicationContext;
    protected ISiteService siteService;
    protected IArticleColumnService articleColumnService;
    protected IArticleService articleService;

    protected Class<Resolver> clazz;

    public CmsAbstractProcess(String dialectPrefix, String tagName) {
        super(
                // 此处理器将仅应用于HTML模式
                TemplateMode.HTML,
                // 要应用于名称的匹配前缀
                dialectPrefix,
                // 标签名称：匹配此名称的特定标签
                tagName,
                // 将标签前缀应用于标签名称
                true,
                // 无属性名称：将通过标签名称匹配
                null,
                // 没有要应用于属性名称的前缀
                false,
                // 优先(内部方言自己的优先)
                PRECEDENCE);
    }

    /**
     * 处理自定义标签 DOM 结构
     *
     * @param context
     *            模板页上下文
     * @param tag
     *            待处理标签
     * @param structureHandler
     *            元素标签结构处理器
     */
    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
            IElementTagStructureHandler structureHandler) {
        IModelFactory modelFactory = context.getModelFactory();
        IModel model = modelFactory.createModel();

        model = addModelElement(model, context, tag, modelFactory);
        structureHandler.replaceWith(model, false);
    }

    protected IModel addModelElement(IModel model, ITemplateContext context, IProcessableElementTag tag,
            IModelFactory modelFactory) {
        applicationContext = SpringContextUtils.getApplicationContext(context);
        siteService = applicationContext.getBean(ISiteService.class);
        articleColumnService = applicationContext.getBean(IArticleColumnService.class);
        articleService = applicationContext.getBean(IArticleService.class);
        String siteAlias = tag.getAttributeValue("siteAlias");
        String method = tag.getAttributeValue("method");
        try {
            String className = RESOLVER_PACKAGE + Strings.upperFirst(siteAlias) + "Resolver";
            clazz = Lang.forName(className, Resolver.class);
            if (Lang.isNotEmpty(clazz)) {
                Mirror<Resolver> mirror = Mirror.me(clazz);
                Resolver resolver = Mirror.me(clazz).born();
                model = (IModel) mirror.invoke(resolver, method, applicationContext, model, context, tag, modelFactory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
}
