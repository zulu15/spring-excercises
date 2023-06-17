package com.jis.springboot.web.interceptor.app.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jis.springboot.web.interceptor.app.model.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private List<Usuario> usuarios;
	
	public UsuarioServiceImpl() {
		usuarios = Arrays.asList(new Usuario(1,"Lucas","Martinez"),
									new Usuario(2, "Joaquin", "Sanchez"),
									new Usuario(3, "Macarena", "Rodriguez"),
									new Usuario(4, "Lucia", "Pedrini"),
									new Usuario(5, "Camila", "Fernandez"));
	}
	
	@Override
	public List<Usuario> listar() {
		return usuarios;
	}

	@Override
	public Usuario obtenerPorId(Integer id) {
		for(Usuario u: usuarios) {
			if(u.getId().equals(id)) return u;
		}
		return null;
	}

	@Override
	public Optional<Usuario> obtenerUsuarioPorId(Integer id) {
		return Optional.ofNullable(obtenerPorId(id));
	}

}
