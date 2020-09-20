package com.feather.website.dialect;

import com.feather.website.processor.*;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashSet;
import java.util.Set;

/**
 * Thymeleaf 方言：系统用
 * <p>Title: CmsDialect</p>
 * <p>Description: </p>
 *
 * @author Lusifer
 * @version 1.0.0
 * @date 2018/3/4 9:34
 */

public class CmsDialect extends AbstractProcessorDialect {
    // 定义方言名称
    private static final String DIALECT_NAME = "Cms Dialect";

    public CmsDialect() {
        // 设置自定义方言与“方言处理器”优先级相同
        super(DIALECT_NAME, "cms", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    /**
     * 元素处理器
     * @param dialectPrefix 方言前缀
     * @return
     */
    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = new HashSet<IProcessor>();

        // 添加自定义标签处理器
        processors.add(new CmsNavBarProcessor(dialectPrefix));
        processors.add(new CmsCarouselProcessor(dialectPrefix));
        processors.add(new CmsTopProcessor(dialectPrefix));
        processors.add(new CmsArticleColumnsProcessor(dialectPrefix));
        processors.add(new CmsArticleColumnProcessor(dialectPrefix));
        processors.add(new CmsMultiArticleColumnProcessor(dialectPrefix));
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }
}