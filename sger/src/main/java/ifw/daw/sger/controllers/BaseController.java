package ifw.daw.sger.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import ifw.daw.sger.models.Usuarios;
import io.swagger.v3.oas.annotations.Operation;

@Controller
public class BaseController {

	@ModelAttribute("usuarioActual")
	@ResponseBody
	@Operation(summary = "Muetra el usuario resgistrado actualmente")
	public Usuarios getUsuarioActual(Authentication auth) {
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
        	System.out.println((Usuarios) auth.getPrincipal());
            return (Usuarios) auth.getPrincipal();
        }
        return null;
	}
}
