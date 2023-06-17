package com.jis.springboot.di.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jis.springboot.di.app.services.ISaludo;

@Controller
public class IndexController {

	@Autowired
	@Qualifier("saludoServiceAux")
	private ISaludo saludoService;
	
	@GetMapping({"","/","/index"})
	public String index(Model model) {
		model.addAttribute("saludo", saludoService.saludar());
		return "index";
	}
	
	


	
}
