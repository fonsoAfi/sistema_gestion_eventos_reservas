package ifw.daw.sger.controllers;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifw.daw.sger.exceptions.RoleMismatchException;
import ifw.daw.sger.exceptions.UserAlreadyExistsException;
import ifw.daw.sger.models.Pais;
import ifw.daw.sger.models.Rol;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.repositories.AuthRepository;
import ifw.daw.sger.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@SessionAttributes({"paises", "roles"})
@RequestMapping("/auth")
public class AuthController {


	@Autowired
	AuthRepository authRepo;
	
	@Autowired
	AuthService authService;
    
	
	@GetMapping("/login/{id}")
	@ResponseBody
	public String loginDesdeEventos(@PathVariable Integer id, HttpSession session) {
		
		System.out.println(id);
		
		if (id != null) {
			session.setAttribute("idEvento", id);
		}

		return "login";
	}
    
	@GetMapping("/login")
	@ResponseBody
	@Operation(summary = "Muestra el login")
	public String showLogin(Authentication auth, 
			HttpServletRequest request, HttpSession session, Model model) {
		
		System.out.println("Autenticacion de usuario sin registrar: " + auth);
		Object errorMessage = session.getAttribute("errorMessage");
		String mail = (String) session.getAttribute("last_mail");

		if (request.getAuthType() == null && errorMessage != null) {
			model.addAttribute("last_mail", mail);
			session.removeAttribute("last_mail");
			model.addAttribute("errorMessage", errorMessage);
			session.removeAttribute("errorMessage");
		}
		
		return "login";
	}
	
	@GetMapping("/registrarse")
	@ResponseBody
	@Operation(summary = "Muestra el registro de usuario",
	   		   description = "Muestra el registro de usuario agrega usuario nuevo, paises, roles")
	public String showResgistro(HttpServletRequest request, Model model) {
		if (!model.containsAttribute("usuarioReq")) {
			Usuarios usuarioReq = new Usuarios();
			usuarioReq.setPais("");
			model.addAttribute("usuarioReq", usuarioReq);
		}
		
		System.out.println("GET usuarioReq nuevo");
		model.addAttribute("paises", Pais.values());
		model.addAttribute("roles", Rol.values());
			
		return "registrarse";
	}
	
	@PostMapping("/registrarse")
	@ResponseBody
	@Operation(summary = "Guarda el nuevo usuario registrado",
	   		   description = "Guarda el nueva usuario registrado o muestra un error")
	public String procesarRegistro(@Valid @ModelAttribute("usuarioReq") Usuarios usuarioReq, BindingResult result, 
			HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) throws RoleMismatchException, UserAlreadyExistsException {

	    if (result.hasErrors()) {

	    	result.getFieldErrors().forEach(error -> {
	    		String field = error.getField();
	    		String attributeName = field + "Error";
	    		
	    		model.addAttribute(attributeName, error.getDefaultMessage());
	    	});
	    	
	    	return "registrarse";
	        
	    }
	    
	    try {
	        authService.registrarUsuario(usuarioReq);
	    } catch (NullPointerException | RoleMismatchException | UserAlreadyExistsException e) {
	        redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
	        redirectAttributes.addFlashAttribute("usuarioReq", usuarioReq);
	        return "redirect:/auth/registrarse";
	    }
	    String successMsg = "El usuario " + usuarioReq.getMail() + " registrado correctamente ";
	    redirectAttributes.addFlashAttribute("successMsg", successMsg);
	    return "redirect:/auth/login"; 
	}
	
	
	@GetMapping("/accessError")
	@ResponseBody
	@Operation(summary = "Muestra un error de acceso")
	public String showAccessError() {
		return "accessError";
	}
}
