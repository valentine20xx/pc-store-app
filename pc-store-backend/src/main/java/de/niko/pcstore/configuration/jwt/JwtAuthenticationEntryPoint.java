package de.niko.pcstore.configuration.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.niko.pcstore.dto.ErrorDTO;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        errorDTO.setMessage("Wrong or no token");

        String errorDTOAsJsonObject = objectMapper.writeValueAsString(errorDTO);

        response.getWriter().write(errorDTOAsJsonObject);
    }
}