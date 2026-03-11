package ifw.daw.sger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ifw.daw.sger.dto.CambiarClaveDTO;
import ifw.daw.sger.dto.PerfilAdministradorDTO;
import ifw.daw.sger.dto.PerfilAsistenteDTO;
import ifw.daw.sger.dto.PerfilDTO;
import ifw.daw.sger.dto.PerfilOrganizadorDTO;
import ifw.daw.sger.exceptions.ClaveMismatchException;
import ifw.daw.sger.models.Asistentes;
import ifw.daw.sger.models.Organizadores;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.repositories.AsistenteRepository;
import ifw.daw.sger.repositories.AuthRepository;

@Service
public class UsuarioService {

	
	@Autowired
	PasswordEncoder passEncoder;
	
	@Autowired
	AsistenteRepository asistenteRepo;
	
	private final AuthRepository authRepo;
	
	public UsuarioService(AuthRepository authRepo) {
		this.authRepo = authRepo;
	}
	
	public Usuarios obtenerUsuario(Authentication auth) {
		return authRepo.findCredsByMail(auth.getName());
	}
	
	public PerfilDTO obtenerPerfil(Authentication auth) {
		Usuarios usuario = authRepo.findCredsByMail(auth.getName());
		
		return switch (usuario.getRol()) {
			case ASISTENTES -> {
				Asistentes asistente = new Asistentes();
				asistente.setUsuario(usuario);
				//asistenteRepo.save(asistente);
				yield new PerfilAsistenteDTO(
						usuario.getNombrePerfil(), usuario.getMail()
				);
			}
			case ORGANIZADORES -> {
				Organizadores organizador = new Organizadores();
				organizador.setLimiteEventos(1);
				yield new PerfilOrganizadorDTO(
						usuario.getNombrePerfil(), usuario.getMail(), organizador.getNombreOrganizador(), organizador.getTipoOrganizador(), organizador.getLimiteEventos()
				);
			}
			case ADMINISTRADORES -> new PerfilAdministradorDTO(usuario.getNombrePerfil(), usuario.getMail());
		};
	}
	
	public boolean compararClaves(String clave, String claveRepetida) {
		return (clave.equals(claveRepetida));
	}
	
	//implementar el cambio de clave
	public void cambiarClave(CambiarClaveDTO claveDTO, Authentication auth) {
		Usuarios usuarioDB = authRepo.findCredsByMail(auth.getName());
		if (usuarioDB != null) {
			
			System.out.println("Clave en la BD: " + usuarioDB.getClave());
			System.out.println("Nueva clave: " + passEncoder.encode(claveDTO.getClaveNueva()));
			if (!passEncoder.matches(claveDTO.getClaveNueva(), usuarioDB.getClave())) {
				authRepo.updateClave(passEncoder.encode(claveDTO.getClaveNueva()), usuarioDB.getMail());
			} else {
				throw new ClaveMismatchException();
			}
		} else {
			throw new NullPointerException("Las credenciales no se encontraron");
		}
	}
	
}
