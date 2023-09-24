package com.registro.usuarios.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.registro.usuarios.modelo.ProductoVendido;

public interface ProductosVendidosRepositorio extends CrudRepository<ProductoVendido, Integer> {

}
