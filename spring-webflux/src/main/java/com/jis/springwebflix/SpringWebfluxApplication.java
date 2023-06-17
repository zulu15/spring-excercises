package com.jis.springwebflix;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.jis.springwebflix.models.dao.ProductRepository;
import com.jis.springwebflix.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringWebfluxApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringWebfluxApplication.class);

	@Autowired
	private ProductRepository productDAO;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		mongoTemplate.dropCollection("productos").subscribe(p -> System.out.println(p));

		Flux.just(new Producto("TV Panasonic 12 pulgadas", 150.000), new Producto("Play Station 5", 180000d),
				new Producto("Samsubng A13", 80000d), new Producto("Notebook HP AS2", 200000d),
				new Producto("Iphone 12", 75000d), new Producto("TCL", 900000d)).flatMap(producto -> {
					producto.setCreatedDate(new Date());
					return productDAO.save(producto);

				}).subscribe(producto -> log.info("Se guardo: " + producto.getNombre()));

	}

}
