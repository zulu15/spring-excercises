package com.jis.springboot.web.app.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Model model, Principal principal, RedirectAttributes flash) {
		if(principal != null) {
			//usuaro logeado
			flash.addFlashAttribute("danger", "Ya te encuentras logeado!");
			return "redirect:/listar";
		}
		if(error != null) {
			model.addAttribute("danger", "Usuario o contraseña incorrecto!");
		}

		if(logout != null) {
			model.addAttribute("success", "Sesión cerrada con éxito!");
		}
		return "login";
	}
}
