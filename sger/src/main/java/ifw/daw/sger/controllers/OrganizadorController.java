package ifw.daw.sger.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifw.daw.sger.exceptions.TipoOrganizadorMismatchException;
import ifw.daw.sger.exceptions.UserAlreadyExistsException;
import ifw.daw.sger.models.Organizadores;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.services.AuthService;
import ifw.daw.sger.services.OrganizadorService;
import ifw.daw.sger.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/profile")
public class OrganizadorController {

	@Autowired
    OrganizadorService organizadorService;

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	AuthService authService;
	
	@GetMapping("/organizador/completar_organizador")
	@ResponseBody
	public String mostrarCompletarOrganizador(Model model) {
		model.addAttribute("organizador", new Organizadores());
		
		return "/organizador/completar_organizador";
	}
	
	@PostMapping("/organizador/completar_organizador")
	@ResponseBody
	public String procesarOrganizador(@Valid @ModelAttribute("organizador") Organizadores organizador, BindingResult result, 
			HttpServletRequest request, RedirectAttributes redirectAttributes, Principal principal, Model model) {
		
	    if (result.hasErrors()) {

	    	result.getFieldErrors().forEach(error -> {
	    		String field = error.getField();
	    		String attributeName = field + "Error";
	    		
	    		model.addAttribute(attributeName, error.getDefaultMessage());
	    	});
	    	
	    	return "organizador/completar_organizador";
	        
	    }
		
		Usuarios usuario = authService.mostrarCreds(principal.getName());
		try {
			organizadorService.registrarOrganizador(
					usuario.getIdUsuario(),
					organizador.getNombreOrganizador(), 
					organizador.getTipoOrganizador(), 
					organizador.getLimiteEventos()
			);
		} catch(UserAlreadyExistsException | TipoOrganizadorMismatchException e) {
	        redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
	        redirectAttributes.addFlashAttribute("organizador", organizador);
	        return "redirect:/profile/organizador/completar_organizador";
		}
		System.out.println(organizador.getNombreOrganizador());
		return "/organizador/dashboardOrganizador";
	}
}
