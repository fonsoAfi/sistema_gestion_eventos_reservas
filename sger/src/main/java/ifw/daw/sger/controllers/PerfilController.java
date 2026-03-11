package ifw.daw.sger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ifw.daw.sger.dto.CambiarClaveDTO;
import ifw.daw.sger.dto.PerfilAdministradorDTO;
import ifw.daw.sger.dto.PerfilAsistenteDTO;
import ifw.daw.sger.dto.PerfilDTO;
import ifw.daw.sger.dto.PerfilOrganizadorDTO;
import ifw.daw.sger.exceptions.ClaveMismatchException;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.services.AsistenteService;
import ifw.daw.sger.services.AuthService;
import ifw.daw.sger.services.OrganizadorService;
import ifw.daw.sger.services.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class PerfilController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
    OrganizadorService organizadorService;
	
	@Autowired
	AsistenteService asistenteService;
	
	@Autowired
	AuthService authServ;
	
	
	@GetMapping
	@ResponseBody
	public String perfil(Model model, Authentication auth, HttpSession session) throws Exception {
		
		Usuarios usuario = usuarioService.obtenerUsuario(auth);
		int idUsuario = usuario.getIdUsuario(); 
		PerfilDTO perfil = usuarioService.obtenerPerfil(auth);
		model.addAttribute("perfil", perfil);
		System.out.println("Autenticacion en el controlador: " + auth);
		Integer idEvento = (Integer) session.getAttribute("idEvento");
		
		if (!authServ.getUsuario(idUsuario).getEstadoCuenta().isActivo()) {
			throw new IllegalStateException("La cuenta no esta activa consulte al administrador");
		}
		
		if (perfil instanceof PerfilAsistenteDTO) {
			if (idEvento != null) {
				return "redirect:/profile/asistente/reservas/" + idEvento;
			}
			return "redirect:/profile/asistente/dashboardAsistente";
		} else if (perfil instanceof PerfilOrganizadorDTO) {
			if (!organizadorService.perfilCompleto(idUsuario)) {
				return "redirect:/profile/organizador/completar_organizador";
			}
			return "redirect:/profile/organizador/dashboardOrganizador";
		} else if (perfil instanceof PerfilAdministradorDTO ) {
			return "redirect:/profile/administrador/dashboardAdministrador";
		}
		throw new IllegalArgumentException("Valor inesperado: " + perfil);
	}
	
	@GetMapping("/cambiar_clave")
	@ResponseBody
	public String showCambiarClave(Model model) {
		model.addAttribute("claveDTO", new CambiarClaveDTO());
		return "cambiar_clave";
	}
	
	@PostMapping("/cambiar_clave")
	@ResponseBody
	public String procesarCambioClave(@ModelAttribute("claveDTO") CambiarClaveDTO claveDTO, BindingResult result, Authentication auth,
			 Model model) {
		
		if(usuarioService.compararClaves(claveDTO.getClaveNueva(), claveDTO.getClaveRepetida())) {
		    try {
		    	usuarioService.cambiarClave(claveDTO, auth);
		    	model.addAttribute("success", "La clave se ha cambiado correctamente");
		    } catch (NullPointerException | ClaveMismatchException e) {
		    	model.addAttribute("errorMsg", e.getMessage());
		    }
		} else {
			model.addAttribute("errorMsg", "Las clave no coinciden");
		}
					
		return "cambiar_clave";
	}
}
