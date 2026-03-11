package ifw.daw.sger.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import ifw.daw.sger.models.Categorias;
import ifw.daw.sger.models.EstadoCheckout;
import ifw.daw.sger.models.EstadoCuenta;
import ifw.daw.sger.models.EstadoReserva;
import ifw.daw.sger.models.EstadoVenta;
import ifw.daw.sger.models.Eventos;
import ifw.daw.sger.models.Rol;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.services.AdministradorService;
import ifw.daw.sger.services.AuthService;
import ifw.daw.sger.services.CategoriasService;
import ifw.daw.sger.services.EventosService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class AdministradorController {

	@Autowired
	AdministradorService adminServ;
	
	@Autowired
	EventosService eventosServ;
	
	@Autowired
	AuthService authServ;
	
	@Autowired
	CategoriasService categoriasServ;
	
	
	@GetMapping("/administrador/dashboardAdministrador")
	@ResponseBody
	@Operation(summary = "mostrar el dashboard del administrador",
			   description = "Muestra estadisticas generales y reservas por evento")
	public String mostrarDashboardAdministrador(Model model, Authentication auth) {
		
		String mail = auth.getName();
		String nombreReal = authServ.obtenerUsuario(mail).getNombreReal();
		
		model.addAttribute("nombreReal", nombreReal);
		model.addAttribute("totalUsuarios", adminServ.getTotalEventos());
		model.addAttribute("totalEventos", adminServ.getTotalEventos());
		model.addAttribute("totalReservas", adminServ.getTotalReservas());
		model.addAttribute("totalVentas", adminServ.getTotalVentas());
		model.addAttribute("totalVentasCompletadas", adminServ.getTotalVentasPorEstado(EstadoVenta.completada));
		model.addAttribute("totalIngresos", adminServ.getTotalIngresos(EstadoCheckout.completado));
		model.addAttribute("totalReservasCanceladas", adminServ.getTotalReservasPorEstado(EstadoReserva.cancelada));
		model.addAttribute("totalReservasPendientes", adminServ.getTotalReservasPorEstado(EstadoReserva.pendiente));
		model.addAttribute("reservasPorEvento", adminServ.getReservasPorEvento());
		return "/administrador/dashboardAdministrador";
	}
	
	@GetMapping("/administrador/eventos/tablaEventos")
	@ResponseBody
	@Operation(summary = "mostrar todos los eventos",
	   		   description = "Muestra todos los eventos del administrador")
	public String mostrarEventos(Model model) {
		model.addAttribute("listaEventos", adminServ.mostrarTodosEventos());
		return "administrador/eventos/tablaEventos";
	}
	
	@GetMapping("/administrador/eventos/form_eventos/{id}")
	@ResponseBody
	@Operation(summary = "muestra el formulario para un evento",
	   		   description = "Muestra el formulario parar un evento pasado por id")
	public String mostrarFormEventos(@PathVariable("id") int id, Model model) {
		Eventos evento = eventosServ.getEvento(id);
		model.addAttribute("evento", evento);
		return "administrador/eventos/form_eventos";
	}
	
	@PostMapping("/administrador/eventos/form_eventos")
	@ResponseBody
	@Operation(summary = "actualiza un evento",
	   		   description = "actualiza un evento para el usuario registrado")
	public String actualizarEvento(@ModelAttribute("evento") Eventos evento, Principal principal) throws Exception {

		adminServ.actualizarPrecioBase(evento, principal);
		return "redirect:/profile/administrador/eventos/tablaEventos";
	}
	
	@GetMapping("/administrador/usuarios/tablaUsuarios")
	@ResponseBody
	@Operation(summary = "muestra una lista de usuarios",
	   		   description = "muestra una lista de usuarios con el rol asistentes")
	public String mostrarUsuarios(Model model) {
		model.addAttribute("listaUsuarios", adminServ.getUsuariosPorRol(Rol.ASISTENTES));
		return "administrador/usuarios/tablaUsuarios";
	}
	
	@GetMapping("/administrador/usuarios/form_usuarios/{id}")
	@ResponseBody
	@Operation(summary = "muestrao un usuario y los estados de la cuenta de usuario",
	   		   description = "muestra un usuario a partir de su id y los estados de las cuentas de usuario")
	public String mostrarUsuarios(@PathVariable("id") int id, Model model) throws Exception {
		Usuarios usuario = authServ.getUsuario(id);
		usuario.setEstadoCuenta(null);
		model.addAttribute("usuario", usuario);
		model.addAttribute("estadosCuenta", EstadoCuenta.values());
		return "administrador/usuarios/form_usuarios";
	}
	
	@PostMapping("/administrador/usuarios/form_usuarios")
	@ResponseBody
	@Operation(summary = "actualiza el estado de la cuenta de un usuario",
	   		   description = "actualiza el estado de la cuenta de un usuario")
	public String actualizarUsuario(@ModelAttribute("usuario") Usuarios usuario, 
									@RequestParam("idUsuario") int idUsuario,
									Principal principal) {
		
		adminServ.actualizarEstadoCuenta(usuario, principal);
		return "redirect:/profile/administrador/usuarios/tablaUsuarios";
	}
	
	@GetMapping("/administrador/categorias/tablaCategorias")
	@ResponseBody
	@Operation(summary = "Muestra las categorias",
	   		   description = "Muestra las categorias existentes")
	public String mostrarCategorias(Model model) {
		model.addAttribute("listaCategorias", adminServ.getCategorias());
		return "administrador/categorias/tablaCategorias";
	}
	
	@GetMapping("/administrador/categorias/actualizar/{id}")
	@ResponseBody
	@Operation(summary = "Muestra una categoria",
	   		   description = "Muestra una categoria a partir de su id")
	public String mostrarActualizarCategoria(@PathVariable("id") int id, Model model) throws Exception {
		Categorias categoria = categoriasServ.getCategoria(id);
		model.addAttribute("categoria", categoria);
		return "administrador/categorias/actualizar";
	}
	
	@ResponseBody
	@Operation(summary = "Actualiza una categoria",
	   		   description = "Actualiza una categoria")
	@PostMapping("/administrador/categorias/actualizar")
	public String actualizarCategoria(@ModelAttribute("categoria") Categorias categoria, 
									  @RequestParam("idCategoria") int idCategoria,
									  Principal principal) throws Exception {
		
		adminServ.actualizarCategoria(categoria, principal);
		return "redirect:/profile/administrador/categorias/tablaCategorias";
	}
	
	@ResponseBody
	@Operation(summary = "Borra una categoria",
	   		   description = "Borra una categoria a partir de su id")
	@PostMapping("/administrador/categorias/borrar/{id}")
	public String borrarEvento(@PathVariable("id") int id, Principal principal) throws Exception {
		
		categoriasServ.borrarCategoria(id, principal);
		
		return "redirect:/profile/administrador/categorias/tablaCategorias";
	}
	
	@ResponseBody
	@Operation(summary = "Muestra una categoria",
	   		   description = "Muestra una categoria nueva sin valores")
	@GetMapping("/administrador/categorias/nueva")
	public String mostrarNuevaCategoria(Model model) {
		model.addAttribute("categoria", new Categorias());
		return "administrador/categorias/nueva";
	}
	
	@ResponseBody
	@Operation(summary = "Crea una categoria nueva",
	   		   description = "Crea una categoria nueva si falla agrega un mensaje de error")
	@PostMapping("/administrador/categorias/nueva")
	public String procesarNuevaCategoria(@ModelAttribute("categoria") Categorias categoria, BindingResult result, 
			HttpServletRequest request, RedirectAttributes redirectAttributes, Model model, Principal principal) {
		
		try {
			categoriasServ.crearNuevaCategoria(categoria, principal);
		} catch (Exception e) {
	        redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
		}
		
		return "redirect:/profile/administrador/categorias/tablaCategorias";
	}
	
	
}
