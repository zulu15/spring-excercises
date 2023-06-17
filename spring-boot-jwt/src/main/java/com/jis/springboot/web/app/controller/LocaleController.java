package com.jis.springboot.web.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LocaleController {

	@GetMapping("/locale")
	public String localeHandler(HttpServletRequest request) {
		String lastPage = request.getHeader("referer");
		System.out.println(lastPage);
		return "redirect:".concat(lastPage);
	}
}
