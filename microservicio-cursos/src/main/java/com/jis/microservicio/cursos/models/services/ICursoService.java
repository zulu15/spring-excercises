package com.jis.microservicio.cursos.models.services;

import java.util.List;
import java.util.Optional;
import com.jis.microservicio.cursos.models.Usuario;
import com.jis.microservicio.cursos.models.entity.Curso;
public interface ICursoService {
	
	List<Curso> findAll();
	Curso findById(Long cursoId);
	void deleteCurso(Curso curso);
	Curso saveOrUpdate(Curso curso);
	Optional<Usuario> asignarUsuario(Usuario u, Long cursoId, String token);
	void desasignarUsuario(Usuario u, Long cursoId, String token);
	Optional<Usuario> crearUsuario(Usuario u, Long cursoId, String token);
	Curso findByIdConUsuarios(Long cursoId, String token);
	void desasignarUsuario(Long usuarioId);
	
}
