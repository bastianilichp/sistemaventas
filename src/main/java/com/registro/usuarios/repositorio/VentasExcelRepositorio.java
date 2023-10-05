package com.registro.usuarios.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.registro.usuarios.controlador.dto.VentasExcelDTO;
import com.registro.usuarios.modelo.Venta;

public interface VentasExcelRepositorio extends CrudRepository<Venta, Integer> {


	
	

}
