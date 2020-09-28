package com.netcetera.codingchallenge.authentication.jwt.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JWTResponse implements Serializable {
    private static final long serialVersionUID = 5895787271951299252L;

    @JsonProperty("access_token")
    private String JWToken;

    @JsonProperty("expires_in")
    private final int expiresIn = 60 * 60;
}
