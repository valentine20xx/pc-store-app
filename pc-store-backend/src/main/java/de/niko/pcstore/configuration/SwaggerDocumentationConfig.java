package de.niko.pcstore.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocumentationConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        GroupedOpenApi.Builder groupedOpenApi = GroupedOpenApi.builder();
        groupedOpenApi.group("pc-store");
        groupedOpenApi.displayName("PC Store API");
        groupedOpenApi.  packagesToScan("de.niko.pcstore.controller.api");

        return groupedOpenApi.build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        Contact contact = new Contact();
        contact.setName("Niko");
        contact.setEmail("example@test.de");

        License license = new License();
        license.name("Apache 2.0");
        license.url("http://www.apache.org/licenses/LICENSE-2.0.html");

        Info info = new Info();
        info.title("PC Store REST Gateway");
        info.description("This is the API-definition for the basic data in the PC Store project");
        info.version("v1.0.0");
        info.license(license);
        info.contact(contact);

        OpenAPI openAPI = new OpenAPI();
        openAPI.info(info);

        return openAPI;
    }
}
