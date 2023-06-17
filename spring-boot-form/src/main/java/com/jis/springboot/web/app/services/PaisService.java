package com.jis.springboot.web.app.services;

import java.util.List;
import com.jis.springboot.web.app.models.Pais;

public interface PaisService {
	public List<Pais> listarPaises();
	public Pais obtenerPorId(Integer id);

}
