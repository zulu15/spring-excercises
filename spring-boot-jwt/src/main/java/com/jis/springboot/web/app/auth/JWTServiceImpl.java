package com.jis.springboot.web.app.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jis.springboot.web.app.auth.filter.JWTAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTServiceImpl implements JWTService {

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	
	public JWTServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String create(Authentication authResult) throws JsonProcessingException {
		Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
		Claims claims = Jwts.claims();
		claims.put("roles", new ObjectMapper().writeValueAsString(roles));

		String token = Jwts.builder()
							.setClaims(claims)
							.setSubject(authResult.getName()).signWith(JWTAuthenticationFilter.SECRET_KEY,SignatureAlgorithm.HS512).compact();		
		return token;
	}

	@Override
	public boolean isValid(String header) {

		try {

			getClaims(header);
			return true;

		} catch (Exception e) {
			System.out.println(e);
		}
	return false;
	}

	@Override
	public Claims getClaims(String header) {
		return Jwts.parserBuilder().setSigningKey(JWTAuthenticationFilter.SECRET_KEY)
				.build()
				.parseClaimsJws(resolve(header) )
				.getBody();
	}

	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws StreamReadException, DatabindException, IOException {
		Object roles = getClaims(token).get("roles");
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
																				.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class) );
		return authorities;
	}

	@Override
	public String resolve(String header) {
		if(header == null || !header.contains("Bearer")) return null;
		return header.replace("Bearer ", "").trim();
	}

}
