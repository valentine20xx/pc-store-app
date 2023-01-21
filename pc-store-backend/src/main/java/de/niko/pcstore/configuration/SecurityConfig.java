package de.niko.pcstore.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.niko.pcstore.configuration.jwt.JwtAuthenticationEntryPoint;
import de.niko.pcstore.configuration.jwt.JwtFilter;
import de.niko.pcstore.configuration.jwt.JwtUserDetailsService;
import de.niko.pcstore.controller.api.InternalOrderApi;
import de.niko.pcstore.dto.ErrorDTO;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtFilter jwtFilter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint, JwtUserDetailsService jwtUserDetailsService, JwtFilter jwtFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(jwtUserDetailsService);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());

        http.csrf().disable()
                .authorizeRequests()
                .expressionHandler(expressionHandler)
                .antMatchers("/get-token", "/swagger-ui/**", "/v3/api-docs/**", "/api").permitAll()
                .mvcMatchers(InternalOrderApi.GET_INTERNAL_ORDER_LIST, InternalOrderApi.GET_INTERNAL_ORDER, InternalOrderApi.GET_INTERNAL_ORDER_PERSONAL_COMPUTER).hasRole("READ")
                .mvcMatchers(InternalOrderApi.ADD_INTERNAL_ORDER, InternalOrderApi.UPDATE_INTERNAL_ORDER_STATUS, InternalOrderApi.DELETE_INTERNAL_ORDER).hasRole("EDIT")
                .anyRequest().authenticated()
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
                }).authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        Map<String, List<String>> roleHierarchyMap = new HashMap<>();
        roleHierarchyMap.put("ROLE_EDIT", Arrays.asList("ROLE_READ"));

        roleHierarchy.setHierarchy(RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap));
        return roleHierarchy;
    }
}