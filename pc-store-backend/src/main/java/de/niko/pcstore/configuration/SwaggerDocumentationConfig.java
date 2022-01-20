package de.niko.pcstore.configuration;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

@Configuration
public class SwaggerDocumentationConfig {
    @Bean
    public Docket customImplementation() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("PC Store REST Gateway")
                .description("This is the API-definition for the basic data in the PC Store project")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("", "", ""))
                .build();

        Tag internalOrderAPITag = new Tag(Tags.INTERNAL_ORDER_API_TAG, "API for manipulations with internal orders");

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("pc-store")
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.niko.pcstore.controller.api"))
                .build()
                .tags(internalOrderAPITag)
                .directModelSubstitute(LocalDate.class, Date.class)
                .directModelSubstitute(OffsetDateTime.class, Date.class)
                .directModelSubstitute(Timestamp.class, Date.class)
                .apiInfo(apiInfo);
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.LIST)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.METHOD)
                .showExtensions(false)
                .showCommonExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }
}
