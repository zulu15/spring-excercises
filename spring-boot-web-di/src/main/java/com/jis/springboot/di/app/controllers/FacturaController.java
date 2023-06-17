package com.jis.springboot.di.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jis.springboot.di.app.models.Factura;

@Controller
@RequestMapping("/factura")
public class FacturaController {

	@Autowired
	private Factura factura;
	
	@GetMapping("/ver")
	public String verFactura(Model model) {
		model.addAttribute("titulo", "Visualizando la factura");
		model.addAttribute("factura", factura);
		return "factura/ver";
	}
}
