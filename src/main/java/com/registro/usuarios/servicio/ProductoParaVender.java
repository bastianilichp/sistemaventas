package com.registro.usuarios.servicio;

import com.registro.usuarios.modelo.Producto;

public class ProductoParaVender	 extends Producto {
    private Integer cantidad;



    public ProductoParaVender(Integer id, String nombre, String codigo, Integer precioCompra, Integer precioVenta,
			Integer stock, Integer cantidad) {
		super(id, nombre, codigo, precioCompra, precioVenta, stock);
		 this.cantidad = cantidad;
	}

	public ProductoParaVender(String nombre, String codigo, Integer precioCompra, Integer precioVenta, Integer stock, Integer cantidad) {
		super(nombre, codigo, precioCompra, precioVenta, stock);
		 this.cantidad = cantidad;
	}

	public void aumentarCantidad() {
        this.cantidad++;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Integer getTotal() {
        return this.getPrecioVenta() * this.cantidad;
    }
}
