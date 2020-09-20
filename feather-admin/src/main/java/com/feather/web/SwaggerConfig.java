package com.feather.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.feather.common.config.Global;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2的接口配置
 * 
 * @author feather
 */
@Configuration
@ConditionalOnProperty(name = "feather.swagger.enable", havingValue = "true")
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    @Value("${feather.swagger.group}")
    private String group;

    @Value("${feather.swagger.version}")
    private String version;

    @Value("${feather.swagger.description}")
    private String description;

    @Value("${feather.swagger.package}")
    private String basePackage;

    @Value("${feather.swagger.path}")
    private String pathPattern;

    /**
     * 默认API
     */
    @Bean
    public Docket createDefaultApi() {
        // 摘要信息
        ApiInfo apiInfo = new ApiInfoBuilder().title("默认接口").version("版本号:" + Global.getVersion()).build();

        return new Docket(DocumentationType.SWAGGER_2)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo)
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.feather.system.controller"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    /**
     * 产品API
     */
    @Bean
    public Docket createProductApi() {
        String prdGroup = "产品接口";
        String prdVersion = "版本号:" + Global.getVersion();
        String prdPackage = "com.feather.prd.controller";

        ApiInfo apiInfo = new ApiInfoBuilder().title(prdGroup).version(prdVersion).build();

        return new Docket(DocumentationType.SWAGGER_2).groupName(prdGroup).apiInfo(apiInfo).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage(prdPackage)).paths(PathSelectors.any()).build();
    }

    /**
     * 业务API
     */
    @Bean
    @ConditionalOnExpression("!''.equals('${feather.swagger.group}') && !''.equals('${feather.swagger.package}')")
    public Docket createBusinessApi() {
        ApiInfo apiInfo = new ApiInfoBuilder().title(group).description(description).version(version).build();

        return new Docket(DocumentationType.SWAGGER_2).groupName(group).apiInfo(apiInfo).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(StringUtils.isEmpty(pathPattern) ? PathSelectors.any() : PathSelectors.ant(pathPattern)).build();
    }
}
