package com.jis.microservicio.usuarios.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url="${msvc.cursos.url}")
public interface CursoFeignClient {
	
	@DeleteMapping("/eliminar-usuario/{usuarioId}")
	public void desasignarUsuarioDeCursoPorUsuarioId(@PathVariable Long usuarioId);
	
}
