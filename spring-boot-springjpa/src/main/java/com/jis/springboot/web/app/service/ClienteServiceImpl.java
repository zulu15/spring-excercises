package com.jis.springboot.web.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jis.springboot.web.app.models.entity.Cliente;
import com.jis.springboot.web.app.models.entity.Factura;
import com.jis.springboot.web.app.models.entity.IClienteDAO;
import com.jis.springboot.web.app.models.entity.IFacturaDAO;
import com.jis.springboot.web.app.models.entity.IProductoDAO;
import com.jis.springboot.web.app.models.entity.Producto;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteDAO clienteDAO;
	
	@Autowired
	private IProductoDAO productoDAO;
	
	@Autowired
	private IFacturaDAO facturaDAO;
	
	
	@Override	
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDAO.save(cliente);		
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable page) {
		return clienteDAO.findAll(page);	

	}

	@Override
	public List<Producto> findByName(String nombre) {
		return productoDAO.findByName(nombre);
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDAO.save(factura);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		return productoDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteFacturaById(Long id) {
		facturaDAO.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id) {
		return facturaDAO.fetchByIdWithClienteWithItemFacturaWithProducto(id);
	}

	@Override
	public Cliente findByIdWithFacturas(Long id) {
		return clienteDAO.findByIdWithFacturas(id);
	}

}
