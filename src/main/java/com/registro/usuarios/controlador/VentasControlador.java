package com.registro.usuarios.controlador;

import java.io.ByteArrayInputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.registro.usuarios.modelo.ProductoVendido;
import com.registro.usuarios.modelo.Venta;
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

	private String fechaDetalle;



	@GetMapping(value = "/")
	public String mostrarVentas(Model model) {		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hoy = sdf.format(new Date());
		model.addAttribute("ventas",
					ventasRepositorio.findFiltroFecha(hoy + " 00:00:00", hoy + " 23:59:59"));
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
	public String filtrarFechaVentas(@RequestParam String fechaDesde,@RequestParam String fechaHasta, Model model) {	
		String fDesde = Utiles.separarFechas(fechaDesde);
		String fHasta = Utiles.separarFechas(fechaHasta);
		model.addAttribute("ventas",
				ventasRepositorio.findFiltroFecha(fDesde + " 00:00:00", fHasta + " 23:59:59"));
		

		return "ventas/ver_ventas";
	}

	@GetMapping(value = "/resumen")
	public String resumenDiario(Model model) {
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hoy = sdf.format(new Date());
		List<Venta> ventas = ventasRepositorio.findFiltroFecha(hoy + " 00:00:00", hoy + " 23:59:59");
		Integer subTotal = 0;
		Integer cantidad = 0;
		Integer desc = 0;

		for (Venta v : ventas) {
			subTotal += v.getTotal();
			desc += v.getDescuento();

			for (ProductoVendido pv : v.getProductos()) {
				cantidad += pv.getCantidad();
			}
		}
		model.addAttribute("fecha", hoy);
		model.addAttribute("subTotal", formatoNumero.format(subTotal));
		model.addAttribute("desc", formatoNumero.format(desc));
		model.addAttribute("total", formatoNumero.format(subTotal - desc));
		model.addAttribute("cantidad", cantidad);

		return "ventas/resumen_ventas";
	}

	@PostMapping(value = "/filtroResumen")
	public String resumenFiltrarVentas(@RequestParam String fechaDesde,@RequestParam String fechaHasta, Model model) {
		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		
		String fDesde= Utiles.separarFechas(fechaDesde);
		String fHasta = Utiles.separarFechas(fechaHasta);		
		List<Venta> ventas = ventasRepositorio.findFiltroFecha(fDesde + " 00:00:00", fHasta + " 23:59:59");
		Integer subTotal = 0;
		Integer cantidad = 0;
		Integer desc = 0;

		for (Venta v : ventas) {
			subTotal += v.getTotal();
			desc += v.getDescuento();

			for (ProductoVendido pv : v.getProductos()) {
				cantidad += pv.getCantidad();
			}
		}
		model.addAttribute("fecha", fDesde +" / " +fHasta);
		model.addAttribute("subTotal", formatoNumero.format(subTotal));
		model.addAttribute("desc", formatoNumero.format(desc));
		model.addAttribute("total", formatoNumero.format(subTotal - desc));
		model.addAttribute("cantidad", cantidad);
		


		return "ventas/resumen_ventas";
	}

	public String getFechaDetalle() {
		return fechaDetalle;
	}

	public void setFechaDetalle(String fechaDetalle) {
		this.fechaDetalle = fechaDetalle;
	}

	

}
