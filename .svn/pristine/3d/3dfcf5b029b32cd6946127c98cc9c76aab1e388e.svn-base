package com.feather.website.processor;

/**
 * @author nothing
 * @date 2019-11-04 17:57
 */

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
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Date;
import java.util.List;

/**
 * 自定义标签
 * <p>Title: CmsDictProcessor</p>
 * <p>Description: </p>
 *
 * @author Lusifer
 * @version 1.0.0
 * @date 2018/3/4 10:52
 */
public class CmsMultiArticleColumnProcessor extends CmsAbstractProcess {

    // 标签名
    private static final String TAG_NAME = "multi-article-column";


    public CmsMultiArticleColumnProcessor(String dialectPrefix){
        super(dialectPrefix, TAG_NAME);
    }

    @Override
    protected IModel addModelElement(IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        model = super.addModelElement(model, context, tag, modelFactory);
        return model;
    }
}