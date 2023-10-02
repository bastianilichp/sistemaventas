package com.registro.usuarios.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.registro.usuarios.modelo.Producto;

@Component("/productos/mostra/exportar")
public class ExportarExcel extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"listado_productos.xlsx\"");
		
		Sheet hoja = workbook.createSheet("Stock");
		Date fechaActual = new Date();
		Row filaTitulo = hoja.createRow(0);
		Cell celda = filaTitulo.createCell(0);
		celda.setCellValue("STOCK PRODUCTOS AL DIA: " + fechaActual);

		Row filaData = hoja.createRow(2);
		String[] columnas = { "Codigo Barra", "Descripción", "Precio Compra", "Precio Venta", "Stock" };

		for (int i = 0; i < columnas.length; i++) {
			celda = filaData.createCell(i);
			celda.setCellValue(columnas[i]);
		}
		List<Producto> listaP = (List<Producto>) model.get("productos");
		int numFila = 3;

		for (Producto producto : listaP) {
			filaData = hoja.createRow(numFila);
			filaData.createCell(0).setCellValue(producto.getCodigo());
			filaData.createCell(1).setCellValue(producto.getNombre());
			filaData.createCell(3).setCellValue(producto.getPrecioCompra());
			filaData.createCell(4).setCellValue(producto.getPrecioVenta());
			filaData.createCell(5).setCellValue(producto.getStock());
			numFila++;

		}
	}

}
