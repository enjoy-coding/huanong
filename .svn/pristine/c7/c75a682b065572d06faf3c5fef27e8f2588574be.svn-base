package com.feather.cms.config;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author feather
 */
@Configuration
public class CmsFrameConfig extends HandlerInterceptorAdapter implements WebMvcConfigurer {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this).addPathPatterns(CmsFrameConstants.FRAME_PREFIX + "/**");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();
        String[] array = uri.split("/");
        String domain = array[1];
        String page = array.length >= 3 ? array[2] : null;

        int at = uri.indexOf(CmsFrameConstants.FRAME_PREFIX);
        String key = uri.substring(at + CmsFrameConstants.FRAME_PREFIX.length());

        ValueOperations<String, byte[]> operations = redisTemplate.opsForValue();
        byte[] b = operations.get(key);
        if (b == null) {
            b = FileUtils.readFileToByteArray(new File("C:/Users/Administrator/Desktop/01.jpg"));
            operations.set(key, b);
        }
        response.getOutputStream().write(b);
        return false;
    }

    public static void main(String[] args) {
        String[] array = "/domain/page".split("/");
        for (String item : array) {
            System.out.println("==>" + item);
        }
    }
}