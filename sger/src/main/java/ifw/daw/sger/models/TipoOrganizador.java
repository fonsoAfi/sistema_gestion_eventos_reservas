package ifw.daw.sger.models;

public enum TipoOrganizador {
	EMPRESA_PRIVADA, ONG_CULTURAL, ONG, INDIVIDUAL,
	PARTICULAR, VIRTUAL, ENTIDAD_PUBLICA;
	
    public String toString() {
		return name();
	}
    
    public static boolean esTipoValido(String valor) {
        boolean encontrado = false;
    	for (TipoOrganizador tipo : TipoOrganizador.values()) {
        	if (tipo.toString().equals(valor)) {
        		encontrado = true;
        		break;
        	}
        }
    	return encontrado;
    }
}
