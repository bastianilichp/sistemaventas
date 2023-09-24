package com.registro.usuarios.controlador;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.registro.usuarios.modelo.Producto;
import com.registro.usuarios.repositorio.ProductosRepositorio;




@Controller
@RequestMapping(path = "/productos")
public class ProductoControlador {
	
	
	 private ProductosRepositorio productosRepositorio;	 
	 
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
	    public String mostrarProductos(Model model) {
	        model.addAttribute("productos", productosRepositorio.findAll());
	        return "productos/ver_productos";
	    }
	    
	    @PostMapping(value = "/eliminar")
	    public String eliminarProducto(@ModelAttribute Producto producto, RedirectAttributes redirectAttrs) {
	        redirectAttrs
	                .addFlashAttribute("mensaje", "Eliminado correctamente")
	                .addFlashAttribute("clase", "warning");
	        productosRepositorio.deleteById(producto.getId());
	        return "redirect:/productos/mostrar";
	    }
	    
	    @PostMapping(value = "/editar/{id}")
	    public String actualizarProducto(@ModelAttribute @Validated Producto producto, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
	        if (bindingResult.hasErrors()) {
	            if (producto.getId() != null) {
	                return "productos/editar_producto";
	            }
	            return "redirect:/productos/mostrar";
	        }
	        Producto posibleProductoExistente = productosRepositorio.findFirstByCodigo(producto.getCodigo());

	        if (posibleProductoExistente != null && !posibleProductoExistente.getId().equals(producto.getId())) {
	            redirectAttrs
	                    .addFlashAttribute("mensaje", "Ya existe un producto con ese código")
	                    .addFlashAttribute("clase", "warning");
	            return "redirect:/productos/agregar";
	        }
	        productosRepositorio.save(producto);
	        redirectAttrs
	                .addFlashAttribute("mensaje", "Editado correctamente")
	                .addFlashAttribute("clase", "success");
	        return "redirect:/productos/mostrar";
	    }
	    
	    @GetMapping(value = "/editar/{id}")
	    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
	        model.addAttribute("producto", productosRepositorio.findById(id).orElse(null));
	        return "productos/editar_producto";
	    }

	    @PostMapping(value = "/agregar")
	    public String guardarProducto(@ModelAttribute @Validated Producto producto, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
	        if (bindingResult.hasErrors()) {
	            return "productos/agregar_producto";
	        }
	        if (productosRepositorio.findFirstByCodigo(producto.getCodigo()) != null) {
	            redirectAttrs
	                    .addFlashAttribute("mensaje", "Ya existe un producto con ese código")
	                    .addFlashAttribute("clase", "warning");
	            return "redirect:/productos/agregar";
	        }
	        productosRepositorio.save(producto);
	        redirectAttrs
	                .addFlashAttribute("mensaje", "Agregado correctamente")
	                .addFlashAttribute("clase", "success");
	        return "redirect:/productos/agregar";
	    }
	    

}
