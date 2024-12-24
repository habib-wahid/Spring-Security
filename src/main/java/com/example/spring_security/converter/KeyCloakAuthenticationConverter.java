package com.example.spring_security.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyCloakAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<String> realmRoles = jwt.getClaimAsMap("realm_access") != null
                ? (List<String>) jwt.getClaimAsMap("realm_access").get("roles")
                : List.of();

        // Extract roles from resource_access
        List<String> clientRoles = (List<String>) Optional.ofNullable(jwt.getClaimAsMap("resource_access"))
                .map(resourceAccess -> {
                    Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("OAuth2-pkce-client");
                    return clientAccess != null ? (List<String>) clientAccess.get("roles") : Collections.emptyList();
                })
                .orElse(Collections.emptyList());

        // Combine all roles and map to authorities
        return Stream.concat(realmRoles.stream(), clientRoles.stream())
                .map(role -> "ROLE_" + role.toUpperCase()) // Prefix with ROLE_ for Spring Security compatibility
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
