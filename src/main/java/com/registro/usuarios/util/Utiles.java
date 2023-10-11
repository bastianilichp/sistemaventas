
package com.registro.usuarios.util;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utiles {

	public static String obtenerFechaYHoraActual() {
		TimeZone myTimeZone = TimeZone.getTimeZone("America/Santiago");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleDateFormat.setTimeZone(myTimeZone);
		String dateTime = simpleDateFormat.format(new Date());	

		return (dateTime);
	}
	
	public static String obtenerFechaActual() {
		TimeZone myTimeZone = TimeZone.getTimeZone("America/Santiago");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		simpleDateFormat.setTimeZone(myTimeZone);
		String dateTime = simpleDateFormat.format(new Date());	

		return (dateTime);
	}
	
	public static String separarFechas(String fecha) {		
	 	String[] fecha1 = fecha.split("/");	 	
    	String mes = fecha1[0].trim();
    	String dia = fecha1[1].trim();
    	String anio = fecha1[2].trim();
    	String fechaFinal = anio+"-"+mes+"-"+dia;
		
		
		return fechaFinal;
		
	}
}
