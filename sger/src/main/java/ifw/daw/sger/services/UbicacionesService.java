package ifw.daw.sger.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifw.daw.sger.models.Ubicaciones;
import ifw.daw.sger.repositories.UbicacionesRepository;

@Service
public class UbicacionesService {

	@Autowired
	UbicacionesRepository ubicacionesRepo;
	
	public List<Ubicaciones> mostrarUbicaciones() {
		return ubicacionesRepo.findAll();
	}
	
}
