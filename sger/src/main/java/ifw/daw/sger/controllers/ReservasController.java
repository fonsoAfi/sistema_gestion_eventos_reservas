package ifw.daw.sger.controllers;

import java.security.Principal;

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

import ifw.daw.sger.models.Eventos;
import ifw.daw.sger.models.Reservas;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.services.AuthService;
import ifw.daw.sger.services.EventosService;
import ifw.daw.sger.services.ReservaService;

@Controller
@RequestMapping("/profile")
public class ReservasController {
	
	@Autowired
	AuthService authServ;
	
	@Autowired
	EventosService eventosServ;
	
	@Autowired
	ReservaService reservasServ;
	
	
	@GetMapping("/asistente/reservas/{id}")
	@ResponseBody
	public String mostrarFormReservas(@PathVariable("id") int id, Model model, Principal principal) {
		
		String mail = principal.getName();
		Eventos evento = eventosServ.getEvento(id);
		Usuarios usuario = authServ.obtenerUsuario(mail);

		model.addAttribute("gastosGestion", ReservaService.GASTOS_GESTION);
		model.addAttribute("evento", evento);
		model.addAttribute("usuario", usuario);
		model.addAttribute("reserva", new Reservas());
		
		return "/asistente/reservas/form_reserva";
	}
	
	@PostMapping("/asistente/reservas/nueva")
	@ResponseBody
	public String procesarReserva(@ModelAttribute("reserva") Reservas reserva, 
								  @RequestParam("idEvento") int idEvento, Principal principal) {
		
		reservasServ.registrarReserva(reserva, idEvento, principal);
		int idReserva = reserva.getIdReserva();
		
		return "redirect:/profile/asistente/checkout/form_checkout/" + idReserva;
	}
	
}
