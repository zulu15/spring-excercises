package com.jis.springboot.web.interceptor.app;

public class UsuarioNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioNoEncontradoException(String id) {
		super("Usuario con id ".concat(id).concat(" no encontrado"));

	}
	

}
