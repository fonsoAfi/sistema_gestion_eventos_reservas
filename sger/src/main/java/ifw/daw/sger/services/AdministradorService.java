package ifw.daw.sger.services;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifw.daw.sger.dto.ReservasPorEventoDTO;
import ifw.daw.sger.models.Categorias;
import ifw.daw.sger.models.EstadoCheckout;
import ifw.daw.sger.models.EstadoReserva;
import ifw.daw.sger.models.EstadoVenta;
import ifw.daw.sger.models.Eventos;
import ifw.daw.sger.models.Rol;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.repositories.*;
import jakarta.transaction.Transactional;

@Service
public class AdministradorService {

    private final CategoriasRepository categoriasRepo;

	@Autowired
	AuthRepository authRepo;
	
	@Autowired
	EventosRepository eventosRepo;
	
	@Autowired
	ReservaRepository reservasRepo;
	
	@Autowired
	VentasRepository ventasRepo;
	
	@Autowired
	CheckoutRepository checkoutRepo;


    AdministradorService(CategoriasRepository categoriasRepo) {
        this.categoriasRepo = categoriasRepo;
    }
	
	
	public long getTotalUsuarios() {
		return authRepo.count();
	}
	
	public long getTotalEventos() {
		return eventosRepo.count();
	}
	
	public long getTotalReservas() {
		return reservasRepo.count();
	}
	
	public long getTotalVentas() {
		return ventasRepo.count();
	}
	
	public long getTotalVentasPorEstado(EstadoVenta estado) {
		return ventasRepo.countByEstado(estado);
	}
	
	public BigDecimal getTotalIngresos(EstadoCheckout estado) {
		return checkoutRepo.sumarMontoPorEstado(estado);
	}

	public long getTotalReservasPorEstado(EstadoReserva estado) {
		return reservasRepo.countByEstado(estado);
	}
	
	public List<ReservasPorEventoDTO> getReservasPorEvento() {
		return reservasRepo.countReservasByEvento();
	}
	
	public List<Eventos> mostrarTodosEventos() {
		return eventosRepo.findAll();
	}
	
	@Transactional
	public void actualizarPrecioBase(Eventos eventoForm, Principal principal) throws Exception {
		if (authRepo.findUserByMail(principal.getName()).isEmpty()) {
			throw new Exception("Usuario no encontrado");
		}
		Usuarios usuario = authRepo.findUserByMail(principal.getName()).get(0);
		if (!usuario.getRol().isAdmin()) {
			throw new Exception("El usuario no esta autorizado no es un administrador");
		}
		Eventos eventoBD = eventosRepo.findById(eventoForm.getIdEvento())
				.orElseThrow(() -> new RuntimeException("Evento no encontrado"));
		eventoBD.setPrecioBase(eventoForm.getPrecioBase());
	}
	
	public List<Usuarios> getUsuariosPorRol(Rol rol) {
		return authRepo.findByRol(rol);
	}
	
	@Transactional
	public void actualizarEstadoCuenta(Usuarios usuarioForm, Principal principal) {
		Usuarios usuarioBD = authRepo.findById(usuarioForm.getIdUsuario())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		usuarioBD.setEstadoCuenta(usuarioForm.getEstadoCuenta());
	}
	
	public List<Categorias> getCategorias() {
		return categoriasRepo.findAll();
	}
	
	@Transactional
	public void actualizarCategoria(Categorias categoriaForm, Principal principal) throws Exception {
		if (authRepo.findUserByMail(principal.getName()).isEmpty()) {
			throw new Exception("Usuario no encontrado");
		}
		Usuarios usuario = authRepo.findUserByMail(principal.getName()).get(0);
		if (!usuario.getRol().isAdmin()) {
			throw new Exception("El usuario no esta autorizado no es un administrador");
		}
		Categorias categoriaBD = categoriasRepo.findById(categoriaForm.getIdCategoria())
				.orElseThrow(() -> new RuntimeException("Evento no encontrado"));
		if (categoriaForm.getNombreCategoria() != null || !categoriaForm.getNombreCategoria().isBlank()) {			
			categoriaBD.setNombreCategoria(categoriaForm.getNombreCategoria());
		}
		if (categoriaForm.getDescripcion() != null || !categoriaForm.getDescripcion().isBlank()) {			
			categoriaBD.setDescripcion(categoriaForm.getDescripcion());
		}
	}
}
