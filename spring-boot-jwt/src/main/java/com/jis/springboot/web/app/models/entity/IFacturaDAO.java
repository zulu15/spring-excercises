package com.jis.springboot.web.app.models.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IFacturaDAO extends JpaRepository<Factura, Long> {
	
	@Query("select f from Factura f join fetch f.cliente c join fetch f.items i join fetch i.producto p  where f.id = ?1")
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);

}
