package com.registro.usuarios.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.registro.usuarios.repositorio.VentasRepositorio;



@Controller
@RequestMapping(path = "/ventas")
public class VentasControlador {
	@Autowired
	VentasRepositorio ventasRepositorio;

    @GetMapping(value = "/")
    public String mostrarVentas(Model model) {
        model.addAttribute("ventas", ventasRepositorio.findAll());
        return "ventas/ver_ventas";
    }

}
