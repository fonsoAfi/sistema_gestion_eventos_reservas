package ifw.daw.sger.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifw.daw.sger.models.Asistentes;
import ifw.daw.sger.models.Eventos;
import ifw.daw.sger.repositories.AsistenteRepository;
import ifw.daw.sger.repositories.EventosRepository;
import ifw.daw.sger.repositories.ReservaRepository;

@Service
public class AsistenteService {

	@Autowired
	AsistenteRepository asistRepo;
	
	@Autowired
	EventosRepository eventosRepo;
	
	@Autowired
	ReservaRepository reservaRepo;
	
	public void registrarAsistente(int idUsuario) {
		Asistentes asistente = new Asistentes();
		asistente.setIdUsuario(idUsuario);
		
		asistRepo.save(asistente);
	}
	
	public List<Eventos> mostrarEventosAsistente() {
		return eventosRepo.findAll();
	}
	
	public List<Eventos> mostrarEventosRecientes() {
	    return eventosRepo.findEventosAnioActual();
	}
	
}
