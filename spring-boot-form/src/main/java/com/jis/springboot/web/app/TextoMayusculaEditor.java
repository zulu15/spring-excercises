package com.jis.springboot.web.app;

import java.beans.PropertyEditorSupport;

public class TextoMayusculaEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text.toUpperCase().trim());

	}

	
}
