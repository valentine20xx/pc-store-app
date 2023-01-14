package de.niko.pcstore.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.KeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenManager implements Serializable {
    public static final long TOKEN_VALIDITY = 2 * 60 * 60;
    private Key key;

    public TokenManager() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(SignatureAlgorithm.HS512.getJcaName());
            keyGenerator.init(512);
            this.key = keyGenerator.generateKey();
            log.debug("Generated key: " + Base64.getEncoder().encodeToString((this.key.getEncoded())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to generate a token on successful authentication by the user.
     * To create the token here we use the username, issue date of token and the expiration date of the token.
     * This will form the payload part of the token or claims as we had discussed earlier.
     * To generate the token we use the builder() method of Jwts.
     * This method returns a new JwtBuilder instance that can be used to create compact JWT serialized strings.
     *
     * @param userDetails
     * @return
     */
    public String generateJwtToken(CustomUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        claims.put("fullname", userDetails.getFullName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(this.key)
                .compact();
    }

    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        boolean isTokenExpired = claims.getExpiration().before(new Date());

        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}