package com.feather.cms.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.feather.cms.domain.CmsContent;
import com.feather.cms.service.ICmsContentService;

/**
 * @author feather
 */
@Configuration
public class CmsContentInterceptor extends HandlerInterceptorAdapter implements WebMvcConfigurer {
    private static final String CONTENT_PREFIX = CmsConstants.FRAME_PREFIX + "/content";

    @Autowired
    private ICmsContentService cmsContenteService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this).addPathPatterns(CONTENT_PREFIX + "/**");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();
        // String[] array = uri.split("/");
        // String domain = array[1];
        // String page = array.length >= 3 ? array[2] : null;

        int at = uri.indexOf(CONTENT_PREFIX);
        String path = uri.substring(at + CONTENT_PREFIX.length());
        String fileId = new Md5Hash(path).toHex().toString();
        CmsContent cmsContent = cmsContenteService.selectCmsContentById(fileId);
        if (cmsContent != null) {
            byte[] b = cmsContent.hasContent() ? cmsContent.getFileContent() : "".getBytes();
            response.getOutputStream().write(b);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Md5Hash("12345678901234567890").toHex().toString());
        String[] array = "/domain/page".split("/");
        for (String item : array) {
            System.out.println("==>" + item);
        }
    }
}