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

import com.registro.usuarios.modelo.Producto;
import com.registro.usuarios.modelo.ProductoVendido;
import com.registro.usuarios.modelo.Venta;
import com.registro.usuarios.repositorio.VentasExcelRepositorio;
import com.registro.usuarios.repositorio.VentasRepositorio;
import com.registro.usuarios.controlador.dto.VentasExcelDTO;

@Service
public class VentasServicio {
	
	@Autowired
	VentasRepositorio ventasRepositorio;
	
	public ByteArrayInputStream exportarExcel() throws Exception {
		String[] columnas = { "Fecha Venta","Codigo Barra", "Descripción","Cantidad","Precio Unidad", "Total"};
		
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		Sheet sheet = workbook.createSheet("Stock");	
		Row row = sheet.createRow(0);
		
		for (int i = 0; i < columnas.length; i++) { 
			Cell cell = row.createCell(i);
			cell.setCellValue(columnas[i]);
		}
		List<Venta> ventas = ventasRepositorio.findAll();
		int numFila = 1;
		for (Venta venta : ventas) {
			
			for (ProductoVendido vp : venta.getProductos()) {
				row = sheet.createRow(numFila);
				row.createCell(0).setCellValue(vp.getVenta().getFechaYHora());
				row.createCell(1).setCellValue(vp.getCodigo());
				row.createCell(2).setCellValue(vp.getNombre());	
				row.createCell(3).setCellValue(vp.getCantidad());	
				row.createCell(4).setCellValue(vp.getPrecio());	
				row.createCell(5).setCellValue(vp.getTotal());
				
				numFila++;
			}
//			row.createCell(1).setCellValue(venta.getCodigo());
//			row.createCell(2).setCellValue(venta.getNombre());
			
			
			
			
		}
			
		workbook.write(stream);
		workbook.close();
		
		
		return new ByteArrayInputStream(stream.toByteArray());
		
		
		
	}

}
