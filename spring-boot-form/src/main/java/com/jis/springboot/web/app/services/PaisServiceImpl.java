package com.jis.springboot.web.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jis.springboot.web.app.models.Pais;

@Service
public class PaisServiceImpl implements PaisService {

	private List<Pais> paises;
	
	
	public PaisServiceImpl() {
		this.paises = Arrays.asList(new Pais(1,"AR","Argentina"),
				 new Pais(2,"CL","Chile"),
				 new Pais(3,"CO","Colombia"),
				 new Pais(4,"PE","Perú"),
				 new Pais(5,"BOL","Bolivia"));
	}

	@Override
	public List<Pais> listarPaises() {
		return this.paises;
	}

	@Override
	public Pais obtenerPorId(Integer id) {
		for(Pais pais:paises) {
			if(pais.getId() == id) return pais;
		}
		return null;
	}

}
