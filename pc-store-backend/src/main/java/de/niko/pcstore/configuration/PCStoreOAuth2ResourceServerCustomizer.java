package de.niko.pcstore.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.niko.pcstore.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Slf4j
public class PCStoreOAuth2ResourceServerCustomizer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> httpSecurityOAuth2ResourceServerConfigurer) {
        httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtCustomizer -> {
            JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
            jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
                final Map<String, Object> resource_access = jwt.getClaimAsMap("resource_access");

                log.info("resource_access: " + resource_access);

                final Map<String, Object> testClient = (Map<String, Object>) resource_access.get("test-client");

                if (testClient == null) {
                    return Collections.emptyList();
                }
                final List<String> roles = (List<String>) testClient.get("roles");

                if (roles == null) {
                    return Collections.emptyList();
                }

                return (roles).stream()
                        .map(roleName -> "ROLE_" + roleName) // prefix to map to a Spring Security "role"
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            });

            jwtCustomizer.jwtAuthenticationConverter(jwtConverter);
        });
        httpSecurityOAuth2ResourceServerConfigurer.authenticationEntryPoint((request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setCode(HttpServletResponse.SC_UNAUTHORIZED);
            errorDTO.setMessage("No or invalid token");

            String errorDTOAsJsonObject = objectMapper.writeValueAsString(errorDTO);

            response.getWriter().write(errorDTOAsJsonObject);
        });
    }
}
