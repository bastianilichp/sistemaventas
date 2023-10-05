package com.registro.usuarios.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.registro.usuarios.controlador.dto.VentasExcelDTO;
import com.registro.usuarios.modelo.Venta;


public interface VentasRepositorio extends CrudRepository<Venta, Integer> {
	

	 @Query("FROM Venta ORDER BY fechayhora DESC")
		List<Venta> findAll();
	
}
