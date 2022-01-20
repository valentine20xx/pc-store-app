package de.niko.pcstore.configuration;

import de.niko.pcstore.dto.InternalOrderDTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }

    public static class StringToEnumConverter implements Converter<String, InternalOrderDTO.Status> {
        @Override
        public InternalOrderDTO.Status convert(String source) {
            return InternalOrderDTO.Status.fromString(source.toUpperCase());
        }
    }
}