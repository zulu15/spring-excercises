package com.jis.springboot.web.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jis.springboot.web.app.models.entity.Cliente;
import com.jis.springboot.web.app.models.entity.Factura;
import com.jis.springboot.web.app.models.entity.Producto;

public interface IClienteService {
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable page);
	public void save(Cliente cliente);
	public Cliente findById(Long id);
	public void delete(Long id);
	public List<Producto> findByName(String nombre);
	public void saveFactura(Factura factura);
	public Producto findProductoById(Long id);
	public Factura findFacturaById(Long id);
	public void deleteFacturaById(Long id);
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
	public Cliente findByIdWithFacturas(Long id);
}
