package com.feather.website.processor;

import com.feather.website.domain.CmsArticle;
import com.feather.website.domain.CmsArticleColumn;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.IArticleService;
import com.feather.website.service.ISiteService;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.*;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

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
public class CmsCarouselProcessor extends CmsAbstractProcess {
    // 标签名
    private static final String TAG_NAME = "carousel";

    public CmsCarouselProcessor(String dialectPrefix){
        super(dialectPrefix, TAG_NAME);
    }

    /**
     * <div id="fsD1" class="focus">
     * <div id="D1pic1" class="fPic">
     * <div class="fcon" style="display: none;">
     * <a target="_blank" href="http://www.lanrentuku.com/"><img src="../img/ad_r2_c4.jpg" style="opacity: 1; "></a>
     * <span class="shadow"><a target="_blank" href="#">红三代叶明子太庙办盛典 白色羽毛装高贵动人</a></span>
     * </div>
     *
     * <div class="fcon" style="display: none;">
     * <a target="_blank" href="http://www.lanrentuku.com/"><img src="../img/ad_r2_c4.jpg" style="opacity: 1; "></a>
     * <span class="shadow"><a target="_blank" href="#">佟大为登封面表情搞怪 成熟男人也是天真男孩</a></span>
     * </div>
     *
     * <div class="fcon" style="display: none;">
     * <a target="_blank" href="http://www.lanrentuku.com/"><img src="../img/ad_r2_c4.jpg" style="opacity: 1; "></a>
     * <span class="shadow"><a target="_blank" href="#">21岁出柜超模巴厘自曝搞笑全裸照</a></span>
     * </div>
     *
     * <div class="fcon" style="display: none;">
     * <a target="_blank" href="http://www.lanrentuku.com/"><img src="../img/ad_r2_c4.jpg" style="opacity: 1; "></a>
     * <span class="shadow"><a target="_blank" href="#">金喜善出道21年 皮肤白皙越长越“嫩”！</a></span>
     * </div>
     * </div>
     * <div class="fbg">
     * <div class="D1fBt" id="D1fBt">
     * <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>1</i></a>
     * <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>2</i></a>
     * <a href="javascript:void(0)" hidefocus="true" target="_self" class="current"><i>3</i></a>
     * <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>4</i></a>
     * </div>
     * </div>
     * </div>
     *
     * @param model
     * @param context
     * @param tag
     * @param modelFactory
     * @return
     */
    @Override
    protected IModel addModelElement(IModel model, ITemplateContext context, IProcessableElementTag tag, IModelFactory modelFactory) {
        return super.addModelElement(model, context, tag, modelFactory);
    }
}