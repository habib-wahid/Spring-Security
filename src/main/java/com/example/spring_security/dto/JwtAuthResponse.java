package com.example.spring_security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class JwtAuthResponse {
    private String token;
    private String tokenType;
}
