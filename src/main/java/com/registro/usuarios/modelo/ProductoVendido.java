package com.registro.usuarios.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class ProductoVendido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer cantidad;
	private Integer precio;
	private String nombre;
	private String codigo;
	
	@ManyToOne
	@JoinColumn
	private Venta venta;

	public ProductoVendido(Integer cantidad, Integer precio, String nombre, String codigo, Venta venta) {
		super();
		this.cantidad = cantidad;
		this.precio = precio;
		this.nombre = nombre;
		this.codigo = codigo;
		this.venta = venta;
	}

	public ProductoVendido() {
		super();
	}

	 public Integer getTotal() {
	        return this.cantidad * this.precio;
	}
	 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
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

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

}
