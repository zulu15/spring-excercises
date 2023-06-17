package com.jis.springboot.web.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jis.springboot.web.app.models.Role;

@Service
public class RoleServiceImpl implements RoleService {

	private List<Role> roles;
	
	public RoleServiceImpl() {
		this.roles = Arrays.asList(new Role(1,"Administrador","ROLE_ADMIN"),
								   new Role(2,"Moderador","ROLE_MODERATOR"),
								   new Role(3,"Usuario","ROLE_USER"));
	}

	@Override
	public List<Role> listarRoles() {
		return roles;
	}

	@Override
	public Role buscarPorId(Integer id) {
		for(Role role: roles) {
			if(role.getId() == id) return role;
		}
		return null;
	}

}
