package com.jis.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jis.reactor.models.Usuario;

import reactor.core.publisher.Flux;


@SpringBootApplication
public class SpringReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringReactorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux<Usuario> nombres = Flux.just("Joaquin Sanchez","Marcos Galperin","Pedro Somola","Bruce Willis","Luca Maldonado","Juan Perez","Bruce Lee")
			.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
			.filter(usuario -> usuario.getNombre().toLowerCase().equals("bruce"))
			.doOnNext(usuario -> {
				if(usuario.getNombre().isEmpty()) throw new RuntimeException("El nombre no puede estar vacío");
				System.out.println(usuario);
			}).map(usuario -> {
				String nombreToLowerCase = usuario.getNombre().toLowerCase();
				String apellidoToLowerCase = usuario.getApellido().toLowerCase();

				usuario.setNombre(nombreToLowerCase);
				usuario.setApellido(apellidoToLowerCase);
				return usuario;
			});
		
		nombres.subscribe(usuario -> log.info(usuario.toString()), error -> log.error(error.getMessage()), new Runnable() {
			
			@Override
			public void run() {

					log.info("Termino");
				
			}
		});
		
	}

}
