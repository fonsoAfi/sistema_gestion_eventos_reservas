package ifw.daw.sger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ifw.daw.sger.models.Eventos;
import ifw.daw.sger.services.EventosService;

@Controller
public class HomeController {

	@Autowired
	EventosService eventosServ;
	
	@GetMapping("/")
	@ResponseBody
	public String showHome(Model model,
							@RequestParam(defaultValue = "0") int pagina) {
		
		model.addAttribute("mensaje", "Descubre como gestionar tus eventos y reservas");
		
		int totalPaginas = 9;
		Page<Eventos> listaEventos = eventosServ.getPaginasEventos(pagina, totalPaginas);
		if (!listaEventos.isEmpty()) {
			model.addAttribute("listaEventos", listaEventos.getContent());
			model.addAttribute("totalPaginas", listaEventos.getTotalPages());
			model.addAttribute("paginaActual", pagina);
		}
		return "index";
	}
}
