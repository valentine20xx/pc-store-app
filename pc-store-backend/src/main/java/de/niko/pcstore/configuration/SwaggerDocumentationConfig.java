package de.niko.pcstore.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocumentationConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        GroupedOpenApi.Builder groupedOpenApi = GroupedOpenApi.builder();
        groupedOpenApi.group("pc-store");
        groupedOpenApi.displayName("PC Store API");
        groupedOpenApi.packagesToScan("de.niko.pcstore.controller.api");

        return groupedOpenApi.build();
    }

    @Bean
    public OpenAPI openAPI() {
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

        OpenAPI openAPI = new OpenAPI(SpecVersion.V31);
        openAPI.info(info);

        Components components = new Components();
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setName(Tags.SECURITY_SCHEME_NAME);
        securityScheme.setScheme("bearer");
        securityScheme.setBearerFormat("JWT");
        securityScheme.setType(SecurityScheme.Type.OAUTH2);
        securityScheme.setIn(SecurityScheme.In.HEADER);
        OAuthFlows oAuthFlows = new OAuthFlows();
        OAuthFlow oAuthFlow = new OAuthFlow();
        oAuthFlow.setTokenUrl("http://localhost:8180/realms/master/protocol/openid-connect/token");
        oAuthFlow.setAuthorizationUrl(" http://localhost:8180/realms/master/protocol/openid-connect/auth");
        oAuthFlows.setAuthorizationCode(oAuthFlow);
        securityScheme.setFlows(oAuthFlows);
        components.addSecuritySchemes(Tags.SECURITY_SCHEME_NAME, securityScheme);
        openAPI.setComponents(components);

        return openAPI;
    }
}
