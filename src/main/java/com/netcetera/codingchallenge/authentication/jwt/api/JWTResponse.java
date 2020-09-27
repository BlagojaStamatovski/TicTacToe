package com.netcetera.codingchallenge.authentication.jwt.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JWTResponse implements Serializable {
    private static final long serialVersionUID = 5895787271951299252L;
    private String JWToken;
}
