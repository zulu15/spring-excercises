package com.jis.springboot.web.interceptor.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jis.springboot.web.interceptor.app.UsuarioNoEncontradoException;

@ControllerAdvice
public class ArithmeticErrorHanlderController {

	@ExceptionHandler(ArithmeticException.class)
	public String manejarError(Exception e, Model model) {
		model.addAttribute("exception", "Error al dividir por zero");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return "error/arithmeticError";
	}
	
	@ExceptionHandler(UsuarioNoEncontradoException.class)
	public String manejarUsuarioNoEncontradoError(Exception e, Model model) {
		model.addAttribute("exception", "Error usuario no existe");
		return "error/userNotFound";
	}
}
