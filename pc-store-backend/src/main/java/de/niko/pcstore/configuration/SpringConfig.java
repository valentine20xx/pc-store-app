package de.niko.pcstore.configuration;

import de.niko.pcstore.dto.InternalOrderDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        Converter<String, InternalOrderDTO.Status> converter = new Converter<>() {
            @Override
            public InternalOrderDTO.Status convert(String source) {
                return InternalOrderDTO.Status.fromString(source.toUpperCase());
            }
        };

        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(converter);
            }
        };
    }

    @Bean
    public MessageCodesResolver myMessageCodesResolver() {
        return new MessageCodesResolver() {
            @Override
            public String[] resolveMessageCodes(String errorCode, String objectName) {
                return new String[]{
                        errorCode
                };
            }

            @Override
            public String[] resolveMessageCodes(String errorCode, String objectName, String field, Class<?> fieldType) {
                return new String[]{
                        errorCode
                };
            }
        };
    }
}
