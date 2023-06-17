package com.jis.springboot.web.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jis.springboot.web.app.auth.JWTService;
import com.jis.springboot.web.app.auth.filter.JWTAuthenticationFilter;
import com.jis.springboot.web.app.auth.filter.JWTAuthorizationFilter;
import com.jis.springboot.web.app.service.JPAUserDetailsService;


@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private JWTService jwtService;
	
    @Autowired
    private UserDetailsService userDetailService;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }
    
	/*
	@Bean
	public UserDetailsService userDetailsService() {
		var admin = User.withUsername("joaquin").password(bCryptPasswordEncoder.encode("1234")).roles("USER","ADMIN").build();
		var user = User.withUsername("juan").password(bCryptPasswordEncoder.encode("1234")).roles("USER").build();

		return new InMemoryUserDetailsManager(user,admin);
	}
	*/
	
	/*
	   @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
	 
	        build.jdbcAuthentication()
	        .dataSource(dataSource)
	        .passwordEncoder(bCryptPasswordEncoder)
	        .usersByUsernameQuery("select username, password, enabled from users where username=?")
	        .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");
	    }
*/
	
	@Autowired
    public UserDetailsService userDetailsService(AuthenticationManagerBuilder build) throws Exception {
       build.userDetailsService(userDetailService)
       .passwordEncoder(bCryptPasswordEncoder);
       return build.getDefaultUserDetailsService();
    }
  
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.cors().and()
	                .authorizeHttpRequests()
	                .requestMatchers("/", "/css/**", "/js/**", "/images/**","/locale","/api/clientes/listar","/error","*")
	                .permitAll()
	                .requestMatchers("/api/clientes/**").hasAnyRole("ADMIN")
	                .requestMatchers("/ver/**").hasAnyRole("USER")
	                .requestMatchers("/uploads/**").hasAnyRole("USER")
	                .requestMatchers("/form/**").hasAnyRole("ADMIN")
	                .requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
	                .requestMatchers("/factura/**").hasAnyRole("ADMIN")
	                .anyRequest().authenticated()
	                .and().addFilter(new JWTAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtService))
	                .addFilter(new JWTAuthorizationFilter(authenticationManager(authenticationConfiguration), jwtService))
	                .exceptionHandling().accessDeniedPage("/error_403").and().csrf().disable();
	 
        return http.build();
    }
}
