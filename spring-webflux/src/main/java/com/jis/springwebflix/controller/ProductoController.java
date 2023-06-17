package com.jis.springwebflix.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import com.jis.springwebflix.models.dao.ProductRepository;
import com.jis.springwebflix.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {

	private Logger log = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductRepository productDao;

	@GetMapping({ "/listar" })
	public String listar(Model model) {
		Flux<Producto> productos = productDao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		});
		productos.subscribe(producto -> log.info(producto.getNombre()));
		model.addAttribute("productos", productos);
		return "listar";
	}
	
	@GetMapping({ "/listar-async"})
	public String listarDataDriven(Model model) {
		Flux<Producto> productos = productDao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).delayElements(Duration.ofSeconds(1));
		
		productos.subscribe(producto -> log.info(producto.getNombre()));
		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 1));
		return "listar";
	}
	
	
	@GetMapping({ "/listar-chunk"})
	public String listarChunked(Model model) {
		Flux<Producto> productos = productDao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).repeat(5000);


		model.addAttribute("productos", productos);
		return "listar";
	}


}
