package com.jis.springboot.web.app.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jis.springboot.web.app.models.entity.Cliente;
import com.jis.springboot.web.app.service.IClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	
	@GetMapping({ "/listar" })
	@Secured("ROLE_ADMIN")
	public List<Cliente> listarClientes(Model model, Authentication authentication, Locale locale) {
		return clienteService.findAll();
	}
}
