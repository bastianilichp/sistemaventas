package com.registro.usuarios.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String nombre;
	private String codigo;
	private Integer precioCompra;
	private Integer precioVenta;
	private Integer stock;

	public Producto(Integer id, String nombre, String codigo, Integer precioCompra, Integer precioVenta,
			Integer stock) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.stock = stock;
	}

	public Producto(String nombre, String codigo, Integer precioCompra, Integer precioVenta, Integer stock) {
		super();
		this.nombre = nombre;
		this.codigo = codigo;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.stock = stock;
	}

	public Producto() {
		super();
	}

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

	public Integer getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Integer precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Integer getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Integer precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
    public boolean sinStock() {
        return this.stock <= 0;
    }
    
    public void restarStock(Integer stock) {
        this.stock -= stock;
    }


}
