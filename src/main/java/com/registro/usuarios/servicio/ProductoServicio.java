package com.registro.usuarios.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registro.usuarios.modelo.Producto;
import com.registro.usuarios.repositorio.ProductosRepositorio;

@Service
public class ProductoServicio {
	
	@Autowired
	private ProductosRepositorio productoRepositorio;
	
	public List<Producto> listAll(String palabraClave){
		if(palabraClave != null) {
			return productoRepositorio.findAll(palabraClave);
			
		}
		return productoRepositorio.findAll();
	}

}
