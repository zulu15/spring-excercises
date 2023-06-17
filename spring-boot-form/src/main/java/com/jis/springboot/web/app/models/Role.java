package com.jis.springboot.web.app.models;

public class Role {
	private Integer id;
	private String nombre;
	private String codigo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Role(Integer id, String nombre, String codigo) {

		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
	}
	public Role() {

	}
	
	
	

}
