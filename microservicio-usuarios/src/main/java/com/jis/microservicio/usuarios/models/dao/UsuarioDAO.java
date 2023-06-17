package com.jis.microservicio.usuarios.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jis.microservicio.usuarios.models.entity.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{

	 Optional<Usuario> findByEmail(String email);

}
