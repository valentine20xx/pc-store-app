package de.niko.pcstore.controller.api;

import de.niko.pcstore.configuration.Tags;
import de.niko.pcstore.configuration.jwt.CustomUser;
import de.niko.pcstore.configuration.jwt.TokenManager;
import de.niko.pcstore.dto.ErrorDTO;
import de.niko.pcstore.dto.JwtRequest;
import de.niko.pcstore.dto.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = Tags.JWT_TAG)
@RestController
public class JwtController {
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    public JwtController(AuthenticationManager authenticationManager, TokenManager tokenManager) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
    }

    @Operation(summary = "Get a token for service access")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Wrong username/password pair", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "403", description = "User is disabled", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
    })
    @RequestMapping(value = "/get-token",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST)
    public ResponseEntity<Object> getToken(@RequestBody JwtRequest request) {
        try {
            final var username = request.getUsername();
            final var password = request.getPassword();

            final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            final var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            final var userDetails = (CustomUser) authentication.getPrincipal();
            final var jwtToken = tokenManager.generateJwtToken(userDetails);

            final var jwtResponse = new JwtResponse(jwtToken);

            return ResponseEntity.ok(jwtResponse);
        } catch (DisabledException e) {
            e.printStackTrace();
            final var errorDTO = new ErrorDTO(403, "user disabled");
            return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            final var errorDTO = new ErrorDTO(400, "invalid credentials");
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
    }
}