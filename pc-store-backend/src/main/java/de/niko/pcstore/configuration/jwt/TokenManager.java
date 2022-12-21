package de.niko.pcstore.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenManager implements Serializable {
    private static final long serialVersionUID = 7008375124389347049L;
    public static final long TOKEN_VALIDITY = 10 * 60 * 60;
    @Value("${secret}")
    private String jwtSecret;

    /**
     * This method is used to generate a token on successful authentication by the user.
     * To create the token here we use the username, issue date of token and the expiration date of the token.
     * This will form the payload part of the token or claims as we had discussed earlier.
     * To generate the token we use the builder() method of Jwts. This method returns a new JwtBuilder instance that can be used to create compact JWT serialized strings.
     *
     * @param userDetails
     * @return
     */
    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, SignatureAlgorithm.HS512.getJcaName());

        String username = getUsernameFromToken(token);
        Claims claims = Jwts.parserBuilder().setSigningKey(originalKey).build().parseClaimsJws(token).getBody();

        boolean isTokenExpired = claims.getExpiration().before(new Date());

        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    public String getUsernameFromToken(String token) {


        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}