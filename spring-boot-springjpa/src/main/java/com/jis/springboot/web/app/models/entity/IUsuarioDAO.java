package com.jis.springboot.web.app.models.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDAO extends JpaRepository<Usuario, Long> {

	public Usuario findByUsername(String username);
}
