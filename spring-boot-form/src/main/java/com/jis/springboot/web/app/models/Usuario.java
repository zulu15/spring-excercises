package com.jis.springboot.web.app.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.jis.springboot.web.app.validator.Regex;
import com.jis.springboot.web.app.validator.Requerido;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;


	
public class Usuario {
	
    //@Pattern(regexp = "[0-9]{2}[.,][0-9]{3}[.,][0-9]{3}[-][A-Z]{1}")
	@Regex
	private String id;
	
	//@NotEmpty(message = "El username no puede estar vacío")
	//@Size(min = 5, max = 20, message = "El username debe contener mínimo 5 caractetes y máximo 20")
	@Requerido
	private String username;
	
	@Requerido
	@Email(message = "El formato del correo es inválido")
	private String email;
	
	@Requerido
	private String password;
	
	@NotNull
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;
	
	private Boolean isHabilitado;
	
	@NotNull
	@Min(5)
	@Max(20)
	private Integer cuenta;

	@NotNull
	private Pais pais;
	
	@NotEmpty
	private List<Role> roles;
	
	@NotEmpty
	private String genero;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCuenta() {
		return cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}
	

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Usuario(String username, String email, String password, String id, Integer cuenta, Date fechaNacimiento) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.id = id;
		this.cuenta = cuenta;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Usuario() {
	
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Boolean getIsHabilitado() {
		return isHabilitado;
	}

	public void setIsHabilitado(Boolean isHabilitado) {
		this.isHabilitado = isHabilitado;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}



	
	
	
}
