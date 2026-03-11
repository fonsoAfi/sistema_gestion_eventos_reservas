package ifw.daw.sger.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifw.daw.sger.models.Categorias;
import ifw.daw.sger.models.Eventos;
import ifw.daw.sger.models.Ubicaciones;
import ifw.daw.sger.services.AuthService;
import ifw.daw.sger.services.CategoriasService;
import ifw.daw.sger.services.EventosService;
import ifw.daw.sger.services.UbicacionesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

// Cambiar vista a la tabla original
@Controller
@RequestMapping("/profile")
public class EventosController {

	@Autowired 
	AuthService authServ;
	
	@Autowired
	EventosService eventosServ;
	
	@Autowired
	CategoriasService categoriasServ;
	
	@Autowired
	UbicacionesService ubicacionesServ;
	
	
	@GetMapping("/organizador/dashboardOrganizador")
	@ResponseBody
	@Operation(summary = "Muestra los eventos del organizador",
	   		   description = "Muestra los eventos del organizador")
	public String mostrarEventosOrganizador(Model model, Principal principal, Authentication auth) {
		
		String mail = auth.getName();
		int idUsuario = authServ.mostrarIdUsuario(mail);
		String nombreReal = authServ.obtenerUsuario(mail).getNombreReal();

		List<Eventos> listaEventos = eventosServ.getEventos(idUsuario);
		
		model.addAttribute("nombreReal", nombreReal);
		model.addAttribute("listaEventos", listaEventos);
		
		model.addAttribute("nuevoEvento", new Eventos());
		
		List<Categorias> listaCategorias = categoriasServ.mostrarCategorias();
		model.addAttribute("listaCategorias", listaCategorias);
		
		List<Ubicaciones> listaUbicaciones = ubicacionesServ.mostrarUbicaciones();
		model.addAttribute("listaUbicaciones", listaUbicaciones);
		model.addAttribute("nuevoEvento", new Eventos());
		
		return "/organizador/dashboardOrganizador";
	}
	
	
	@PostMapping("/organizador/eventos/nuevo")
	@ResponseBody
	public String procesarNuevoEvento(@ModelAttribute("nuevoEvento") Eventos nuevoEvento, BindingResult result, 
			HttpServletRequest request, RedirectAttributes redirectAttributes, Model model, Principal principal) {
		
		try {
			int idUsuario = authServ.mostrarIdUsuario(principal.getName());
			eventosServ.crearNuevoEvento(nuevoEvento, idUsuario);
		} catch (RuntimeException e) {
	        redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
		}
		
		return "redirect:/profile/organizador/dashboardOrganizador";
	}
	
	@GetMapping("/organizador/eventos/actualizar/{id}")
	@ResponseBody
	public String mostrarFormActualizarEvento(@PathVariable("id") int id, Model model) {
		
		Eventos evento = eventosServ.getEvento(id);
		model.addAttribute("evento", evento);
		model.addAttribute("listaCategorias", categoriasServ.mostrarCategorias());
		model.addAttribute("listaUbicaciones", ubicacionesServ.mostrarUbicaciones());
		return "/organizador/eventos/form_actualizar";
	}
	
	@PostMapping("/organizador/eventos/actualizar")
	@ResponseBody
	public String actualizarEvento(@ModelAttribute("evento") Eventos evento, Principal principal) {
		
		eventosServ.actualizarEvento(evento, principal);
		
		return "redirect:/profile/organizador/dashboardOrganizador";
	}
	
	@PostMapping("/organizador/eventos/borrar/{id}")
	@ResponseBody
	public String borrarEvento(@PathVariable("id") int id, Principal principal) {
		
		eventosServ.borrarEvento(id, principal);
		
		return "redirect:/profile/organizador/dashboardOrganizador";
	}
	
}
