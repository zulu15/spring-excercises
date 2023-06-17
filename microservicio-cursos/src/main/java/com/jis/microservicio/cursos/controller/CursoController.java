package com.jis.microservicio.cursos.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jis.microservicio.cursos.models.Usuario;
import com.jis.microservicio.cursos.models.entity.Curso;
import com.jis.microservicio.cursos.models.services.ICursoService;

import feign.FeignException;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

	@Autowired
	private ICursoService cursoService;

	@Autowired
	private Environment environment;
	
	
	@GetMapping
	public ResponseEntity<?> listCursos() {
		Map<String, Object> response = new HashMap<>();
		response.put("cursos", cursoService.findAll());
		String podName = environment.getProperty("MY_POD_NAME");
		String podIp = environment.getProperty("MY_POD_IP");
		response.put("pod_data", podName + ": "+podIp);
		response.put("config.texto", environment.getProperty("config.texto"));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{cursoId}")
	public ResponseEntity<?> findCursoById(@PathVariable Long cursoId, @RequestHeader(value = "Authorization", required = true)	String token) {
		Curso c =  cursoService.findByIdConUsuarios(cursoId, token);
		Map<String, String> response = new HashMap<>();
		if(c == null) {
			response.put("error", "El curso con id: " +cursoId+ " no existe");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
		}
		 return new ResponseEntity<>(c, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> saveNewCurso(@RequestBody Curso curso) {
		Map<String, String> response = new HashMap<>();
		if (curso.getNombre() == null || curso.getNombre().strip().isEmpty()) {
			response.put("mensaje", "No se pudo crear el curso");
			response.put("error", "El nombre del curso no puede estar vacío");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
		}

		cursoService.saveOrUpdate(curso);
		response.put("mensaje", "El curso " + curso.getNombre() + " fue creado con éxito");
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@PutMapping("/{cursoId}")
	public ResponseEntity<?> saveNewCurso(@PathVariable Long cursoId, @RequestBody Curso curso) {
		Curso cursoInDatabase = cursoService.findById(cursoId);
		Map<String, String> response = new HashMap<>();

		if (cursoInDatabase == null) {
			response.put("mensaje", "No se pudo actualizar el curso");
			response.put("error", "El curso no se encontró en la base de datos");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
		}
		if (curso.getNombre() == null || curso.getNombre().strip().isEmpty()) {
			response.put("mensaje", "No se pudo actualizar el curso");
			response.put("error", "El nombre del curso no puede estar vacío");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
		}
		cursoInDatabase.setNombre(curso.getNombre());
		cursoService.saveOrUpdate(cursoInDatabase);
		response.put("mensaje", "El curso " + cursoInDatabase.getNombre() + " fue actualizado con éxito");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{cursoId}")
	public ResponseEntity<?> deleteCursoById(@PathVariable Long cursoId) {
		Map<String, String> response = new HashMap<>();
		Curso curso = cursoService.findById(cursoId);
		if (curso == null) {
			response.put("mensaje", "No se pudo eliminar el curso");
			response.put("error", "El curso no existe");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
		}

		cursoService.deleteCurso(curso);
		response.put("mensaje", "El curso " + curso.getNombre() + " fue eliminado con éxito");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
	}

	
	
	@PutMapping("/asignar-usuario/{cursoId}")
	public ResponseEntity<?> asignarUsuarioAcurso(@PathVariable Long cursoId, @RequestBody Usuario u, @RequestHeader(value = "Authorization", required = true)	String token){
		Optional<Usuario> o;
		try {
			o = cursoService.asignarUsuario(u, cursoId, token);
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Error al asignar usuario a curso: "+e.getMessage()));
		}
		if(o.isPresent()) {
			return ResponseEntity.ok(o.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/crear-usuario/{cursoId}")
	public ResponseEntity<?> crearUsuarioAcurso(@PathVariable Long cursoId, @RequestBody Usuario u, @RequestHeader(value = "Authorization", required = true)	String token){
		Optional<Usuario> o;
		try {
			o = cursoService.crearUsuario(u, cursoId, token);
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Error al crear usuario: "+e.getMessage()));
		}
		if(o.isPresent()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/eliminar-usuario-curso/{cursoId}")
	public ResponseEntity<?> desasignarUsuarioDeCurso(@PathVariable Long cursoId, @RequestBody Usuario u, @RequestHeader(value = "Authorization", required = true)	String token){
		try {
			cursoService.desasignarUsuario(u, cursoId, token);
			return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Se desasigno el usuario correctamente"));
			
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Error al desasignar usuario: "+e.getMessage()));
		}
	
	}
	
	
	@DeleteMapping("/eliminar-usuario/{usuarioId}")
	public ResponseEntity<?> desasignarUsuarioDeCursoPorUsuarioId(@PathVariable Long usuarioId){
		try {
			cursoService.desasignarUsuario(usuarioId);
			return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Se desasigno el usuario correctamente"));
			
		} catch (FeignException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Error al desasignar usuario: "+e.getMessage()));
		}
	
	}
	
	
}
