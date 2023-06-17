package com.jis.microservicio.usuarios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		  http.cors().and().csrf().disable().authorizeRequests()
          .requestMatchers(HttpMethod.GET,"/api/usuarios/authorized", "/api/usuarios/login","/logout","/login/oauth2/code/","/favicon.ico","/error").permitAll()
          .requestMatchers(HttpMethod.GET, "/api/usuarios", "/api/usuarios/{id}").hasAnyAuthority("SCOPE_read")
          .requestMatchers(HttpMethod.POST, "/api/usuarios").hasAuthority("SCOPE_write")
          .requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}").hasAuthority("SCOPE_write")
          .requestMatchers(HttpMethod.DELETE, "/api/usuarios/{id}").hasAuthority("SCOPE_write")
          .anyRequest().authenticated()
          .and()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/msvc-usuarios-client"))
          .oauth2Client(withDefaults()).csrf().disable()
          .oauth2ResourceServer().jwt();

		  return http.build();

	}
}

/*
 * httpSecurity.authorizeHttpRequests()
 * .requestMatchers("/authorized").permitAll()
 * .requestMatchers(HttpMethod.GET,"/api/usuarios","/api/usuarios/{id}").
 * hasAnyAuthority("SCOPE_read","SCOPE_write")
 * .requestMatchers(HttpMethod.POST,"/api/usuarios").hasAnyAuthority(
 * "SCOPE_write")
 * .requestMatchers(HttpMethod.PUT,"/api/usuarios/{id}").hasAnyAuthority(
 * "SCOPE_write")
 * .requestMatchers(HttpMethod.DELETE,"/api/usuarios/{id}").hasAnyAuthority(
 * "SCOPE_write") .anyRequest() .authenticated() .and() .sessionManagement()
 * .sessionCreationPolicy(SessionCreationPolicy.STATELESS) .and()
 * .oauth2Login(oauthLogin ->
 * oauthLogin.loginPage("/oauth2/authorization/msvc-usuarios-client"))
 * .oauth2Client(withDefaults()) .oauth2ResourceServer().jwt(); return
 * httpSecurity.build();
 */