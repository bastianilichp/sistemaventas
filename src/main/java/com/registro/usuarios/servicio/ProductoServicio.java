package com.registro.usuarios.servicio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registro.usuarios.controlador.dto.MasVendidosDTO;
import com.registro.usuarios.modelo.Producto;
import com.registro.usuarios.repositorio.ProductosRepositorio;
import com.registro.usuarios.repositorio.ProductosVendidosRepositorio;



@Service
public class ProductoServicio {
	
	@Autowired
	private ProductosRepositorio productoRepositorio;
	
	@Autowired
	private ProductosVendidosRepositorio productosVendidosRepositorio;
	
	public List<Producto> listAll(String palabraClave){
		if(palabraClave != null) {
			return productoRepositorio.findAll(palabraClave);
			
		}
		return productoRepositorio.findAll();
	}
	
	public List<Producto> listadoStock(Integer stockMenor){		
			return productoRepositorio.findStock(stockMenor);
			}
	
	
	public ByteArrayInputStream exportarExcel() throws Exception {
		String[] columnas = { "Codigo Barra", "Descripción", "Precio Compra", "Precio Venta", "Stock" };
		
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		Sheet sheet = workbook.createSheet("Productos");	
		Row row = sheet.createRow(0);
		
		for (int i = 0; i < columnas.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnas[i]);
		}
		List<Producto> listaP = productoRepositorio.findAll();
		int numFila = 1;
		for (Producto producto : listaP) {
			row = sheet.createRow(numFila);
			row.createCell(0).setCellValue(producto.getCodigo());
			row.createCell(1).setCellValue(producto.getNombre());
			row.createCell(2).setCellValue(producto.getPrecioCompra());
			row.createCell(3).setCellValue(producto.getPrecioVenta());
			row.createCell(4).setCellValue(producto.getStock());
			numFila++;
		}
			
		workbook.write(stream);
		workbook.close();
		
		return new ByteArrayInputStream(stream.toByteArray());
		
		
		
	}
	
	public ByteArrayInputStream exportarExcelStock() throws Exception {
		String[] columnas = { "Codigo Barra", "Descripción", "Precio Compra", "Precio Venta", "Stock" };
		
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		Sheet sheet = workbook.createSheet("Stock");	
		Row row = sheet.createRow(0);
		
		for (int i = 0; i < columnas.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnas[i]);
		}
		List<Producto> listaP = productoRepositorio.findStock();
		int numFila = 1;
		for (Producto producto : listaP) {
			row = sheet.createRow(numFila);
			row.createCell(0).setCellValue(producto.getCodigo());
			row.createCell(1).setCellValue(producto.getNombre());
			row.createCell(2).setCellValue(producto.getPrecioCompra());
			row.createCell(3).setCellValue(producto.getPrecioVenta());
			row.createCell(4).setCellValue(producto.getStock());
			numFila++;
		}
			
		workbook.write(stream);
		workbook.close();
		
		return new ByteArrayInputStream(stream.toByteArray());		
		
	}
	public ByteArrayInputStream exportarExcelMasVendido() throws Exception {
		String[] columnas = { "Cantidad", "Codigo", "Nombre" };
		
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		Sheet sheet = workbook.createSheet("Ranking");	
		Row row = sheet.createRow(0);
		
		for (int i = 0; i < columnas.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnas[i]);
		}
		List<MasVendidosDTO> lista = productosVendidosRepositorio.findMasVendidos();
		int numFila = 1;
		for (MasVendidosDTO producto : lista) {
			row = sheet.createRow(numFila);
			row.createCell(0).setCellValue(producto.getCantidad());
			row.createCell(1).setCellValue(producto.getCodigo());
			row.createCell(2).setCellValue(producto.getNombre());
			
			numFila++;
		}
			
		workbook.write(stream);
		workbook.close();
		
		return new ByteArrayInputStream(stream.toByteArray());		
		
	}
	
	public List<MasVendidosDTO> liistadoMasVendido(String filtroVendido){		
		if(filtroVendido != null) {
			return productosVendidosRepositorio.findMasVendidosFiltro(filtroVendido);
		}
		return productosVendidosRepositorio.findMasVendidos();
		}


}
