package com.registro.usuarios.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.registro.usuarios.modelo.Producto;

public interface ProductosRepositorio extends CrudRepository<Producto, Integer> {

    Producto findFirstByCodigo(String codigoProducto);
}
