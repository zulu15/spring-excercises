package com.jis.springboot.web.interceptor.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jis.springboot.web.interceptor.app.UsuarioNoEncontradoException;
import com.jis.springboot.web.interceptor.app.model.Usuario;
import com.jis.springboot.web.interceptor.app.services.UsuarioService;

@Controller
public class IndexController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping({ "/", "/index" })
	public String index() {
		int r = 100/0;
		return "fsafdsdfsd";
	}

	@GetMapping({ "cerrado" })
	public String cerrado() {
		return "cerrado";
	}
	
	@GetMapping({ "/ver/{usuarioId}" })
	public String ver(@PathVariable Integer usuarioId, Model model) {
		//Usuario u = usuarioService.obtenerPorId(usuarioId);
		//if(u == null) throw new UsuarioNoEncontradoException(usuarioId.toString());
		
		model.addAttribute("usuario", usuarioService.obtenerUsuarioPorId(usuarioId).orElseThrow(() -> new UsuarioNoEncontradoException(usuarioId.toString())));
		return "ver";
	}

}
