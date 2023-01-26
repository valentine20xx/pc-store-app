package de.niko.pcstore.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.niko.pcstore.controller.api.InternalOrderApi;
import de.niko.pcstore.dto.ErrorDTO;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Slf4j
@EnableWebSecurity
public class SecurityConfig {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SecurityConfig() {
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        return authenticationManagerBuilder.build();
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(true);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());

        http.csrf().disable()
                .authorizeRequests()
                .expressionHandler(expressionHandler)
                .antMatchers("/get-token", "/swagger-ui/**", "/v3/api-docs/**", "/api").permitAll()
                .antMatchers(HttpMethod.POST,"/internal-order/**").hasRole("EDIT")
                .antMatchers(HttpMethod.DELETE,"/internal-order/**").hasRole("EDIT")
                .antMatchers(HttpMethod.GET, "/internal-order/update-status").hasRole("EDIT")

                .antMatchers(HttpMethod.GET, "/internal-order/**").hasRole("READ")
//                .mvcMatchers(HttpMethod.GET, InternalOrderApi.GET_INTERNAL_ORDER_LIST,
//                        InternalOrderApi.GET_INTERNAL_ORDER,
//                        InternalOrderApi.GET_INTERNAL_ORDER_PERSONAL_COMPUTER).hasRole("READ")
//
//                .mvcMatchers(HttpMethod.POST, InternalOrderApi.ADD_INTERNAL_ORDER).hasRole("EDIT")
//                .mvcMatchers(HttpMethod.GET, InternalOrderApi.UPDATE_INTERNAL_ORDER_STATUS).hasRole("EDIT")
//                .mvcMatchers(HttpMethod.DELETE, InternalOrderApi.DELETE_INTERNAL_ORDER).hasRole("EDIT")
                .anyRequest().denyAll()
                .and().exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
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
                })
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().oauth2Login()
                .and().oauth2ResourceServer(
                        oauth2ResourceServer -> oauth2ResourceServer.jwt(
                                jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        return jwtConverter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }

    String jwkSetUri = "http://localhost:8180/realms/master/protocol/openid-connect/certs";

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        Map<String, List<String>> roleHierarchyMap = new HashMap<>();
        roleHierarchyMap.put("ROLE_EDIT", Arrays.asList("ROLE_READ"));

        roleHierarchy.setHierarchy(RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap));
        return roleHierarchy;
    }
}