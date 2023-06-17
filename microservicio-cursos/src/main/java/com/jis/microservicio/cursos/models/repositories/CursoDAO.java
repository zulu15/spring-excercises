package com.jis.microservicio.cursos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jis.microservicio.cursos.models.entity.Curso;

public interface CursoDAO extends JpaRepository<Curso, Long> {
	@Modifying
	@Query("delete from CursoUsuario c where c.usuarioId = ?1")
	void desasignarUsuario(Long usuarioId);
	
}
