package cn.edu.njuit.api.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(
                new Info()
                        .title("api demo")
                        .contact(new Contact().name("mqxu").email("mqxu.@gmail.com"))
                        .version("1.0")
                        .description("api demo 接口文档")
                        .license(new License().name("Apache 2.0").url("http://doc.xiaominfo.com"))
        );
    }
}
