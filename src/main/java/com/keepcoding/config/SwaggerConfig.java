package com.keepcoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket api() {
    	return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.keepcoding.web.api"))
                .paths(PathSelectors.any())
                .build();
    }
    
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
    			.title("Documentación de la protectora de Animales")
    			.description("Informacion completa sobre la API REST para consumo de clientes de todo tipo")
    			.termsOfServiceUrl("En contrucción")
    			.contact(new Contact("Laura Barrero Sánchez", "laura.barrero@smarting.es", null))
    			.version("1.0")
    			.build();
    }
}
