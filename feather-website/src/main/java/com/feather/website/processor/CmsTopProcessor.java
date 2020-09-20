package com.feather.website.processor;

/**
 * @author nothing
 * @date 2019-11-04 17:57
 */
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * 置顶信息标签
 * <p>Title: CmsDictProcessor</p>
 * <p>Description: </p>
 *
 * @author Lusifer
 * @version 1.0.0
 * @date 2018/3/4 10:52
 */
public class CmsTopProcessor extends CmsAbstractProcess {

    // 标签名
    private static final String TAG_NAME = "top-news";

    public CmsTopProcessor(String dialectPrefix) {
        super(dialectPrefix, TAG_NAME);
    }

    @Override
    protected IModel addModelElement(IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        model = super.addModelElement(model, context, tag, modelFactory);
        return model;
    }
}