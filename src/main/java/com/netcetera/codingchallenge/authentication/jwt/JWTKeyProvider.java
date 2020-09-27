package com.netcetera.codingchallenge.authentication.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;

@Component
@RequiredArgsConstructor
public class JWTKeyProvider {

    private final JWTProperties jwtProperties;

    public Key getSecretKey() {
        try {
            final File keyStoreFile = new ClassPathResource(this.jwtProperties.getKeyStoreName()).getFile();
            final KeyStore keyStore = KeyStore.getInstance(keyStoreFile, this.jwtProperties.getKeyStorePassword().toCharArray());
            return keyStore.getKey(this.jwtProperties.getKeyAlias(), this.jwtProperties.getKeyStorePassword().toCharArray());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Key getPublicKey() {
        try {
            final File keyStoreFile = new ClassPathResource(this.jwtProperties.getKeyStoreName()).getFile();
            final KeyStore keyStore = KeyStore.getInstance(keyStoreFile, this.jwtProperties.getKeyStorePassword().toCharArray());
            final Certificate certificate = keyStore.getCertificate(this.jwtProperties.getKeyAlias());
            return certificate.getPublicKey();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getValidityInMs() {
        return this.jwtProperties.getValidityInMs();
    }
}
