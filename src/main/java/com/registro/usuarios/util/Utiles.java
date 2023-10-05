
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
}
