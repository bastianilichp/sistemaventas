package com.registro.usuarios.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.registro.usuarios.modelo.Producto;
import com.registro.usuarios.modelo.ProductoVendido;
import com.registro.usuarios.modelo.Venta;
import com.registro.usuarios.repositorio.ProductosRepositorio;
import com.registro.usuarios.repositorio.ProductosVendidosRepositorio;
import com.registro.usuarios.repositorio.VentasRepositorio;
import com.registro.usuarios.servicio.ProductoParaVender;
import com.registro.usuarios.servicio.ProductoServicio;

import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

@Controller
@RequestMapping(path = "/vender")
public class VenderControlador {
	
    @Autowired
    private ProductosRepositorio productosRepository;
    @Autowired
    private VentasRepositorio ventasRepository;
    @Autowired
    private ProductosVendidosRepositorio productosVendidosRepository;
    
    @Autowired
	private ProductoServicio productoServicio;
    
    private Integer descuento;

    @PostMapping(value = "/quitar/{indice}")
    public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
            carrito.remove(indice);
            this.guardarCarrito(carrito, request);
        }
        return "redirect:/vender/";
    }

    private void limpiarCarrito(HttpServletRequest request) {
        this.guardarCarrito(new ArrayList<>(), request);
    }

    @GetMapping(value = "/limpiar")
    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        this.limpiarCarrito(request);
        redirectAttrs
                .addFlashAttribute("mensaje", "Venta cancelada")
                .addFlashAttribute("clase", "info");
        this.descuento=0;
        return "redirect:/vender/";
    }

    @PostMapping(value = "/terminar")
    public String terminarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) throws Exception {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        // Si no hay carrito o está vacío, regresamos inmediatamente
        if (carrito == null || carrito.size() <= 0) {
            return "redirect:/vender/";
        }
        Venta v = ventasRepository.save(new Venta(this.descuento));
        // Recorrer el carrito
        for (ProductoParaVender productoParaVender : carrito) {
            // Obtener el producto fresco desde la base de datos
            Producto p = productosRepository.findById(productoParaVender.getId()).orElse(null);
            if (p == null) continue; // Si es nulo o no existe, ignoramos el siguiente código con continue
            // Le restamos existencia
            p.restarStock(productoParaVender.getCantidad());
            // Lo guardamos con la existencia ya restada
            productosRepository.save(p);
            // Creamos un nuevo producto que será el que se guarda junto con la venta
            ProductoVendido productoVendido = new ProductoVendido(productoParaVender.getCantidad(), productoParaVender.getPrecioVenta(), productoParaVender.getNombre(), productoParaVender.getCodigo(), v);
            // Y lo guardamos
            productosVendidosRepository.save(productoVendido);
        }
    	
        // Al final limpiamos el carrito
        this.limpiarCarrito(request);
        // e indicamos una venta exitosa
        redirectAttrs
                .addFlashAttribute("mensaje", "Venta realizada correctamente")
                .addFlashAttribute("clase", "success");
        this.descuento=0;
        return "redirect:/vender/";
    }

    @GetMapping(value = "/")
    public String interfazVender(Model model, HttpServletRequest request) {
    	model.addAttribute("producto", new Producto());
        Integer total = 0;
        Integer subTotal = 0;
        
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        for (ProductoParaVender p: carrito) {       	      	
        		total += p.getTotal();    
        		subTotal += p.getTotal();  
        }        	
        if(descuento != null) {
        	total = total - descuento;
        	
        }else {
        	descuento = 0;
        }
        
        model.addAttribute("descuento", descuento);
        model.addAttribute("total", total);
        model.addAttribute("subTotal", subTotal);
       
        return "vender/vender";
    }

    private ArrayList<ProductoParaVender> obtenerCarrito(HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = (ArrayList<ProductoParaVender>) request.getSession().getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        return carrito;
    }

    private void guardarCarrito(ArrayList<ProductoParaVender> carrito, HttpServletRequest request) {
        request.getSession().setAttribute("carrito", carrito);
    }

    @PostMapping(value = "/agregar")
    public String agregarAlCarrito(@ModelAttribute Producto producto, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        Producto productoBuscadoPorCodigo = productosRepository.findFirstByCodigo(producto.getCodigo());
        if (productoBuscadoPorCodigo == null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto con el código " + producto.getCodigo() + " no existe")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        }
        if (productoBuscadoPorCodigo.sinStock()) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto está agotado")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        }
        boolean encontrado = false;
        for (ProductoParaVender productoParaVenderActual : carrito) {
            if (productoParaVenderActual.getCodigo().equals(productoBuscadoPorCodigo.getCodigo())) {
                productoParaVenderActual.aumentarCantidad();
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
        	carrito.add(new ProductoParaVender(productoBuscadoPorCodigo.getId(), productoBuscadoPorCodigo.getNombre(), productoBuscadoPorCodigo.getCodigo(), productoBuscadoPorCodigo.getPrecioCompra(), productoBuscadoPorCodigo.getPrecioVenta(), productoBuscadoPorCodigo.getStock(), 1));
        	
        }
        this.guardarCarrito(carrito, request);
        return "redirect:/vender/";
    }
    
    @PostMapping(value = "/cantidad/{indice}")   
    public String sumarCantidad(@RequestParam("cantidad") int cantidad,  HttpServletRequest request, @PathVariable int indice) {    	
    	ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
    	carrito.get(indice).setCantidad(cantidad); 	
    	this.guardarCarrito(carrito, request);
    	return "redirect:/vender/";    	
    }
    
    @PostMapping(value = "/descuento")   
    public String descTotal(@RequestParam("descuento") int descuento,  HttpServletRequest request,Model model) {    
    	this.descuento = descuento;    	
    	return "redirect:/vender/";    	
    }

	public Integer getDescuento() {
		return descuento;
	}
	
	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}

    
}
