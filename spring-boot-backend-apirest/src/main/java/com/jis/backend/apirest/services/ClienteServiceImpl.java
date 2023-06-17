package com.jis.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jis.backend.apirest.dao.IClienteDAO;
import com.jis.backend.apirest.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDAO clienteDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return clienteDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteDAO.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDAO.deleteById(id);

	}

}
