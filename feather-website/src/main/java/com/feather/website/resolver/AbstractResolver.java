package com.feather.website.resolver;

import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;

/**
 * @author nothing
 * @date 2019-11-05 15:08
 */
public class AbstractResolver implements Resolver{
    @Override
    public IModel navBar(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        return null;
    }

    @Override
    public IModel carousel(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        return null;
    }

    @Override
    public IModel topNews(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        return null;
    }

    @Override
    public IModel articleColumn(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        return null;
    }

    @Override
    public IModel multiArticleColumns(ApplicationContext applicationContext, IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        return null;
    }

}
