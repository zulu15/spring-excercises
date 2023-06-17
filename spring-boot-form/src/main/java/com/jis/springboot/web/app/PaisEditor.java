package com.jis.springboot.web.app;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jis.springboot.web.app.services.PaisService;

@Component
public class PaisEditor extends PropertyEditorSupport {

	@Autowired
	private PaisService paisServiceImpl;

	@Override
	public void setAsText(String paisId) throws IllegalArgumentException {

		try {
			setValue(paisServiceImpl.obtenerPorId(Integer.parseInt(paisId)));
		} catch (NumberFormatException e) {
			setValue(null);
		}

	}

}
