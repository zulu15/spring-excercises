package com.jis.springboot.web.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jis.springboot.web.app.models.entity.IUsuarioDAO;
import com.jis.springboot.web.app.models.entity.Role;
import com.jis.springboot.web.app.models.entity.Usuario;

@Service
public class JPAUserDetailsService implements UserDetailsService{

	@Autowired
	private IUsuarioDAO usuarioDAO; 
	
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario u = usuarioDAO.findByUsername(username);
		if(u == null) throw new UsernameNotFoundException("Usuario no encontrado");
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Role role : u.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		if(authorities.isEmpty()) throw new UsernameNotFoundException("El usuario no tiene roles asignados");
		
		return new User(username,u.getPassword(),u.getEnabled(), true, true, true, authorities);
	}

	
}
