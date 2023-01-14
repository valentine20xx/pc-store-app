package de.niko.pcstore.configuration.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class JwtUserDetailsService implements UserDetailsService {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser foundUser = null;

        if ("username1".equals(username)) {
            foundUser = new CustomUser("username1", passwordEncoder().encode("password"), AuthorityUtils.NO_AUTHORITIES, "User name one");
        } else if ("username2".equals(username)) {
            foundUser = new CustomUser("username2", passwordEncoder().encode("password"), AuthorityUtils.createAuthorityList("ROLE_READ"), "User name two");
        } else if ("username3".equals(username)) {
            foundUser = new CustomUser("username3", passwordEncoder().encode("password"), AuthorityUtils.createAuthorityList("ROLE_READ", "ROLE_EDIT"), "User name three");
        }

        if (foundUser != null) {
            log.info("Found user: " + foundUser.getUsername());

            return foundUser;
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}