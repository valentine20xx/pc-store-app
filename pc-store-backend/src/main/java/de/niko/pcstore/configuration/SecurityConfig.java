package de.niko.pcstore.configuration;

import de.niko.pcstore.configuration.jwt.JwtAuthenticationEntryPoint;
import de.niko.pcstore.configuration.jwt.JwtFilter;
import de.niko.pcstore.configuration.jwt.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@EnableWebSecurity
public class SecurityConfig {

    //    final Environment environment;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint, JwtUserDetailsService jwtUserDetailsService, JwtFilter jwtFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/swagger-ui/**");
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(jwtUserDetailsService);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        var activeProfiles = environment.getActiveProfiles();
//        var activeProfilesList = Arrays.asList(activeProfiles);
//
//        var isLocal = activeProfilesList.contains("local");
//        var isDev = activeProfilesList.contains("dev");
//        var isProd = activeProfilesList.contains("prod");

        http.csrf().disable()
                .authorizeRequests().antMatchers("/get-token", "/swagger-ui/**", "/v3/api-docs/**", "/api").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

//        if (isLocal || activeProfilesList.size() == 0) {
//            LOGGER.info("Local environment");
////            http.authorizeRequests().antMatchers("/**").permitAll();
//
////            http.authorizeRequests()
////                    .antMatchers("/swagger-ui/**").permitAll()
////
////                    .and()
////                    .authorizeRequests()
////                   .antMatchers("/internal**").authenticated()
//////
//////                    .hasRole("SWAGGER")
//////
////                    .and()
////                    .httpBasic();
//
////            http
////                    .authorizeRequests().antMatchers("/internal-order/**").hasRole("SWAGGER").and().httpBasic();
//
//        } else if (isDev) {
//            LOGGER.info("Development environment");
//            http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
////            No auth
//        } else if (isProd) {
//            LOGGER.info("Production environment");
//        }
//
//        return http.build();
    }
}