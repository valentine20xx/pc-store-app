package de.niko.pcstore.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.niko.pcstore.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@EnableMethodSecurity
@Slf4j
@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(true);
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> authorizationManager_READ(RoleHierarchy roleHierarchy) {
        return (authentication, object) -> {

// Jwt jwtToken = ((JwtAuthenticationToken) authentication.get()).getToken();
// Check token session
            var authorityAuthorizationManager = AuthorityAuthorizationManager.hasRole("READ");
            authorityAuthorizationManager.setRoleHierarchy(roleHierarchy);

            return authorityAuthorizationManager.check(authentication, object);
        };
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> authorizationManager_EDIT(RoleHierarchy roleHierarchy) {
        return (authentication, object) -> {
            var authorityAuthorizationManager = AuthorityAuthorizationManager.hasRole("EDIT");
            authorityAuthorizationManager.setRoleHierarchy(roleHierarchy);

            return authorityAuthorizationManager.check(authentication, object);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthorizationManager<RequestAuthorizationContext> authorizationManager_READ,
                                           AuthorizationManager<RequestAuthorizationContext> authorizationManager_EDIT) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**", "/api", "/favicon.ico").permitAll()
                        .requestMatchers(HttpMethod.POST, "/internal-order/**").access(authorizationManager_EDIT)
                        .requestMatchers(HttpMethod.DELETE, "/internal-order/**").access(authorizationManager_EDIT)
                        .requestMatchers(HttpMethod.GET, "/internal-order/update-status").access(authorizationManager_EDIT)
                        .requestMatchers(HttpMethod.GET, "/internal-order/**").access(authorizationManager_READ)
                        .anyRequest().denyAll())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                            if (authentication != null) {
                                log.warn("User: " + authentication.getName() + " attempted to access the protected URL: " + request.getRequestURI());
                            }

                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

                            ErrorDTO errorDTO = new ErrorDTO();
                            errorDTO.setCode(HttpServletResponse.SC_FORBIDDEN);
                            errorDTO.setMessage("Not enough authorities");

                            String errorDTOAsJsonObject = objectMapper.writeValueAsString(errorDTO);

                            response.getWriter().write(errorDTOAsJsonObject);
                        }))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("http://localhost:18080/auth/realms/PC-Store/protocol/openid-connect/logout"))
                .oauth2ResourceServer(new PCStoreOAuth2ResourceServerCustomizer())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(@Value("${jwk-set-uri:}") String jwkSetUri) {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        Map<String, List<String>> roleHierarchyMap = new HashMap<>();
        roleHierarchyMap.put("ROLE_EDIT", List.of("ROLE_READ"));

        roleHierarchy.setHierarchy(RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap));
        return roleHierarchy;
    }
}