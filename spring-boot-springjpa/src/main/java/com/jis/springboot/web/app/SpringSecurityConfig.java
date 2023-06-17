package com.jis.springboot.web.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jis.springboot.web.app.service.JPAUserDetailsService;


@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsService userDetailService;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	
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
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar**","/locale")
                .permitAll()
                .requestMatchers("/ver/**").hasAnyRole("USER")
                .requestMatchers("/uploads/**").hasAnyRole("USER")
                .requestMatchers("/form/**").hasAnyRole("ADMIN")
                .requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
                .requestMatchers("/factura/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                		.successHandler(loginSuccessHandler)
                		.loginPage("/login")
                .permitAll()
                .and().logout()
                .permitAll().and().exceptionHandling().accessDeniedPage("/error_403");
 
        return http.build();
    }
}
