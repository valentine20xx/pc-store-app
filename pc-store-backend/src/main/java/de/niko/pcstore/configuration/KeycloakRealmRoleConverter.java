package de.niko.pcstore.configuration;

import com.nimbusds.jose.shaded.json.JSONObject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

@Slf4j
public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        log.info("realm_access: " + realmAccess);

        final Map<String, Object> resource_access = jwt.getClaimAsMap("resource_access");

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
    }
}