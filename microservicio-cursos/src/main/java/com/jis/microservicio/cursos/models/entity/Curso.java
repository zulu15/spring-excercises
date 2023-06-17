package com.jis.microservicio.cursos.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jis.microservicio.cursos.models.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "cursos")
public class Curso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curso_id")
	private List<CursoUsuario> cursoUsuario;

	@Transient
	private List<Usuario> usuarios;
	
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<CursoUsuario> getCursoUsuario() {
		return cursoUsuario;
	}

	public void setCursoUsuario(List<CursoUsuario> cursoUsuario) {
		this.cursoUsuario = cursoUsuario;
	}

	public void addCursoUsuario(CursoUsuario ca) {
		cursoUsuario.add(ca);
	}

	public void removeCursoUsuario(CursoUsuario ca) {
		cursoUsuario.remove(ca);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Curso(String nombre) {
		this.nombre = nombre;
	}

	public Curso() {
		cursoUsuario = new ArrayList<>();
		usuarios = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + "]";
	}

}
