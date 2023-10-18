package com.registro.usuarios.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.registro.usuarios.controlador.dto.MasVendidosDTO;
import com.registro.usuarios.modelo.ProductoVendido;

public interface ProductosVendidosRepositorio extends JpaRepository<ProductoVendido, Integer> {
	
	@Query(value = "SELECT sum(cantidad) as cantidad, nombre , codigo  "
	  		+ " from producto_vendido "
	  		+ " Group by nombre, codigo "
	  		+ "order by cantidad desc" , nativeQuery=true)
	  public List<MasVendidosDTO>findMasVendidos();
	
	
}
