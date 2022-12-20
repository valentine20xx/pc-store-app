package de.niko.pcstore.configuration;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
public class SecurityConfig {
    private static Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    public static final String SECURITY_CONFIG_NAME = "App Bearer token";

    final Environment environment;

    public SecurityConfig(Environment environment) {
        this.environment = environment;
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/swagger-ui/**");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var activeProfiles = environment.getActiveProfiles();
        var activeProfilesList = Arrays.asList(activeProfiles);

        var isLocal = activeProfilesList.contains("local");
        var isDev = activeProfilesList.contains("dev");
        var isProd = activeProfilesList.contains("prod");

        http
                .csrf().disable()
//                .authorizeRequests().antMatchers("/internal**").authenticated().and()
                .authorizeRequests().mvcMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll().and()
                .authorizeRequests().anyRequest().authenticated().and()
                // httpBasic authentication
                .httpBasic();

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