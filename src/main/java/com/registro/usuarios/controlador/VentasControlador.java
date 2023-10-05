package com.registro.usuarios.controlador;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.registro.usuarios.repositorio.VentasRepositorio;
import com.registro.usuarios.servicio.ProductoParaVender;
import com.registro.usuarios.servicio.VentasServicio;



@Controller
@RequestMapping(path = "/ventas")
public class VentasControlador {
	
	@Autowired
	VentasRepositorio ventasRepositorio;
	
	@Autowired
	private VentasServicio ventasServicio;
	
	

	@GetMapping(value = "/")
    public String mostrarVentas(Model model) {
        model.addAttribute("ventas", ventasRepositorio.findAll());
        return "ventas/ver_ventas";
    }

	@GetMapping("/exportarExcel")
	public ResponseEntity<InputStreamResource> exportarExcel() throws Exception {
		ByteArrayInputStream stream = ventasServicio.exportarExcel();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=ventas.xls");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));

	}
}
