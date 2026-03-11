package ifw.daw.sger.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import ifw.daw.sger.models.Cancelaciones;
import ifw.daw.sger.models.EstadoReserva;
import ifw.daw.sger.models.Reembolsos;
import ifw.daw.sger.models.Reservas;
import ifw.daw.sger.services.AsistenteService;
import ifw.daw.sger.services.AuthService;
import ifw.daw.sger.services.CancelacionService;
import ifw.daw.sger.services.EventosService;
import ifw.daw.sger.services.ReembolsoService;
import ifw.daw.sger.services.ReservaService;
import ifw.daw.sger.util.GeneradorTicketPdf;
import io.swagger.v3.oas.annotations.Operation;

@Controller
@RequestMapping("/profile")
public class AsistenteController {

    private final EventosService eventosService;

	@Autowired
	AsistenteService asistenteServ;
	
	@Autowired
	ReservaService reservaServ;
	
	@Autowired
	AuthService authServ;
	
	@Autowired
	CancelacionService cancelacionServ;
	
	@Autowired
	ReembolsoService reembolsoServ;


    AsistenteController(EventosService eventosService) {
        this.eventosService = eventosService;
    }
	
	
	
	@GetMapping("/asistente/dashboardAsistente")
	@ResponseBody
	@Operation(summary = "Muestra el dasboard del asistente",
			   description = "Muestra las eventos recientes, reservas segun estado de reserva y todos los eventos")
	public String mostrarDashboardAsistente(@RequestParam(required = false, defaultValue="reservados") String vista,
											@RequestParam(required = false) EstadoReserva estado,
											Model model, Authentication auth) {
		
		String mail = auth.getName();
		int idUsuario = authServ.obtenerUsuario(mail).getIdUsuario();
		String nombreReal = authServ.obtenerUsuario(mail).getNombreReal();
		int reservasConfirmadas = reservaServ.getNumeroReservas(idUsuario, EstadoReserva.confirmada);
		int reservasPendientes = reservaServ.getNumeroReservas(idUsuario, EstadoReserva.pendiente);
		model.addAttribute("nombreReal", nombreReal);
		model.addAttribute("numReservasConfirmadas", reservasConfirmadas);
		model.addAttribute("numReservasPendientes", reservasPendientes);

		model.addAttribute("eventosRecientes", asistenteServ.mostrarEventosRecientes());
		List<Reservas> listaReservas;
		
		if (estado != null) {
			listaReservas = reservaServ.getReservasUsuarios(idUsuario, estado);
			if (listaReservas.isEmpty()) {
				model.addAttribute("msg", "No se han encontrado reservas con el estado: " + estado);
			}
			model.addAttribute("listaReservas", listaReservas);
		} else {
			listaReservas = reservaServ.getAllUserReservas(idUsuario);
			model.addAttribute("listaReservas", listaReservas);
		}
	
		model.addAttribute("vista", vista);
		model.addAttribute("eventosReservados", reservaServ.getReservasUsuarios(idUsuario, EstadoReserva.confirmada));			
		model.addAttribute("todosEventos", eventosService.getEventos());			
		
		return "asistente/dashboardAsistente";
	}
	
	@PostMapping("/asistente/entradas")
	@ResponseBody
	@Operation(summary = "Muestra reservas segun estado de reserva ",
	   		   description = "Muestra reservas segun estado de reserva")
	public String mostrarReservasPorEstado(@RequestParam(required = false) EstadoReserva estado,
										   Authentication auth, Model model) {
		
		String mail = auth.getName();
		int idUsuario = authServ.obtenerUsuario(mail).getIdUsuario();
		List<Reservas> listReservas;
		
		if (estado != null) {
			listReservas = reservaServ.getReservasUsuarios(idUsuario, estado);
			if (listReservas.isEmpty()) {
				model.addAttribute("msg", "No se han encontrado reservas con el estado: " + estado);
			}
		} else {
			listReservas = reservaServ.getAllUserReservas(idUsuario);
			model.addAttribute("msg", "No existe el estado: " + estado);
		}
		
		model.addAttribute("listReservas", listReservas);
		return "asistente/dashboardAsistente";
	}
	
	@GetMapping("asistente/reservas/descargar/entrada/{id}")
	@ResponseBody
	@Operation(summary = "Descarga una entrada ",
	   		   description = "Descarga una entrada si el estado de la reserva es confirmada")
	public ResponseEntity<byte[]> descargarEntrada(@PathVariable("id") int idReserva,
												   Authentication auth, Model model) throws Exception {
		String mail = auth.getName();
		int idUsuario = authServ.obtenerUsuario(mail).getIdUsuario();
		Reservas reserva = reservaServ.getReserva(idUsuario, idReserva);
		byte[] pdf = null;
		String filename = "";
		
		if(reserva.getEstado() == EstadoReserva.confirmada) {
			pdf = GeneradorTicketPdf.generarEntrada(reserva);
			filename = "entrada_" + idReserva + ".pdf";
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", filename);
		
		return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
	}
	
	@PostMapping("/asistente/reservas/cancelar/{id}")
	@ResponseBody
	@Operation(summary = "Cancela una reserva",
	   		   description = "Cancela una reserca a partir de la id de reserva el usuario")
	public ResponseEntity<?> cancelarReserva(@PathVariable int id, Authentication auth, 
											 Cancelaciones cancelacion) {
		
		String mail = auth.getName();
		int idUsuario = authServ.obtenerUsuario(mail).getIdUsuario();
		Reservas reserva = reservaServ.getReserva(idUsuario, id);
		
		try {
			cancelacionServ.guardarCancelacion(reserva, cancelacion);
			reservaServ.setEstadoReserva(id, "cancelada");	
			
			return ResponseEntity.ok().build();
			
		} catch (RuntimeException e) {

			return ResponseEntity.badRequest().body(e.getMessage());			
		}
	}
	
	
	@PostMapping("/asistente/reservas/reembolsar/{id}")
	@ResponseBody
	@Operation(summary = "Crea un reembolso",
	   		   description = "Crea un reembolso a partir de la id y usuario")
	public ResponseEntity<?> solicitarReembolso(@PathVariable int id, Authentication auth,
												Reembolsos reembolso) {
		
		String mail = auth.getName();
		int idUsuario = authServ.obtenerUsuario(mail).getIdUsuario();
		Reservas reserva = reservaServ.getReserva(idUsuario, id);
		
		try {
			reembolsoServ.guardarReembolso(reserva, reembolso);
			
			return ResponseEntity.ok().build();
			
		} catch (RuntimeException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
}
