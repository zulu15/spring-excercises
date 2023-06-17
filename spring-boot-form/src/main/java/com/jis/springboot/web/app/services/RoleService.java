package com.jis.springboot.web.app.services;

import java.util.List;

import com.jis.springboot.web.app.models.Role;

public interface RoleService {
	public List<Role> listarRoles();
	public Role buscarPorId(Integer id);

}
