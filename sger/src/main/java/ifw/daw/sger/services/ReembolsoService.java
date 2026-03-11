package ifw.daw.sger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifw.daw.sger.models.EstadoReembolso;
import ifw.daw.sger.models.Reembolsos;
import ifw.daw.sger.models.Reservas;
import ifw.daw.sger.repositories.ReembolsoRepository;
import jakarta.transaction.Transactional;

@Service
public class ReembolsoService {

	@Autowired
	ReembolsoRepository reembolsoRepo;
	
	@Autowired
	CheckoutService checkoutServ;
	
	
	@Transactional
	public void guardarReembolso(Reservas reserva, Reembolsos reembolso) {
		
		if (reembolso != null) {
			reembolso.setCantidad(reserva.getTotal());
			reembolso.setEstado(EstadoReembolso.pendiente);
			reembolso.setReserva(reserva);
			reembolso.setUsuario(reserva.getUsuario());
			
			if (reembolso.getEstado() != EstadoReembolso.pendiente) {
				throw new RuntimeException("La reserva no puede ser reembolsada porque su reembolso ya se aprobo o rechazo");
			}
		} else {
			throw new RuntimeException("El reembolso no se ha podido efectuar porque es nulo");
		}
		reembolsoRepo.save(reembolso);
		
	}
	
}
