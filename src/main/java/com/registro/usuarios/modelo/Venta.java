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

	    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	    private Set<ProductoVendido> productos;

	    public Venta() {
	        this.fechaYHora = Utiles.obtenerFechaYHoraActual();
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
	

}
