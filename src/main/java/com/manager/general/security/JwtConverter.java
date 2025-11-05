package com.manager.general.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Convertit un JWT Keycloak en Authentication Spring Security.
 * Récupère les rôles du realm et du client, et les transforme en authorities.
 */
@Slf4j
@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String REALM_ACCESS = "realm_access";
    private static final String RESOURCE_ACCESS = "resource_access";

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        log.info("Authorities: {}", authorities);
        return new JwtAuthenticationToken(jwt, authorities, getPrincipalName(jwt));
    }

    /**
     * Extrait les rôles du JWT (realm + clients)
     */
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Set<String> roles = new HashSet<>();

        // Rôles du realm
        Map<String, Object> realmAccess = jwt.getClaimAsMap(REALM_ACCESS);
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            roles.addAll((Collection<String>) realmAccess.get("roles"));
        }

        // Rôles des clients
        Map<String, Object> resourceAccess = jwt.getClaimAsMap(RESOURCE_ACCESS);
        if (resourceAccess != null) {
            resourceAccess.values().forEach(clientObj -> {
                Map<String, Object> clientMap = (Map<String, Object>) clientObj;
                if (clientMap.containsKey("roles")) {
                    roles.addAll((Collection<String>) clientMap.get("roles"));
                }
            });
        }

        // Préfixer les rôles avec ROLE_
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toSet());
    }

    /**
     * Définit le nom principal (username)
     */
    private String getPrincipalName(Jwt jwt) {
        return jwt.getClaimAsString("preferred_username");
    }
}