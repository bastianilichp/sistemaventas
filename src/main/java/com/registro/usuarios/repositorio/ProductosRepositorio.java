package com.registro.usuarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.registro.usuarios.modelo.Producto;
import java.util.List;


public interface ProductosRepositorio extends JpaRepository<Producto, Integer> {

    Producto findFirstByCodigo(String codigoProducto);
    
    @Query( value = "SELECT * FROM producto where nombre LIKE %?1% "
    		+ " or codigo LIKE %?1% ", 
    		  nativeQuery = true)
    public List<Producto> findAll(String palabraClave);

    
    
}
