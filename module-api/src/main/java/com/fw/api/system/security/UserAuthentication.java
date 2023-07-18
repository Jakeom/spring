package com.fw.api.system.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * UserAuthentication
 * @author sjpaik
 */
public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(String principal, String credentials) {
        super(principal, credentials);
    }

    public UserAuthentication(String principal, String credentials, List<GrantedAuthority> authorityList) {
        super(principal, credentials);
    }

}
