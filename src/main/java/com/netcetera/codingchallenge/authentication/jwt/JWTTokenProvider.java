package com.netcetera.codingchallenge.authentication.jwt;

import com.netcetera.codingchallenge.authentication.jwt.rest.JWTAuthException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JWTTokenProvider {

    private final JWTKeyProvider jwtKeyProvider;
    private final UserDetailsService userDetailsService;

    private Key secretKey;
    private Key publicKey;

    @PostConstruct
    protected void init() {
        this.secretKey = this.jwtKeyProvider.getSecretKey();
        this.publicKey = this.jwtKeyProvider.getPublicKey();
    }

    public String createToken(final String username, final Set<String> authorities) {

        final Claims claims = Jwts.claims().setSubject(username);
        claims.put("authorities", authorities);

        final Date now = new Date();
        final Date validity = new Date(now.getTime() + this.jwtKeyProvider.getValidityInMs());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.RS384, this.secretKey)
                .compact();
    }

    public Authentication getAuthentication(final String token) {
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getUsername(final String token) {
        return Jwts.parser().setSigningKey(this.publicKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(final HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public boolean validateToken(final String token) {
        try {
            final Jws<Claims> claims = Jwts.parser().setSigningKey(this.publicKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (final JwtException | IllegalArgumentException e) {
            throw new JWTAuthException("Expired or invalid JWT token");
        }
    }
}