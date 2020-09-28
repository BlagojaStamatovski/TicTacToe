package com.netcetera.codingchallenge.authentication.jwt;

import com.netcetera.codingchallenge.authentication.jwt.rest.JWTAuthException;
import com.netcetera.codingchallenge.users.User;
import com.netcetera.codingchallenge.users.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JWTTokenProviderUnitTest {
    @Mock
    UserService userService;

    @Mock
    JWTKeyProvider jwtKeyProvider;

    JWTTokenProvider jwtTokenProvider;

    static KeyPair keyPair;
    static final String USERNAME = "testUser";
    static final Set<String> AUTHORITIES = new LinkedHashSet<>();

    @BeforeAll
    static void init() throws NoSuchAlgorithmException {
        if (keyPair == null) {
            final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            keyPair = generator.generateKeyPair();
        }
        if (AUTHORITIES.isEmpty()) {
            AUTHORITIES.add("authority1");
            AUTHORITIES.add("authority2");
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        final User testUser = User.builder().username(USERNAME).password("password").build();
        final Key secretKey = keyPair.getPrivate();
        final Key publicKey = keyPair.getPublic();
        Mockito.when(this.jwtKeyProvider.getSecretKey()).thenReturn(secretKey);
        Mockito.when(this.jwtKeyProvider.getPublicKey()).thenReturn(publicKey);
        Mockito.when(this.jwtKeyProvider.getValidityInMs()).thenReturn(360000L);
        Mockito.when(this.userService.loadUserByUsername(USERNAME)).thenReturn(testUser);

        this.jwtTokenProvider = new JWTTokenProvider(this.jwtKeyProvider, this.userService);
        this.jwtTokenProvider.init();
    }

    @Test
    void createToken() {
        final String token = this.jwtTokenProvider.createToken(USERNAME, AUTHORITIES);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void getAuthentication() {
        final String token = this.jwtTokenProvider.createToken(USERNAME, AUTHORITIES);
        final Authentication authentication = this.jwtTokenProvider.getAuthentication(token);

        final User principal = (User) authentication.getPrincipal();
        final Set<String> retrievedAuthorities = new HashSet<>();
        authentication.getAuthorities().forEach(x -> retrievedAuthorities.add(x.getAuthority()));

        assertEquals(USERNAME, principal.getUsername());
        for (final String authority : retrievedAuthorities) {
            assertTrue(AUTHORITIES.contains(authority));
        }
    }

    @Test
    void getUsername() {
        final String token = this.jwtTokenProvider.createToken(USERNAME, AUTHORITIES);
        assertEquals(USERNAME, this.jwtTokenProvider.getUsername(token));
    }

    @Test
    void resolveToken() {
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final String token = this.jwtTokenProvider.createToken(USERNAME, AUTHORITIES);
        Mockito.when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer " + token);
        final String resolvedToken = this.jwtTokenProvider.resolveToken(httpServletRequest);
        assertEquals(token, resolvedToken);
    }

    @Test
    void validateToken() {
        final String token = this.jwtTokenProvider.createToken(USERNAME, AUTHORITIES);
        assertTrue(this.jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateTokenRaisesException() {
        assertThrows(JWTAuthException.class, () -> this.jwtTokenProvider.validateToken("obviouslyNotAToken"));
    }

    @Test
    void validateExpiredTokenRaisesException() {
        Mockito.when(this.jwtKeyProvider.getValidityInMs()).thenReturn(0L);
        final String token = this.jwtTokenProvider.createToken(USERNAME, AUTHORITIES);
        assertThrows(JWTAuthException.class, () -> this.jwtTokenProvider.validateToken(token));
    }
}