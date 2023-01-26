package de.niko.pcstore;

import de.niko.pcstore.configuration.Tags;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableJpaRepositories
@SecurityScheme(name = Tags.SECURITY_SCHEME_NAME, scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.OAUTH2, in = SecuritySchemeIn.HEADER,
        flows = @OAuthFlows(
                password = @OAuthFlow(
                        authorizationUrl = "${spring.security.oauth2.client.provider.keycloak.authorization-uri}",
                        tokenUrl = "${spring.security.oauth2.client.provider.keycloak.token-uri}"
                )
        )
)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
