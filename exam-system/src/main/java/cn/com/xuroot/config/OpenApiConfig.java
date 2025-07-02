package cn.com.xuroot.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "考试系统 API", description = "用户模块接口文档", version = "v1"))
public class OpenApiConfig {
}
