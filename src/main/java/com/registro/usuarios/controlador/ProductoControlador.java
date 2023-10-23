package com.registro.usuarios.controlador;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.registro.usuarios.controlador.dto.MasVendidosDTO;
import com.registro.usuarios.modelo.Producto;
import com.registro.usuarios.modelo.ProductoVendido;
import com.registro.usuarios.repositorio.ProductosRepositorio;
import com.registro.usuarios.servicio.CustomerService;
import com.registro.usuarios.servicio.ProductoServicio;
import com.registro.usuarios.util.BaseResponse;

@Controller
@RequestMapping(path = "/productos")
public class ProductoControlador {

	private ProductosRepositorio productosRepositorio;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductoServicio productoServicio;

	public ProductoControlador(ProductosRepositorio productosRepositorio) {
		super();
		this.productosRepositorio = productosRepositorio;
	}

	@GetMapping(value = "/agregar")
	public String agregarProducto(Model model) {
		model.addAttribute("producto", new Producto());
		return "productos/agregar_producto";
	}

	@GetMapping(value = "/mostrar")
	public String mostrarProductos(Model model, @Param("palabraClave") String palabraClave) {
		List<Producto> productos = productoServicio.listAll(palabraClave);
		model.addAttribute("productos", productos);
		model.addAttribute("palabraClave", palabraClave);

		return "productos/ver_productos";
	}
	

	@PostMapping(value = "/eliminar")
	public String eliminarProducto(@ModelAttribute Producto producto, RedirectAttributes redirectAttrs) {
		redirectAttrs.addFlashAttribute("mensaje", "Eliminado correctamente").addFlashAttribute("clase", "warning");
		productosRepositorio.deleteById(producto.getId());
		return "redirect:/productos/mostrar";
	}

	@PostMapping(value = "/editar/{id}")
	public String actualizarProducto(@ModelAttribute @Validated Producto producto, BindingResult bindingResult,
			RedirectAttributes redirectAttrs) {
		if (bindingResult.hasErrors()) {
			if (producto.getId() != null) {
				return "productos/editar_producto";
			}
			return "redirect:/productos/mostrar";
		}
		Producto posibleProductoExistente = productosRepositorio.findFirstByCodigo(producto.getCodigo());

		if (posibleProductoExistente != null && !posibleProductoExistente.getId().equals(producto.getId())) {
			redirectAttrs.addFlashAttribute("mensaje", "Ya existe un producto con ese código")
					.addFlashAttribute("clase", "warning");
			return "redirect:/productos/agregar";
		}
		productosRepositorio.save(producto);
		redirectAttrs.addFlashAttribute("mensaje", "Editado correctamente").addFlashAttribute("clase", "success");
		return "redirect:/productos/mostrar";
	}

	@GetMapping(value = "/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable int id, Model model) {
		model.addAttribute("producto", productosRepositorio.findById(id).orElse(null));
		return "productos/editar_producto";
	}

	@PostMapping(value = "/agregar")
	public String guardarProducto(@ModelAttribute @Validated Producto producto, BindingResult bindingResult,
			RedirectAttributes redirectAttrs) {
		if (bindingResult.hasErrors()) {
			return "productos/agregar_producto";
		}
		if (productosRepositorio.findFirstByCodigo(producto.getCodigo()) != null) {
			redirectAttrs.addFlashAttribute("mensaje", "Ya existe un producto con ese código")
					.addFlashAttribute("clase", "warning");
			return "redirect:/productos/agregar";
		}
		productosRepositorio.save(producto);
		redirectAttrs.addFlashAttribute("mensaje", "Agregado correctamente").addFlashAttribute("clase", "success");
		return "redirect:/productos/agregar";
	}

	@GetMapping("/exportarExcel")
	public ResponseEntity<InputStreamResource> exportarExcel() throws Exception {
		ByteArrayInputStream stream = productoServicio.exportarExcel();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=listado_productos_total.xls");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}
	
	@GetMapping("/exportarExcelStock")
	public ResponseEntity<InputStreamResource> exportarExcelStock() throws Exception {
		ByteArrayInputStream stream = productoServicio.exportarExcelStock();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=stock_productos_criticos.xls");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));

	}

	@GetMapping(value = "/cargaMasiva")
	public ResponseEntity<BaseResponse> cargaMasiva(@RequestParam("file")MultipartFile importFile) {
		BaseResponse baseResponde = customerService.importCustomerData(importFile);		
		return new ResponseEntity<>(baseResponde,HttpStatus.OK);		
	}
	
	@GetMapping(value = "/stock")
	public String mostrarStock() {
		return "productos/ver_stock";
	}
	
	@GetMapping(value = "/buscarStockMenor")
	public String buscarStockMenor(Model model, @RequestParam Integer stockMenor) {
		List<Producto> productos = productoServicio.listadoStock(stockMenor);
		model.addAttribute("productos", productos);
		model.addAttribute("stockMenor", stockMenor);		
		return "productos/ver_stock";
	}
	
	@GetMapping(value = "/masVendidos")
	public String masVendidos(Model model, @Param("filtroVendido") String filtroVendido) {
		List<MasVendidosDTO> masVendidos = productoServicio.liistadoMasVendido(filtroVendido);
		model.addAttribute("masVendidos", masVendidos);
		return "productos/mas_vendido";
	}
	
	@GetMapping("/exportarExcelMasVendidos")
	public ResponseEntity<InputStreamResource> exportarExcelMasVendido() throws Exception {
		ByteArrayInputStream stream = productoServicio.exportarExcelMasVendido();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=ranking_mas_vendidos.xls");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));

	}
	@GetMapping(value = "/limpiarV")
	public String limpiarV() {			
		return "redirect:/productos/masVendidos";
	}
	
	@GetMapping(value = "/limpiarP")
	public String limpiarP() {			
		return "redirect:/productos/mostrar";
	}
	
	

}
