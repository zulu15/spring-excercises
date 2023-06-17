package com.jis.springboot.web.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private Integer cantidad;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Producto producto;
	

	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public ItemFactura(Long id, Integer cantidad) {

		this.id = id;
		this.cantidad = cantidad;
	}
	public ItemFactura() {

	}	
	
	public Double calcularImporte() {
		return producto.getPrecio() * getCantidad();
	}
}
	
	
	

