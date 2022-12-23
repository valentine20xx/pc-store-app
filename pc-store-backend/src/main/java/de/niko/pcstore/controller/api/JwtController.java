package de.niko.pcstore.controller.api;

import de.niko.pcstore.configuration.Tags;
import de.niko.pcstore.dto.ErrorDTO;
import de.niko.pcstore.dto.JwtRequest;
import de.niko.pcstore.configuration.jwt.JwtUserDetailsService;
import de.niko.pcstore.configuration.jwt.TokenManager;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = Tags.JWT_TAG)
@RestController
//@CrossOrigin
public class JwtController {
    private final JwtUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    public JwtController(JwtUserDetailsService userDetailsService, AuthenticationManager authenticationManager, TokenManager tokenManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
    }

    @Operation(summary = "Get a token for service access")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad input parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
    })
    @RequestMapping(value = "/get-token",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST)
    public ResponseEntity<Object> getToken(@RequestBody JwtRequest request) throws Exception {
        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

            authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
            ErrorDTO errorDTO = new ErrorDTO(401,"user disabled");
            return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwtToken = tokenManager.generateJwtToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse(jwtToken);

        return ResponseEntity.ok(jwtResponse);
    }
}