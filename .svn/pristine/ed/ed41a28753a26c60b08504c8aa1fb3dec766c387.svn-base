package com.feather.website.resolver;

import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;

/**
 * @author nothing
 * @date 2019-11-05 14:54
 */
public interface Resolver {

    /**
     * 解析导航栏
     *
     * @param applicationContext
     * @param model
     * @param context
     * @param tag
     * @param modelFactory
     * @return
     */
    public IModel navBar(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory);

    /**
     * 解析轮播图
     *
     * @param applicationContext
     * @param model
     * @param context
     * @param tag
     * @param modelFactory
     * @return
     */
    public IModel carousel(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory);

    /**
     * 右侧置顶消息栏目
     * @param applicationContext
     * @param model
     * @param context
     * @param tag
     * @param modelFactory
     * @return
     */
    public IModel topNews(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory);

    /**
     * 解析单个栏目
     * @param applicationContext
     * @param model
     * @param context
     * @param tag
     * @param modelFactory
     * @return
     */
    public IModel articleColumn(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory);

    /**
     * 解析复合栏目
     *
     * @param applicationContext
     * @param model
     * @param context
     * @param tag
     * @param modelFactory
     * @return
     */
    public IModel multiArticleColumns(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory);
}
