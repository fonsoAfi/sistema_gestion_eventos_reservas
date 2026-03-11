package ifw.daw.sger.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ifw.daw.sger.exceptions.RoleMismatchException;
import ifw.daw.sger.exceptions.UserAlreadyExistsException;
import ifw.daw.sger.models.Asistentes;
import ifw.daw.sger.models.Rol;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.repositories.AsistenteRepository;
import ifw.daw.sger.repositories.AuthRepository;
import jakarta.transaction.Transactional;

@Service
public class AuthService {
	
	@Autowired
	AuthRepository authRepo;
	
	@Autowired
	PasswordEncoder passEncoder;
	
	@Autowired
	AsistenteRepository asistenteRepo;

	
	public Usuarios mostrarCreds(String mail) {
		return authRepo.findCredsByMail(mail);
	}
	
	public int mostrarIdUsuario(String mail) {
		return authRepo.findIdByMail(mail);
	}
	
	public Usuarios getUsuario(int idUsuario) throws Exception {
		return authRepo.findById(idUsuario).orElseThrow(() -> new Exception("Usuario no encontrado"));
	}
	
	public void registrarUsuario(Usuarios usuario) {
		if (!authRepo.existsNombrePerfil(usuario.getNombrePerfil())) {
			if (!authRepo.existsEmail(usuario.getMail())) {
				Rol rol = usuario.getRol();
				System.out.println("ROL a insertar: " + rol);
					
				if (Rol.valueOf(rol.toString()) != null) {
					System.out.println(rol);
					usuario.setClave(passEncoder.encode(usuario.getClave()));
					usuario.setUltimoAcceso(LocalDateTime.now());
					usuario.setFechaRegistro(LocalDate.now());
					usuario.setRol(rol);
					if (rol.equals(Rol.ASISTENTES)) {
						Asistentes asistente = new Asistentes();
						asistente.setUsuario(usuario);
						asistente.setPuntosFidelidad(0);
						usuario.setAsistentes(asistente);
					} 
					authRepo.save(usuario);	
				} else {
					System.out.println("Rol invalido");
					throw new RoleMismatchException();
				}					
	
			} else {
				System.out.println("El mail ya existe");
				throw new UserAlreadyExistsException("*El usuario con email " + usuario.getMail() + " ya existe");
			}
		} else {
			System.out.println("El nombre de perfil ya existe");
			throw new UserAlreadyExistsException("*El usuario con el nombre de perfil " + usuario.getNombrePerfil() + " ya existe");
		}
	}
	
	
	public String obtenerPerfil(int id_usuario) {
		
		Usuarios usuario = authRepo.findById(id_usuario).orElseThrow();
		
		return "/profile/" + usuario.getRol();
	}
	
	@Transactional
	public Usuarios obtenerUsuario(String mail) {

		List<Usuarios> usuarios = authRepo.findUserByMail(mail);
		
		if (usuarios.isEmpty()) {
			throw new IllegalStateException("usuario no encontrado");
		}
				
		return usuarios.get(0);

	}
	

}
