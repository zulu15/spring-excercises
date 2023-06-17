package com.jis.backend.apirest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jis.backend.apirest.entity.Cliente;

public interface IClienteDAO extends JpaRepository<Cliente, Long>{

	
}
