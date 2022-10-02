package de.niko.pcstore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.MessageCodesResolver;

@Configuration
public class MessageCodesResolverConfiguration {
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
