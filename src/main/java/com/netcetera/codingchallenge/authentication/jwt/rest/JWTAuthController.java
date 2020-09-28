package com.netcetera.codingchallenge.authentication.jwt.rest;

import com.netcetera.codingchallenge.authentication.jwt.JWTTokenProvider;
import com.netcetera.codingchallenge.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class JWTAuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/authenticate")
    public ResponseEntity<JWTResponse> authenticate(@RequestBody final JWTRequest jwtRequest, final HttpServletResponse httpResponse) {
        final String username = jwtRequest.getUsername();
        final String password = jwtRequest.getPassword();
        jwtRequest.setPassword(null);

        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        final UserDetails userDetails = this.userService.loadUserByUsername(username);
        final Set<String> authorities = new HashSet<>();
        userDetails.getAuthorities().stream().forEach(grantedAuthority -> authorities.add(grantedAuthority.getAuthority()));
        final String token = this.jwtTokenProvider.createToken(this.userService.loadUserByUsername(username).getUsername(), authorities);

        httpResponse.setHeader("Authentication", "Bearer " + token);
        return ResponseEntity.ok(new JWTResponse(token));
    }
}
