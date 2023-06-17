package com.jis.springboot.di.app.services;

import org.springframework.stereotype.Service;

//@Service("saludoServiceAux")
public class SaludoServiceAuxImpl implements ISaludo {

	public String saludar() {
		return "Hola Mundo desde Saludo Service Aux";
	}
}
