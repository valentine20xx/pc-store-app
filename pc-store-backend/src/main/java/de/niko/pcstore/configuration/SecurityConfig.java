package de.niko.pcstore.configuration;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    final Environment environment;

    public SecurityConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/**");
    }

    protected void configure(HttpSecurity http) throws Exception {
        var activeProfiles = environment.getActiveProfiles();
        var activeProfilesList = Arrays.asList(activeProfiles);

        var isLocal = activeProfilesList.contains("local");
        var isDev = activeProfilesList.contains("dev");
        var isProd = activeProfilesList.contains("prod");

        if (isLocal) {
            LOGGER.debug("Local environment");
            http.authorizeRequests().antMatchers("/**").permitAll();
        } else if (isDev) {
            LOGGER.debug("Development environment");
            http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
//            No auth
        } else if (isProd) {
            LOGGER.debug("Production environment");
        }
    }


    @Autowired
    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        LOGGER.info("globalSecurityConfiguration");
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//        auth.inMemoryAuthentication().withUser("admin").password("password").roles("USER", "ADMIN");
    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests().antMatchers("/noSecurity").permitAll()
////                .anyRequest().authenticated()
////                .and().formLogin().permitAll();
//        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
//
//    }

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        LOGGER.info("configure");
//
//        httpSecurity
//                .authorizeRequests()
//                .anyRequest().fullyAuthenticated()
//                .and()
//                .formLogin();
//
////        httpSecurity.authorizeRequests()
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                .ldapAuthentication()
//                .contextSource().url("ldap://localhost:389/")
//                .managerDn("uid=admin,ou=system").managerPassword("secret")
//                .and()
//                .userSearchBase("ou=users,ou=system")
//                .userSearchFilter("(uid={0})");
//    }
}