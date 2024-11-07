package tn.esprit.spring.kaddem;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration

public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(infoAPI());

    }
    public Info infoAPI() {
        return new Info().title("kaddem").description("TP").contact(contactAPI());

    }
    public Contact contactAPI() {
        Contact contact = new Contact().name("malek").email("malek.khelil@esprit.tn").url("https://www.linkedin.com/in/malek-khelil/");
        return contact;
    }
    @Bean
    public GroupedOpenApi productPublicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**/**")
                .pathsToExclude("**")
                .build();
    }


}