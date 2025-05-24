package com.pizi.tools.model.view.cofing;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


/**
 * @program: MyProject
 * @description: 业务接口层
 * @author: 痞子
 * @create: 2023-03-24 21:36:29
 */
@Configuration //标记这是配置
@EnableSwagger2 //启用Swagger
public class SwaggerConfig {
    //配置swagger的Docket的bean实例
    private static final String DEFAULT_PATH = "/api";

    //配置swagger的Docket的bean实例

    //    用户相关的swagger配置
    @Bean
    public Docket userDocket(Environment environment) {
        //设置显示的swagger环境
        Profiles profiles = Profiles.of("dev");
        //通过 environment.acceptsProfiles(profiles)判断是否处在自己设定的生产环境中
        boolean flag = environment.acceptsProfiles(profiles);
        //添加head参数end
        Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName("用户相关模块").useDefaultResponseMessages(false)
                //是否开启
                .enable(true).select()
                //为当前包路径
                .apis(scanBasePackage("com.pizi.tools.controller"))

                .paths(PathSelectors.regex("^(?!login).*$")).build().securitySchemes(securitySchemes()).securityContexts(securityContexts()).apiInfo(webApiInfo());
        docket.apiInfo(webApiInfo());
        //判断是否为生产环境
        docket.enable(flag);
        return docket;
    }

    //    测试swagger
//    续设置唯一组名，以防报错
    @Bean
    public Docket testDocket(Environment environment) {
        //设置显示的swagger环境
        Profiles profiles = Profiles.of("dev");
        //通过 environment.acceptsProfiles(profiles)判断是否处在自己设定的生产环境中
        boolean flag = environment.acceptsProfiles(profiles);
        //添加head参数end
        Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName("测试").useDefaultResponseMessages(false)
                //是否开启
                .enable(true).select()
                //为当前包路径
                .apis(scanBasePackage("com.pizi.tools.controller"))

                .paths(PathSelectors.regex("^(?!login).*$")).build().securitySchemes(securitySchemes()).securityContexts(securityContexts()).apiInfo(webApiInfo());
        docket.apiInfo(webApiInfo());
        //判断是否为生产环境
        docket.enable(flag);
        return docket;
    }


    public static Predicate scanBasePackage(final String basePackage) {
        if (StringUtils.isBlank(basePackage)) {
            throw new NullPointerException("basePackage不能为空，多个包扫描使用;分隔");
        }

        String[] controllerPack = basePackage.split(";");

        Predicate predicate = null;

        for (int i = controllerPack.length - 1; i >= 0; i--) {
            String strBasePackage = controllerPack[i];

            if (StringUtils.isNotBlank(strBasePackage)) {
                Predicate secPredicate = RequestHandlerSelectors.basePackage(strBasePackage);

                predicate = predicate == null ? secPredicate : Predicates.or(secPredicate, predicate);

            }

        }

        if (predicate == null) {
            throw new NullPointerException("basePackage配置不正确，多个包扫描使用;分隔");
        }

        return predicate;

    }

    private List<ApiKey> securitySchemes() {
        return newArrayList(new ApiKey("Authorization", "Authorization", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return newArrayList(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("^(?!login).*$")).build());
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("Authorization", authorizationScopes));
    }

    //配置swagger信息=apiInfo
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder().contact(new Contact("CoderMy", null, null)).title("痞子的工具包").description("本文档为痞子的继承集成工具包").version("1.0.0").build();
    }

    Contact DEFAULT_CONTACT = new Contact("痞子", "http://localhost:8082/swagger-ui.html", "sedatezhang@163.com");
}