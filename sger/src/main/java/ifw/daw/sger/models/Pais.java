package ifw.daw.sger.models;

public enum Pais {

	AF("Afganistán"),
	DE("Alemania"),
	AD("Andorra"),
	AO("Angola"),
	SA("Arabia Saudita"),
	AR("Argentina"),
	AU("Australia"),
	AT("Austria"),
	BE("Bélgica"),
	BO("Bolivia"),
	BR("Brasil"),
	CA("Canadá"),
	CL("Chile"),
	CN("China"),
	CO("Colombia"),
	KR("Korea del Sur"),
	CR("Costa Rica"),
	CU("Cuba"),
	DK("Dinamarca"),
	EC("Ecuador"),
	EG("Egipto"),
	SV("El Salvador"),
	ES("España"),
	US("Estados Unidos"),
	FR("Francia"),
	GR("Grecia"),
	GT("Guatemala"),
	HN("Honduras"),
	IN("India"),
	ID("Indonesia"),
	IE("Irlanda"),
	IL("Israel"),
	IT("Italia"),
	JM("Jamaica"),
	JP("Japón"),
	MX("México"),
	NI("Nicaragua"),
	NO("Noruega"),
	NZ("Nueva Zelanda"),
	NL("Paises Bajos"),
	PA("Panamá"),
	PY("Paraguay"),
	PE("Perú"),
	PL("Polonia"),
	PT("Portugal"),
	GB("Reino Unido"),
	DO("República Dominicana"),
	RU("Rusia"),
	ZA("Sudáfrica"),
	SE("Suecia"),
	CH("Suiza"),
	TR("Turquía"),
	UA("Ucrania"),
	UY("Uruguay"),
	VE("Venezuela"),
	VT("Vietnam");
	
	private final String nombre;
	
	Pais(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
