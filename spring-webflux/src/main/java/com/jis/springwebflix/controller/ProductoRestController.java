package com.jis.springwebflix.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jis.springwebflix.models.dao.ProductRepository;
import com.jis.springwebflix.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

	private Logger log = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductRepository productDao;

	@GetMapping
	public Flux<Producto> listar() {
		Flux<Producto> productos = productDao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		}).doOnNext(p -> log.info(p.getNombre()));
		
		return productos;
	}
	
	@GetMapping("/{id}")
	public Mono<Producto> findById(@PathVariable String id){
		return productDao.findById(id);
	}
}
