package com.jis.springboot.web.app.models.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IProductoDAO extends JpaRepository<Producto, Long> {

	@Query("select p from Producto p where p.nombre LIKE %?1%")
	public List<Producto> findByName(String nombre);
}
