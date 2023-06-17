package com.jis.microservicio.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.jis.microservicio.cursos.models.Usuario;

@FeignClient(name = "msvc-usuarios", url = "${msvc.usuarios.url}")
public interface UsuarioClientRest {

	@GetMapping("/{usuarioId}")
	Usuario findUsuarioById(@PathVariable Long usuarioId, @RequestHeader(value = "Authorization", required = true)	String token);

	@PostMapping
	Usuario save(@RequestBody Usuario usuario, @RequestHeader(value = "Authorization", required = true)	String token);

	
	@GetMapping("/")
	List<Usuario> findUsuariosByIds(@RequestParam Iterable<Long> ids, @RequestHeader(value = "Authorization", required = true)	String token);
}
