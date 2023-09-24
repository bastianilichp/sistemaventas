package com.registro.usuarios.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.registro.usuarios.modelo.Venta;

public interface VentasRepositorio extends CrudRepository<Venta, Integer> {
}
