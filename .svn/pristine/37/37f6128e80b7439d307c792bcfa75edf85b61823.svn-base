package com.feather.website.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.InvokingException;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.Stopwatch;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.website.domain.CmsSite;
import com.feather.website.domain.CmsTemplate;
import com.feather.website.domain.CmsTemplateAttr;
import com.feather.website.domain.CmsTemplateInst;
import com.feather.website.dto.ElementDto;
import com.feather.website.service.IArticleColumnService;
import com.feather.website.service.IArticleService;
import com.feather.website.service.IBuildService;
import com.feather.website.service.ISiteService;
import com.feather.website.service.ITemplateAttrService;
import com.feather.website.service.ITemplateInstService;
import com.feather.website.service.ITemplateService;
import com.feather.common.utils.PathUtil;

import fr.opensagres.poi.xwpf.converter.xhtml.internal.utils.StringEscapeUtils;

/**
 * @author nothing
 * @date 2019-11-15 8:56
 */
@Service
public class BuildServiceImpl implements IBuildService {
    ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final String TEMPLATE_PATH = PathUtil.getBaseReourcePath(BuildServiceImpl.class);

    @Autowired
    ISiteService siteService;
    @Autowired
    IArticleColumnService articleColumnService;
    @Autowired
    IArticleService articleService;
    @Autowired
    ITemplateService templateService;
    @Autowired
    ITemplateAttrService templateAttrService;
    @Autowired
    ITemplateInstService templateInstService;

    @Override
    public String build(Long siteId, CmsTemplate template, Long tplInstId, String block, String blockValue) {
        CmsSite site = siteService.findById(siteId);
        CmsTemplateInst templateInst = templateInstService.findById(tplInstId);
        List<CmsTemplateAttr> templateAttrs = templateAttrService.findByTplId(template.getId());

        if (!Lang.isEmpty(templateAttrs) && !Lang.isEmpty(templateInst) & Strings.isNotBlank(block)
                && Strings.isNotBlank(blockValue)) {
            updateTemplateInstValue(templateInst, block, blockValue);
            // 因为更新了value所以这里需要重新读取一次
            templateInst = templateInstService.findById(tplInstId);
        }
        return buildDyncmicHtml(site, template, templateInst, templateAttrs);
    }

    @Override
    public String buildDynamicHtmlToFront(Long tplInstId, NutMap params) throws ClassNotFoundException {
        String useHtml = params.getString("useHtml");
        CmsTemplate template = (CmsTemplate) params.get("template");
        CmsTemplateInst templateInst = templateInstService.findById(tplInstId);
        CmsSite site = (CmsSite) params.get("site");
        if (Lang.isEmpty(template)) {
            template = templateService.findById(templateInst.getTplId());
        }
        List<CmsTemplateAttr> templateAttrs = templateAttrService.findByTplId(template.getId());
        String dynamicHtml = buildDyncmicHtml(site, template, templateInst, false, templateAttrs, useHtml);
        Document doc = Jsoup.parse(dynamicHtml);
        /**
         * 查找静态方法元素
         */
        List<Element> elements = doc.select("div[staticMethod]");
        Mirror<?> mirror = Mirror.me(Class.forName(template.getClassName()));
        if (!Lang.isEmpty(elements) && elements.size() > 0) {
            for (Element element : elements) {
                String methodName = element.attr("staticMethod");
                // 这里为什么用put，是因为解析每个元素，都应该把element覆盖掉，不然会形成一个链表
                params.put("element", element);
                String htmlPart = (String) mirror.invoke(mirror.born(), methodName, params);
                element.html(htmlPart);
            }
        }
        // 更改标题, 前面的方法已经修改过标题，这里删除这个操作
        // modifyTitle(doc, site.getAlias());
        dynamicHtml = clearHtmlJsEditEventAndCss(doc);
        return dynamicHtml;
    }

    private String buildDyncmicHtml(CmsSite site, CmsTemplate template, CmsTemplateInst templateInst,
            List<CmsTemplateAttr> templateAttrs) {
        String useHtml = "index.html";
        boolean isEdit = true;
        return buildDyncmicHtml(site, template, templateInst, isEdit, templateAttrs, useHtml);
    }

    /**
     * 动态构建html
     *
     * @param site
     *            所属站点
     * @param template
     *            模板
     * @param templateInst
     *            模板实例
     * @param isEdit
     *            是否是编辑
     * @param templateAttrs
     *            模板属性
     * @param useHtml
     * @return
     */
    private String buildDyncmicHtml(CmsSite site, CmsTemplate template, CmsTemplateInst templateInst, boolean isEdit,
            List<CmsTemplateAttr> templateAttrs, String useHtml) {
        String htmlStr = "";
        String templateName = template.getName();
        Map<String, CmsTemplateAttr> templateAttrMap = new HashMap<>();
        String rootClassPath = PathUtil.getRootClassPath();
        String path = PathUtil.getPath(BuildServiceImpl.class);
        System.out.println(rootClassPath + "-----" + path);
        String serverModelIndexPath = TEMPLATE_PATH + MODEL + File.separator + templateName + File.separator + useHtml;

        File serverModelIndex = new File(serverModelIndexPath);

        if (serverModelIndex.exists()) {
            htmlStr = Files.read(serverModelIndex);
            Document doc = Jsoup.parse(htmlStr);
            List<Element> elements = doc.select("div[block]");
            if ((!Lang.isEmpty(elements) && elements.size() > 0) && !Lang.isEmpty(templateAttrs)
                    && templateAttrs.size() > 0) {
                try {
                    for (CmsTemplateAttr templateAttr : templateAttrs) {
                        templateAttrMap.put(templateAttr.getBlockName(), templateAttr);
                    }
                    buildEachBlock(template, templateInst, templateAttrMap, doc, elements);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 修改标题
            modifyTitle(doc, site.getAlias());
            htmlStr = StringEscapeUtils.escapeHtml(doc.html());
            htmlStr = htmlStr.replace("${obj.siteId}", String.valueOf(site.getId()))
                    .replace("${obj.tplId}", String.valueOf(template.getId()))
                    .replace("${obj.tplInstId}", String.valueOf(templateInst.getId()))
                    .replace("${obj.isEdit}", Boolean.toString(isEdit));
        }
        return htmlStr;
    }

    private void updateTemplateInstValue(CmsTemplateInst templateInst, String block, String blockValue) {
        Map<String, String> valueMap = new HashMap<String, String>();
        Long id = templateInst.getId();
        String value = templateInst.getValue();
        if (Strings.isNotBlank(value)) {
            valueMap = Json.fromJsonAsMap(String.class, value);
        }
        valueMap.put(block, blockValue);
        templateInstService.updateTemplateInstValue(id, Json.toJson(valueMap));
    }

    /**
     * 修改标题
     *
     * @param doc
     * @param val
     */
    private void modifyTitle(Document doc, String val) {
        List<Element> elements = doc.getElementsByTag("title");
        if (!Lang.isEmpty(elements) && elements.size() > 0) {
            for (Element element : elements) {
                element.html(val);
            }
        }
    }

    /**
     * 根据各个模块构建HTML， 根据的是templateInst存储的值对各个模块进行替换
     *
     * @param template
     * @param templateInst
     * @param templateAttrMap
     * @param doc
     * @param elements
     * @throws ClassNotFoundException
     */
    private void buildEachBlock(CmsTemplate template, CmsTemplateInst templateInst,
            Map<String, CmsTemplateAttr> templateAttrMap, Document doc, List<Element> elements) throws Exception {
        String className = template.getClassName();
        writeModelIfNotExist(className, template.getClassInfo());
        Mirror<?> mirror = Mirror.me(Class.forName(className));
        String value = templateInst.getValue();
        Long siteId = templateInst.getSiteId();
        CmsSite site = siteService.findById(siteId);
        if (Strings.isNotBlank(value) && !Lang.isEmpty(site)) {
            Map<String, String> jsonMap = Json.fromJsonAsMap(String.class, value);
            List<Future<ElementDto>> list = new ArrayList<>();
            for (Element element : elements) {
                Future<ElementDto> future = es
                        .submit(new BuildBlockTask(site, element, templateAttrMap, jsonMap, mirror));
                if (!Lang.isEmpty(future)) {
                    list.add(future);
                }
            }
            Stopwatch sw = Stopwatch.begin();
            for (Future<ElementDto> future : list) {
                while (true) {
                    if (future.isDone() && !future.isCancelled()) {
                        try {
                            ElementDto elementDto = future.get();
                            if (!Lang.isEmpty(elementDto)) {
                                Element element = elementDto.getElement();
                                String htmlPart = elementDto.getHtmlPart();
                                if (!Lang.isEmpty(element) && Strings.isNotBlank(htmlPart)) {
                                    element.html(htmlPart);
                                }
                            }
                            break;
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            sw.stop();
            // log.error("-------构建所有模块总耗时为-------" + sw.du() + "--毫秒--");
        }
    }

    class BuildBlockTask implements Callable<ElementDto> {
        CmsSite site;
        Element element;
        Map<String, String> jsonMap;
        Map<String, CmsTemplateAttr> templateAttrMap;
        Mirror<?> mirror;

        public BuildBlockTask(CmsSite site, Element element, Map<String, CmsTemplateAttr> templateAttrMap,
                Map<String, String> jsonMap, Mirror<?> mirror) {
            this.site = site;
            this.element = element;
            this.templateAttrMap = templateAttrMap;
            this.jsonMap = jsonMap;
            this.mirror = mirror;
        }

        @Override
        public ElementDto call() {
            String htmlPart = "";
            String blockName = element.attr("block");
            // 含有值和对应的templateAttr
            if (jsonMap.containsKey(blockName) && templateAttrMap.containsKey(blockName)) {
                try {
                    Stopwatch sw = Stopwatch.begin();
                    String blockValue = jsonMap.get(blockName);
                    CmsTemplateAttr templateAttr = templateAttrMap.get(blockName);
                    String methodName = templateAttr.getMethod();
                    NutMap params = new NutMap();
                    params.addv("site", site);
                    params.addv("element", element);
                    params.addv("blockValue", blockValue);
                    params.addv("templateAttr", templateAttr);
                    params.addv("siteService", siteService);
                    params.addv("articleColumnService", articleColumnService);
                    params.addv("articleService", articleService);
                    htmlPart = (String) mirror.invoke(mirror.born(), methodName, params);
                    sw.stop();
                    /*
                     * log.error("----线程名为--" + Thread.currentThread().getName()
                     * + "----构建block---" + blockName + " 耗时-------" + sw.du() +
                     * "--毫秒--");
                     */
                } catch (InvokingException e) {
                    e.printStackTrace();
                }
                return new ElementDto(element, htmlPart);
            }
            return null;
        }
    }

    /**
     * 如果模板不存在，从数据库读取模板内容并写入文件中
     *
     * @param className
     * @param classInfo
     */
    private void writeModelIfNotExist(String className, byte[] classInfo) throws IOException {
        className = className.replace(".", "/");
        String fileName = className + CLASS;
        File file = new File(fileName);
        if (file.exists()) {
            FileUtils.writeByteArrayToFile(file, classInfo);
        }
    }

    private String clearHtmlJsEditEventAndCss(Document doc) {
        List<Element> elements = doc.select("[needDeleteAfterEdit=true]");
        if (!Lang.isEmpty(elements) && elements.size() > 0) {
            for (Element element : elements) {
                if (!Lang.isEmpty(element)) {
                    element.remove();
                }
            }
        }
        elements = doc.select("div.editBox");
        if (!Lang.isEmpty(elements) && elements.size() > 0) {
            for (Element element : elements) {
                if (!Lang.isEmpty(element)) {
                    element.removeClass("editBox");
                }
            }
        }
        return doc.html();
    }
}