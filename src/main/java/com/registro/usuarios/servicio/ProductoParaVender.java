package com.registro.usuarios.servicio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.registro.usuarios.modelo.Producto;
import com.registro.usuarios.repositorio.VentasRepositorio;

public class ProductoParaVender	 extends Producto {
    private Integer cantidad;
    
    private Integer totalModificado;
    
    private Integer total;
    

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
    public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

    public Integer getTotal() {
        return this.getPrecioVenta() * this.cantidad;
    }

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getTotalModificado() {
		return totalModificado;
	}

	public void setTotalModificado(Integer totalModificado) {
		this.totalModificado = totalModificado;
	}

	

    
    
    
}
