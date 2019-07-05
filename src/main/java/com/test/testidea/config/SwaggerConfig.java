package com.test.testidea.config;

import com.test.testidea.constant.Mock;
import com.test.testidea.secruity.JwtUtils;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Config
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/11 19:13
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return api("default", PathSelectors.any()::apply);
    }

    private Docket api(String group, Predicate<String> paths) {
        Parameter tokenHeaderParam = new ParameterBuilder()
                .name(JwtUtils.TOKEN_HEADER)
                .description("Token")
                .defaultValue(Mock.TOKEN)
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true)
                .build();

        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message("请求成功").build());
        responseMessages.add(new ResponseMessageBuilder().code(203).message("认证失败").build());
        responseMessages.add(new ResponseMessageBuilder().code(400).message("请求失败").build());
        responseMessages.add(new ResponseMessageBuilder().code(401).message("鉴权失败").build());
        responseMessages.add(new ResponseMessageBuilder().code(403).message("无权访问").build());
        responseMessages.add(new ResponseMessageBuilder().code(405).message("不支持的请求类型").build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message("服务器异常").build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(group)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalOperationParameters(Collections.singletonList(tokenHeaderParam))
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(paths::test)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger UI Description.")
                .description("Swagger UI Description.")
                .version("1.0")
                .build();
    }

}
