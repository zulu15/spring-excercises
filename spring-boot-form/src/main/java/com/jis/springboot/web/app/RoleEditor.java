package com.jis.springboot.web.app;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jis.springboot.web.app.services.RoleService;

@Component
public class RoleEditor extends PropertyEditorSupport{

	@Autowired
	private RoleService roleService;
	
	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		try {
			setValue(roleService.buscarPorId(Integer.parseInt(id)));
		} catch (Exception e) {
			setValue(null);
		}	
		
	}
	

}
