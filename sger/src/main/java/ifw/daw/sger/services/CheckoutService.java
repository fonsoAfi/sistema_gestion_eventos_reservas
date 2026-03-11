package ifw.daw.sger.services;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifw.daw.sger.models.Checkout;
import ifw.daw.sger.models.EstadoCheckout;
import ifw.daw.sger.models.Reservas;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.repositories.CheckoutRepository;
import ifw.daw.sger.repositories.ReservaRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CheckoutService {

	@Autowired
	ReservaRepository reservaRepo;
	
	@Autowired
	AuthService authServ;
	
	@Autowired
	CheckoutRepository checkoutRepo;
	
	
	public void registrarCheckout(Checkout checkout, int idReserva, HttpServletRequest request ,Principal principal) {
		
		
		Reservas reserva = reservaRepo.findById(idReserva).orElseThrow(
				() -> new RuntimeException("Reserva no encontrada")
		);
		
		if (checkoutRepo.existsByReservaAndEstado(reserva, EstadoCheckout.completado)) {
			throw new IllegalStateException("La reserva ya se ha pagado");
		}
		
		Usuarios usuario = authServ.obtenerUsuario(principal.getName());
		
		checkout.setMontoTotal(reserva.getTotal());
		checkout.setFechaCreacion(LocalDateTime.now());
		checkout.setTiempoExpiracion(checkout.getFechaCreacion().plusMinutes(30));
		
		if (checkout.getFechaCreacion().isAfter(checkout.getTiempoExpiracion())) {
			checkout.setEstado(EstadoCheckout.expirado);
		}
		
		checkout.setEstado(EstadoCheckout.pendiente);
		checkout.setMoneda("EUR");
		checkout.setIpOrigen("127.0.0.1");
		checkout.setUsuario(usuario);
		checkout.setReserva(reserva);
		
		checkoutRepo.save(checkout);
	}
	
	public Checkout obtenerCheckoutReserva(Reservas reserva, EstadoCheckout estado) {
		return checkoutRepo.findByReservaAndEstado(reserva, estado).orElseThrow();
	}
}
