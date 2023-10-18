package com.registro.usuarios.controlador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public interface MasVendidosDTO {

	 Integer getCantidad();
	 String getNombre();
	 String getCodigo();

	

}
