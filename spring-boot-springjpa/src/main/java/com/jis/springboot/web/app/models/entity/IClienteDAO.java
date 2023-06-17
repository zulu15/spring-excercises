package com.jis.springboot.web.app.models.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IClienteDAO extends JpaRepository<Cliente, Long> {

	
	@Query("select c from Cliente c left join fetch c.facturas f where c.id = ?1")
	public Cliente findByIdWithFacturas(Long id);

}
