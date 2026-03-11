package ifw.daw.sger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifw.daw.sger.exceptions.TipoOrganizadorMismatchException;
import ifw.daw.sger.exceptions.UserAlreadyExistsException;
import ifw.daw.sger.models.Organizadores;
import ifw.daw.sger.models.TipoOrganizador;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.repositories.AuthRepository;
import ifw.daw.sger.repositories.OrganizadorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class OrganizadorService {

	@Autowired
	AuthRepository authRepo;
	
	@Autowired
	OrganizadorRepository organizadorRepo;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	public boolean perfilCompleto(int idUsuario) {
		return organizadorRepo.existsByIdUsuario(idUsuario);
	}
	
	@Transactional
	public void registrarOrganizador(int idUsuario, String nombreOrganizador, String tipoOrganizador, int limiteEventos) {
		Usuarios usuario;
		
		Organizadores organizador;
		
		if (!organizadorRepo.existsOrganizador(nombreOrganizador)) {
			System.out.println("pasa");
			if (TipoOrganizador.esTipoValido(tipoOrganizador)) {
				System.out.println("pppassasa");
				usuario = entityManager.getReference(Usuarios.class, idUsuario);
				organizador = new Organizadores();
				organizador.setUsuario(usuario);
				organizador.setNombreOrganizador(nombreOrganizador);
				organizador.setTipoOrganizador(tipoOrganizador);
				organizador.setLimiteEventos(limiteEventos);
				
				usuario.setOrganizadores(organizador);
				
				organizadorRepo.save(organizador);
				
			} else {
				throw new TipoOrganizadorMismatchException();
			}
		} else {
			throw new UserAlreadyExistsException("El nombre de organizador indicado ya existe");
		}
		
		
	}
	
}
