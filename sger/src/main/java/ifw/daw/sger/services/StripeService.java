package ifw.daw.sger.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import ifw.daw.sger.models.Checkout;
import ifw.daw.sger.models.EstadoCheckout;
import ifw.daw.sger.models.EstadoReserva;
import ifw.daw.sger.models.EstadoVenta;
import ifw.daw.sger.models.Eventos;
import ifw.daw.sger.models.Reservas;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.models.Ventas;
import ifw.daw.sger.repositories.CheckoutRepository;
import ifw.daw.sger.repositories.EventosRepository;
import ifw.daw.sger.repositories.ReservaRepository;
import ifw.daw.sger.repositories.VentasRepository;
import jakarta.transaction.Transactional;

@Service
public class StripeService {

	@Autowired
	CheckoutRepository checkoutRepo;
	
	@Autowired
	EventosRepository eventosRepo;
	
	@Autowired
	ReservaRepository reservasRepo;
	
	@Autowired
	VentasRepository ventasRepo;
	
	
	@Value("${stripe.key.secret}")
	private String secretKey;
	
	private String successUrl = "http://localhost:8080/profile/asistente/pago/exitoso";
	private String cancelUrl = "http://localhost:8080/profile/asistente/pago/cancelado";
	
	public Session crearSesionPago(Checkout checkout) throws StripeException {
		
		Stripe.apiKey = secretKey;
		
		Long amount = checkout.getMontoTotal()
				.multiply(new BigDecimal(100))
				.longValue();
		
		// No se corresponde a la cantidad de plazas si no a la cantidad de entradas
		Long quantity = 1L;
		
		Reservas reserva = checkout.getReserva();
		
		Usuarios usuario = checkout.getUsuario(); 
		
		String name = "Reserva " + reserva.getIdReserva() + ": " + reserva.getEvento().getTituloEvento();
		
		SessionCreateParams params = 
			SessionCreateParams.builder()
				.setMode(SessionCreateParams.Mode.PAYMENT)
				.setCustomerEmail(usuario.getMail()) // Si quiero que el mail quede puesto
				.setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
				.setCancelUrl(cancelUrl + "?session_id={CHECKOUT_SESSION_ID}")
				.putMetadata("idCheckout", Integer.toString(checkout.getIdCheckout()))
				.addLineItem(
					SessionCreateParams.LineItem.builder()
						.setQuantity(quantity)
						.setPriceData(
							SessionCreateParams.LineItem.PriceData.builder()
								.setCurrency(checkout.getMoneda().toLowerCase())
								.setUnitAmount(amount)
								.setProductData(
									SessionCreateParams.LineItem.PriceData.ProductData.builder()
										.setName(name)
										.build()
								)
								.build()
						)
						.build()
				)
				.build();
		Session session = Session.create(params);
		System.out.println(session.getId());
		return session;
	}
	
	@Transactional
	public void procesarPago(Session session) throws Exception{
		int idCheckout = Integer.valueOf(session.getMetadata().get("idCheckout"));
		
		Checkout checkout = checkoutRepo.findById(idCheckout).orElseThrow(() -> new Exception());
		checkout.setEstado(EstadoCheckout.completado);
		checkout.setFechaPago(LocalDateTime.now());
		
		Reservas reserva = checkout.getReserva();
		int plazasReservadas = reserva.getPlazasReservadas();
		reserva.setEstado(EstadoReserva.confirmada);
		
		Eventos evento = reserva.getEvento();
		int plazasDisponibles = evento.getPlazasDisponibles();
		
		plazasDisponibles = plazasDisponibles - plazasReservadas;
		evento.setPlazasDisponibles(plazasDisponibles);
		System.out.println(plazasDisponibles);

		Ventas venta = new Ventas();
		venta.setEstado(EstadoVenta.completada);
		venta.setFechaVenta(checkout.getFechaPago().toLocalDate());
		venta.setMontoTotal(checkout.getMontoTotal());
		venta.setEvento(evento);
		venta.setUsuario(checkout.getUsuario());
		
		checkoutRepo.save(checkout);
		eventosRepo.save(evento);
		reservasRepo.save(reserva);
		ventasRepo.save(venta);
	}
	
	@Transactional
	public void cancelarPago(Session session) throws Exception {
		int idCheckout = Integer.valueOf(session.getMetadata().get("idCheckout"));
		
		Checkout checkout = checkoutRepo.findById(idCheckout).orElseThrow(() -> new Exception());
		checkout.setEstado(EstadoCheckout.cancelado);
		
		Reservas reserva = checkout.getReserva();
		reserva.setEstado(EstadoReserva.pendiente);
		
		checkoutRepo.save(checkout);
		reservasRepo.save(reserva);
	}
	
}
