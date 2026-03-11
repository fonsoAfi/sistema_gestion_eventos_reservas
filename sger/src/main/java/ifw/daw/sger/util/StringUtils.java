package ifw.daw.sger.util;

public class StringUtils {
	
	private static final int LONG_MAX_TITULOS_EVENTOS = 18;

	public static String eliminarPalabra(String titulo, String palabra) {

	    if (titulo == null || titulo.isBlank()) return titulo;

	    return titulo.replaceAll("\\b" + palabra + "\\b", "").replaceAll("\\s+", " ").trim();
	}
	
	public static String acortarTituloEvento(String titulo) {
		if (titulo == null || titulo.isBlank()) {
			return titulo;
		}
		
		if (titulo.length() <= LONG_MAX_TITULOS_EVENTOS) {
			return titulo;
		}
		
        String[] palabras = titulo.trim().split("\\s+");

        StringBuilder iniciales = new StringBuilder();

        for (String palabra : palabras) {
        	if (palabra.length() > 3) {
        		iniciales.append(
        				Character.toUpperCase(palabra.charAt(0))
        		).append(".");
        	} else {
        		eliminarPalabra(titulo, palabra);
        	}
        }

        return iniciales.toString();
	}
}
