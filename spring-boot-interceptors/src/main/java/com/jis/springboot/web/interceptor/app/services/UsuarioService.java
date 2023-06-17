package com.jis.springboot.web.interceptor.app.services;

import java.util.List;
import java.util.Optional;

import com.jis.springboot.web.interceptor.app.model.Usuario;

public interface UsuarioService {
	public List<Usuario> listar();
	public Usuario obtenerPorId(Integer id);
	public Optional<Usuario> obtenerUsuarioPorId(Integer id);

}
