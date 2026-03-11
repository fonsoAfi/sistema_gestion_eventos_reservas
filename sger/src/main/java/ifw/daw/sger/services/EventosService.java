package ifw.daw.sger.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ifw.daw.sger.models.Eventos;
import ifw.daw.sger.models.Organizadores;
import ifw.daw.sger.repositories.EventosRepository;
import ifw.daw.sger.repositories.OrganizadorRepository;
import jakarta.transaction.Transactional;

@Service
public class EventosService {

	@Autowired
	OrganizadorRepository organizadorRepo;
	
	@Autowired
	EventosRepository eventosRepo;
	
	
	public List<Eventos> getEventos() {
		return eventosRepo.findAll();
	}
	
	public Page<Eventos> getPaginasEventos(int pagina, int totalPaginas) {
		Pageable pageable = PageRequest.of(pagina, totalPaginas);
		return eventosRepo.findAll(pageable);
	}
	
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Eventos> getEventos(int idUsuario) {
		return eventosRepo.findEventosOrganizadorCompleto(idUsuario);
	}
	
	public Eventos getEvento(int idEvento) {
		return eventosRepo.findById(idEvento).get();
	}
	
	public void crearNuevoEvento(Eventos evento, int idUsuario) {
		String tituloEvento = evento.getTituloEvento();
		if (!eventosRepo.findByTituloEvento(tituloEvento).isPresent()) {
			Organizadores organizador = organizadorRepo
				.findUsuarioByIdUsuario(idUsuario)
				.orElseThrow(() -> new IllegalStateException(
					"El usuario no tiene organizador registrado"));
			evento.setOrganizador(organizador);
			
			Eventos eventoBD = eventosRepo.save(evento);
			if (eventoBD != null) {
				System.out.println("Se guardo el evento en la BD");
			}
		} else {
			throw new RuntimeException("El evento con el nombre indicado ya existe");
		}
	}
	
	@Transactional
	public void actualizarEvento(Eventos eventoForm, Principal principal) {
		Eventos eventoBD = eventosRepo.findById(eventoForm.getIdEvento())
				.orElseThrow(() -> new RuntimeException("Evento no encontrado"));
		
		if (!eventoBD.getOrganizador().getUsuario().getMail()
			.equals(principal.getName())) {
			throw new RuntimeException("Usuario no autorizado");
		}
		
		eventoBD.setTituloEvento(eventoForm.getTituloEvento());
		eventoBD.setDescripcion(eventoForm.getDescripcion());
		eventoBD.setFechaInicio(eventoForm.getFechaInicio());
		eventoBD.setFechaFin(eventoForm.getFechaFin());
		eventoBD.setCategoria(eventoForm.getCategoria());
		eventoBD.setUbicacion(eventoForm.getUbicacion());
		
		eventosRepo.save(eventoBD);
		
	}
	
	@Transactional
	public void borrarEvento(int idEvento, Principal principal) {
		Eventos evento =  getEvento(idEvento);
		
		if (evento == null) {			
			throw new RuntimeException("Evento no encontrado");
		}
		
		if (!evento.getOrganizador().getUsuario().getMail()
				.equals(principal.getName())) {
				throw new RuntimeException("Usuario no autorizado");
		}
		
		eventosRepo.delete(evento);
		
	}
	
}
