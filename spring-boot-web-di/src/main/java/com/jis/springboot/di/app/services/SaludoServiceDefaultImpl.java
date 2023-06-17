package com.jis.springboot.di.app.services;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

//@Service("saludoService")
//@Primary
public class SaludoServiceDefaultImpl implements ISaludo {



	
	public String saludar() {
		return "Hola Mundo desde Saludo Service Default ";
	}
}
