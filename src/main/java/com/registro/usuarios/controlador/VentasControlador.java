package com.registro.usuarios.controlador;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.registro.usuarios.repositorio.VentasRepositorio;
import com.registro.usuarios.servicio.ProductoParaVender;
import com.registro.usuarios.servicio.VentasServicio;
import com.registro.usuarios.util.Utiles;



@Controller
@RequestMapping(path = "/ventas")
public class VentasControlador {
	
	@Autowired
	VentasRepositorio ventasRepositorio;
	
	@Autowired
	private VentasServicio ventasServicio;
	
	private String fecha;
	

	@GetMapping(value = "/")
    public String mostrarVentas(Model model) {		
		String fecha = this.fecha;
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hoy = sdf.format(new Date());	
		
		if(fecha == null) {
			model.addAttribute("ventas", ventasRepositorio.findFiltroFecha(hoy+ " 00:00:00",hoy + " 23:59:59"));
		}else {
			String[] fechaCompleta = fecha.split("-");
	    	String fechaDesde = Utiles.separarFechas(fechaCompleta[0]);
	    	String fechaHasta = Utiles.separarFechas(fechaCompleta[1]); 
			
			model.addAttribute("ventas", ventasRepositorio.findFiltroFecha(fechaDesde + " 00:00:00" ,fechaHasta + " 23:59:59"));
		}
        
        return "ventas/ver_ventas";
    }



	@GetMapping("/exportarExcel")
	public ResponseEntity<InputStreamResource> exportarExcel() throws Exception {
		ByteArrayInputStream stream = ventasServicio.exportarExcel();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=ventas.xls");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));

	}
	
	   
    @PostMapping(value = "/filtrar")   
    public String descTotal(@RequestParam String fechaFiltro, Model model) {    
    	this.fecha=fechaFiltro;
    	String[] fechaCompleta = fecha.split("-");
    	String fechaDesde = Utiles.separarFechas(fechaCompleta[0]);
    	String fechaHasta = Utiles.separarFechas(fechaCompleta[1]);   
    	model.addAttribute("ventas", ventasRepositorio.findFiltroFecha(fechaDesde + " 00:00:00" ,fechaHasta + " 23:59:59"));
    	model.addAttribute("fechasBusqueda", fechaDesde +"-"+ fechaHasta);
    	
    	return "ventas/ver_ventas";  	
    }



	public String getFecha() {
		return fecha;
	}



	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
    
    
    
}
