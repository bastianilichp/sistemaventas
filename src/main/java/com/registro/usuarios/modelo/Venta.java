package com.registro.usuarios.modelo;

import java.util.Set;

import javax.persistence.*;

import com.registro.usuarios.util.Utiles;

@Entity
public class Venta {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;
	    private String fechaYHora;
	    private Integer descuento;

	    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	    private Set<ProductoVendido> productos;

	    public Venta(Integer descuento) {
	        this.fechaYHora = Utiles.obtenerFechaYHoraActual();
	        this.descuento = descuento;
	    }
	    public Venta() {
	    	
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public Integer getTotal() {
	    	Integer total = 0;
	        for (ProductoVendido productoVendido : this.productos) {
	            total += productoVendido.getTotal();
	        }
	        return total;
	    }

	    public String getFechaYHora() {
	        return fechaYHora;
	    }

	    public void setFechaYHora(String fechaYHora) {
	        this.fechaYHora = fechaYHora;
	    }

	    public Set<ProductoVendido> getProductos() {
	        return productos;
	    }

	    public void setProductos(Set<ProductoVendido> productos) {
	        this.productos = productos;
	    }

		public Integer getDescuento() {
			return descuento;
		}

		public void setDescuento(Integer descuento) {
			this.descuento = descuento;
		}
	    
	

}
