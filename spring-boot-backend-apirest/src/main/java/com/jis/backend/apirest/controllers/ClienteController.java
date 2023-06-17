package com.jis.backend.apirest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.jis.backend.apirest.entity.Cliente;
import com.jis.backend.apirest.services.IClienteService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/clientes/")
	public List<Cliente> listarClientes() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/{clienteId}")
	public ResponseEntity<?> getCliente(@PathVariable Long clienteId) {
		
		Map<String,String> errores = new HashMap<String,String>();
		Cliente cliente = null;
		
		try {
			cliente = clienteService.findById(clienteId);
		} catch (DataAccessException e) {
			errores.put("mensaje", "Hubo un error al obtener el cliente en la base de datos");
			errores.put("error", e.getMessage() );
			return new ResponseEntity<Map<String,String>>(errores, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cliente == null) {
			errores.put("mensaje", "No se encontró el cliente con id "+clienteId);
			return new ResponseEntity<Map<String,String>>(errores, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@PostMapping("/clientes/")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@Valid @RequestBody Cliente cliente, BindingResult bindingResult) {
		Map<String,Object> errores = new HashMap<String,Object>();
		
		Cliente clienteNuevo = null;
		try {
			clienteNuevo = clienteService.save(cliente);
		} catch (DataAccessException e) {
			errores.put("mensaje", "Error al insertar el cliente en la base de datos");
			errores.put("error", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(errores,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Cliente>(clienteNuevo,HttpStatus.CREATED);
	}
	
	@PutMapping("/clientes/{clienteId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updateCliente(@Valid @RequestBody Cliente cliente, @PathVariable Long clienteId, BindingResult bindingResult) {
		Map<String,Object> errores = new HashMap<String,Object>();
		
		Cliente clienteActual = clienteService.findById(clienteId);
		if(clienteActual == null) {
		
			errores.put("mensaje", "Error al actualizar el cliente en la base de datos");
			errores.put("error", "El cliente no pudo ser encontrado");
			return new ResponseEntity<Map<String,Object>>(errores,HttpStatus.NOT_FOUND);
		}
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setEmail(cliente.getEmail());

		try {
			clienteService.save(clienteActual);
		} catch (DataAccessException e) {

			errores.put("mensaje", "Error al actualizar el cliente en la base de datos");
			errores.put("error", e.getMessage() + ":  "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(errores,HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<Cliente>(cliente,HttpStatus.CREATED);

	}

	@DeleteMapping("/clientes/{clienteId}")
	public ResponseEntity<?> deleteCliente(@PathVariable Long clienteId) {
		try {
			clienteService.delete(clienteId);
			System.out.println("ok");
		} catch (DataAccessException e) {
			Map<String,String> errores = new HashMap<String,String>();
			errores.put("mensaje", "Error al eliminar el cliente en la base de datos");
			errores.put("error", e.getMessage() + ":  "+e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,String>>(errores,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Map<String,String> response = new HashMap<String,String>();
		response.put("mensaje", "El cliente fue eliminado con éxito!");
		return new ResponseEntity<Map<String,String>>(response,HttpStatus.OK);
	}

}
