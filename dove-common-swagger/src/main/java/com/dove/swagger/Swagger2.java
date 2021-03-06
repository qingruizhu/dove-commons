package com.dove.swagger;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2019-12-10 09:49
 */
//@Configuration
//@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.dove"))
                .paths(PathSelectors.any())
                .build();
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
//                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                .title("API【PPM】")
                //创建人
//                .contact(new Contact("qingruizhu", "http://www.baidu.com", "zqr99655@163.com"))
                .contact(new Contact("qingruizhu", "https://mp.csdn.net/postedit/97941666", "zqr99655@163.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }
}