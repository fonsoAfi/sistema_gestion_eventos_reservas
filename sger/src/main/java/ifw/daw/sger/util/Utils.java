package ifw.daw.sger.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

	private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm");
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	
    public static String getFechaHoraFormateada(LocalDateTime fechaHora) {
    	if (fechaHora == null) {
    		throw new NullPointerException("La fecha-hora aportada es nula");
    	}
    	String strFechaHora = fechaHora.format(FORMATO_FECHA_HORA);
    	
    	return strFechaHora;
	}
    
    public static LocalDateTime parseFechaHora(String fechaHoraStr) {
        return LocalDateTime.parse(fechaHoraStr, FORMATO_FECHA_HORA);
    }
    
    
    public static String getFechaFormateada(LocalDate fecha) {
    	if (fecha == null) {
    		throw new NullPointerException("La fecha aportada es nula");
    	}
    	
    	String strFecha = fecha.format(FORMATO_FECHA);
    	
    	return strFecha;
	}
	
}
