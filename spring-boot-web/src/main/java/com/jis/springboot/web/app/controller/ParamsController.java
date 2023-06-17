package com.jis.springboot.web.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/params")
public class ParamsController {

	@GetMapping("/greetings")
	public String sendStringParameter(
			@RequestParam(name = "nombre", required = false, defaultValue = "Sin nombre") String name,
			@RequestParam String apellido, Model model) {
		model.addAttribute("greetings",
				"Hola ".concat(name).concat(" ").concat(apellido).concat(" espero que te encuentres muy bien!"));
		return "/params/greetings";
	}

	@GetMapping("/greetings-loop")
	public String greet(@RequestParam String nombre, @RequestParam Integer loop, Model model) {
		List<String> saludos = new ArrayList<>();
		for (int i = 0; i < loop; i++) {
			saludos.add("Hola ".concat(nombre));
		}
		model.addAttribute("saludos", saludos);
		model.addAttribute("loop", loop);
		return "/params/greetings";
	}

	@GetMapping("/greet")
	public String sendGreeting() {
		return "/params/redirect";
	}
}
