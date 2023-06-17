package com.jis.springboot.web.app.auth;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import io.jsonwebtoken.Claims;

public interface JWTService {

	public String create(Authentication authentication) throws JsonProcessingException;
	public boolean isValid(String token);
	public Claims getClaims(String token);
	public String getUsername(String token);
	public Collection<? extends GrantedAuthority> getRoles(String token)  throws StreamReadException, DatabindException, IOException;
	public String resolve(String token);
}
