package com.jis.microservicio.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jis.microservicio.usuarios.models.dao.UsuarioDAO;
import com.jis.microservicio.usuarios.models.entity.Usuario;
import com.jis.microservicio.usuarios.restclient.CursoFeignClient;

@Service
public class UsuarioService implements IUsuario {

	@Autowired
	private CursoFeignClient cursoFeignClient;
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return usuarioDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		return usuarioDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Usuario usuario) {
		usuarioDAO.delete(usuario);
		cursoFeignClient.desasignarUsuarioDeCursoPorUsuarioId(usuario.getId());

	}

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		return usuarioDAO.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAllByIds(Iterable<Long> ids) {
		return usuarioDAO.findAllById(ids);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return usuarioDAO.findByEmail(email);
	}

}
