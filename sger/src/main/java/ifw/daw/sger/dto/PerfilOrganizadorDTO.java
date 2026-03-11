package ifw.daw.sger.dto;

public record PerfilOrganizadorDTO(
		String nombrePerfil,
		String mail,
		String nombreOrganizador,
		String tipoOrganizador,
		int limiteEventos
) implements PerfilDTO {

}

