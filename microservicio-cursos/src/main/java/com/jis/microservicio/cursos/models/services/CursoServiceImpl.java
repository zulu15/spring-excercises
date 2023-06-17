package com.jis.microservicio.cursos.models.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jis.microservicio.cursos.clients.UsuarioClientRest;
import com.jis.microservicio.cursos.models.Usuario;
import com.jis.microservicio.cursos.models.entity.Curso;
import com.jis.microservicio.cursos.models.entity.CursoUsuario;
import com.jis.microservicio.cursos.models.repositories.CursoDAO;

@Service
public class CursoServiceImpl implements ICursoService {

	@Autowired
	private CursoDAO cursoDAO;

	@Autowired
	private UsuarioClientRest usuarioClientRest;

	@Override
	@Transactional(readOnly = true)
	public List<Curso> findAll() {
		return cursoDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Curso findById(Long cursoId) {
		return cursoDAO.findById(cursoId).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteCurso(Curso curso) {
		cursoDAO.delete(curso);
	}

	@Override
	@Transactional(readOnly = false)
	public Curso saveOrUpdate(Curso curso) {
		return cursoDAO.save(curso);
	}

	@Override
	public Optional<Usuario> asignarUsuario(Usuario u, Long cursoId, String token) {
		Optional<Curso> optionalCurso = cursoDAO.findById(cursoId);
		if(optionalCurso.isPresent()) {
			Usuario usuarioMsvc = usuarioClientRest.findUsuarioById(u.getId(), token);
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuarioMsvc.getId());
			optionalCurso.get().addCursoUsuario(cursoUsuario);
			cursoDAO.save(optionalCurso.get());
			return Optional.of(usuarioMsvc);
		}
		return Optional.empty();
	}

	@Override
	public void desasignarUsuario(Usuario u, Long cursoId, String token) {
		Optional<Curso> optionalCurso = cursoDAO.findById(cursoId);
		if(optionalCurso.isPresent()) {
			Usuario usuarioMsvc = usuarioClientRest.findUsuarioById(u.getId(), token);
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuarioMsvc.getId());
			optionalCurso.get().removeCursoUsuario(cursoUsuario);
			cursoDAO.save(optionalCurso.get());
		}

	}

	@Override
	public Optional<Usuario> crearUsuario(Usuario u, Long cursoId, String token) {
		Optional<Curso> optionalCurso = cursoDAO.findById(cursoId);
		if(optionalCurso.isPresent()) {
			Usuario usuarioMsvc = usuarioClientRest.save(u, token);
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuarioMsvc.getId());
			optionalCurso.get().addCursoUsuario(cursoUsuario);
			cursoDAO.save(optionalCurso.get());
			return Optional.of(usuarioMsvc);
		}
		return Optional.empty();
	}

	@Override
	@Transactional(readOnly = true)
	public Curso findByIdConUsuarios(Long cursoId, String token) {
		Optional<Curso> optionalCurso = cursoDAO.findById(cursoId);
		if(optionalCurso.isPresent()) {
			Curso curso = optionalCurso.get();
			List<Usuario> usuariosByCurso = usuarioClientRest.findUsuariosByIds(curso.getCursoUsuario().stream().map(usuario -> usuario.getUsuarioId()).collect(Collectors.toList()), token);
			System.out.println("Usuarios length: "+usuariosByCurso.size());
			curso.setUsuarios(usuariosByCurso);
			return curso;
		}
		return null;
	}

	@Override
	@Transactional
	public void desasignarUsuario(Long usuarioId) {
		cursoDAO.desasignarUsuario(usuarioId);
		
	}

}
