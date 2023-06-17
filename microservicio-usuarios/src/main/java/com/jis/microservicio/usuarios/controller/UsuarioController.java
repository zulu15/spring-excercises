package com.jis.microservicio.usuarios.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jis.microservicio.usuarios.models.entity.Usuario;
import com.jis.microservicio.usuarios.service.IUsuario;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	private Logger log = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private IUsuario usuarioService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ApplicationContext applicationContext;

	@GetMapping("/crash")
	public void crash() {
		((ConfigurableApplicationContext) applicationContext).close();
	}

	@GetMapping
	public Map<String, List<Usuario>> findAll() {
		return Collections.singletonMap("usuarios", usuarioService.findAll());
	}

	@GetMapping("/{usuarioId}")
	public Usuario findUsuarioById(@PathVariable Long usuarioId) {
		return usuarioService.findById(usuarioId);
	}

	@GetMapping("/")
	public List<Usuario> findUsuariosByIds(@RequestParam List<Long> ids) {
		return usuarioService.findAllByIds(ids);
	}

	@PostMapping
	public Usuario save(@RequestBody Usuario usuario) {
		log.info(usuario.toString());
		usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
		return usuarioService.save(usuario);
	}

	@PutMapping("/{usuarioId}")
	public Usuario update(@PathVariable Long usuarioId, @RequestBody Usuario usuario) {
		Usuario usuarioInDatabase = usuarioService.findById(usuarioId);
		usuarioInDatabase.setNombre(usuario.getNombre());
		usuarioInDatabase.setEmail(usuario.getEmail());
		usuarioInDatabase.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
		return usuarioService.save(usuarioInDatabase);
	}

	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long usuarioId) {
		Usuario usuarioInDatabase = usuarioService.findById(usuarioId);
		if (usuarioInDatabase == null)
			return ResponseEntity.notFound().build();

		usuarioService.delete(usuarioInDatabase);
		Map<String, String> responseMensaje = new HashMap<>();
		responseMensaje.put("mensaje", "El usuario se eliminó con éxito!");
		return new ResponseEntity<>(responseMensaje, HttpStatus.OK);

	}
	
	@GetMapping("/authorized")
	public Map<String, Object> authorized(@RequestParam String code){
		log.debug("Code: "+code);
		return Collections.singletonMap("code", code);
		
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> loginByEmail(@RequestParam String email){
		Optional<Usuario> usuario = usuarioService.findByEmail(email);
		if(usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}
		return ResponseEntity.notFound().build();
	}

}
