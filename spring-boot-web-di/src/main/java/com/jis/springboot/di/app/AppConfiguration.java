package com.jis.springboot.di.app;


import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.jis.springboot.di.app.models.ItemFactura;
import com.jis.springboot.di.app.models.Producto;
import com.jis.springboot.di.app.services.ISaludo;
import com.jis.springboot.di.app.services.SaludoServiceDefaultImpl;
import com.jis.springboot.di.app.services.SaludoServiceAuxImpl;

@Configuration
public class AppConfiguration {

	@Bean("saludoServiceAux")
	public ISaludo registrarSaludoAux() {
		return new SaludoServiceAuxImpl();
	}
	
	@Bean("saludoServiceDefault")
	@Primary
	public ISaludo registrarSaludoDefault() {
		return new SaludoServiceDefaultImpl();
	}
	
	
	@Bean
	public List<ItemFactura> registrarItemsFactura(){
	
		ItemFactura item1 = new ItemFactura( new Producto("Gasewosa Pepsi 3Lts", 300.0), 1);
		ItemFactura item2 = new ItemFactura( new Producto("Galletas Traviata", 175.0), 1);
		ItemFactura item3 = new ItemFactura( new Producto("Dulce de Leche", 200.0), 2);
		
		return Arrays.asList(item1, item2, item3);
	}
}
