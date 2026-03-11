package ifw.daw.sger.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifw.daw.sger.exceptions.MotivoIsNullOrEmptyException;
import ifw.daw.sger.models.Cancelaciones;
import ifw.daw.sger.models.Reservas;
import ifw.daw.sger.repositories.CancelacionRepository;

@Service
public class CancelacionService {
	
	@Autowired
	CancelacionRepository cancelacionRepo;
	
	public void guardarCancelacion(Reservas reserva, Cancelaciones cancelacion) throws RuntimeException {
		
		if (cancelacion != null) {
			cancelacion.setReserva(reserva);
			cancelacion.setFechaCancelacion(LocalDateTime.now());
			if (cancelacion.getMotivo() == null || cancelacion.getMotivo().isEmpty()) {
				throw new MotivoIsNullOrEmptyException();
			}
			if (cancelacion.getFechaCancelacion() == null) {				
				throw new RuntimeException("No se ha aportado fecha de cancelacion");
			}
		} else {
			throw new RuntimeException("Debes rellenar los campos para completar la cancelacion");
		}

		cancelacionRepo.save(cancelacion);
	}

}
