package com.jis.springboot.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jis.springboot.web.app.service.IUploadFileService;

@SpringBootApplication
public class SpringBootSpringjpaApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSpringjpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		uploadFileService.borrarTodos();
		uploadFileService.init();;
		
		
		for(int i=0; i<2; i++) {
			System.out.println(passwordEncoder.encode("1234"));
		}
	}

}
