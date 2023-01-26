package de.niko.pcstore;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableJpaRepositories
@SecurityScheme(name = "test-jwt", scheme = "bearer", bearerFormat = "jwt", type = SecuritySchemeType.OAUTH2, in = SecuritySchemeIn.HEADER,
        flows = @OAuthFlows(
                password = @OAuthFlow(
                        authorizationUrl = "http://localhost:8180/realms/master/protocol/openid-connect/auth",
                        tokenUrl = "http://localhost:8180/realms/master/protocol/openid-connect/token",
                        scopes = {@OAuthScope(name = "openid"), @OAuthScope(name = "profile"), @OAuthScope(name = "email")}

                )
        )
)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
