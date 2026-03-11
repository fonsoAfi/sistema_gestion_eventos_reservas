package ifw.daw.sger.controllers;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import ifw.daw.sger.models.Checkout;
import ifw.daw.sger.models.EstadoCheckout;
import ifw.daw.sger.models.MetodoPago;
import ifw.daw.sger.models.Reservas;
import ifw.daw.sger.repositories.ReservaRepository;
import ifw.daw.sger.services.CheckoutService;
import ifw.daw.sger.services.StripeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class CheckoutController {
	
	@Autowired
	ReservaRepository reservaRepo;
	
	@Autowired
	CheckoutService checkoutServ;
	

	@Autowired
	StripeService stripeServ;

	@GetMapping("/asistente/checkout/form_checkout/{idReserva}")
	@ResponseBody
	@Operation(summary = "Muestra la pagina de intento de pago",
	   		   description = "Muestra la pagina de intento de pago ")
	public String mostrarCheckout(@PathVariable("idReserva") int idReserva, Model model) {
		
		Reservas reserva = reservaRepo.findById(idReserva).orElseThrow(
				() -> new RuntimeException("Reserva no encontrada")
		);
		
		Checkout checkout = new Checkout();
		checkout.setFechaCreacion(LocalDateTime.now());
		checkout.setEstado(EstadoCheckout.pendiente);
		
		model.addAttribute("reserva", reserva);
		model.addAttribute("checkout", checkout);
		model.addAttribute("metodosPago", MetodoPago.values());
		
		return "/asistente/checkout/form_checkout";
	}
	
	@PostMapping("/asistente/checkout/nuevo")
	@ResponseBody
	@Operation(summary = "Procesa el intento de pago",
	   		   description = "Procesa el intento de pago y crea una sesion de pago")
	public String procesarCheckout(@ModelAttribute("checkout") Checkout checkout,
								   @RequestParam("idReserva") int idReserva, Principal principal,
								   HttpServletRequest request) throws StripeException {
		
		checkoutServ.registrarCheckout(checkout, idReserva, request, principal);
		
		String urlPasarela = stripeServ.crearSesionPago(checkout).getUrl();
		System.out.println(urlPasarela);
		
		return "redirect:" + urlPasarela; 
	}
	
	@GetMapping("/asistente/pago/exitoso")
	@ResponseBody
	@Operation(summary = "Muestra la pagina de pago exitoso")
	public String pagoExitoso(@RequestParam("session_id") String sessionId, Model model) {
		model.addAttribute("sessionId", sessionId);
		return "/asistente/pago/exitoso";
	}
	
	@GetMapping("/asistente/pago/cancelado")
	@ResponseBody
	@Operation(summary = "Muestra la pagina de pago cancelado")
	public String pagoCancelado(@RequestParam("session_id") String sessionId, Model model) throws Exception {
		Session session = Session.retrieve(sessionId);
		stripeServ.cancelarPago(session);
		model.addAttribute("sessionId", sessionId);
		return "/asistente/pago/cancelado";
	}
}
