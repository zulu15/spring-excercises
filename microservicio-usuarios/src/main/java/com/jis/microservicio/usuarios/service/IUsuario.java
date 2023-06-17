package com.jis.microservicio.usuarios.service;

import java.util.List;
import java.util.Optional;

import com.jis.microservicio.usuarios.models.entity.Usuario;

public interface IUsuario {

	 List<Usuario> findAll();
	 Usuario findById(Long id);
	 void delete(Usuario usuario);
	 Usuario save(Usuario usuario);
	 List<Usuario> findAllByIds(Iterable<Long> ids);
	 Optional<Usuario> findByEmail(String email);
	
}
