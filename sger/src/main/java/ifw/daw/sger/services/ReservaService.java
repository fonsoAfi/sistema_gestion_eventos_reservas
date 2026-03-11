package ifw.daw.sger.services;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifw.daw.sger.models.EstadoReserva;
import ifw.daw.sger.models.Eventos;
import ifw.daw.sger.models.Reservas;
import ifw.daw.sger.models.Usuarios;
import ifw.daw.sger.repositories.ReservaRepository;
import jakarta.transaction.Transactional;

@Service
public class ReservaService {
	
	@Autowired
	ReservaRepository reservaRepo;
	
	@Autowired
	EventosService eventosServ;
	
	@Autowired
	AuthService authServ;
	
	public static final BigDecimal GASTOS_GESTION = new BigDecimal("2");
	
	@Transactional
	public void registrarReserva(Reservas reserva, int idEvento, Principal principal) {
		System.out.println("id del evento: " + idEvento);
		Eventos evento = eventosServ.getEvento(idEvento);
		Usuarios usuario = authServ.obtenerUsuario(principal.getName());
		
		int numEntradas = reserva.getPlazasReservadas(); 
		
		if (numEntradas <= 0) {
			throw new RuntimeException("El numero de entradas debe ser mayor superior igual a 1 o superior");
		} 
		
		int plazasDisponibles = evento.getPlazasDisponibles();
		
		System.out.println("Plazas disponibles: " + plazasDisponibles);
		
		if (numEntradas > plazasDisponibles) {
			throw new RuntimeException("El numero de entradas supera el numero de plazas disponibles");
		}
		
		String titular = usuario.getNombreReal() + " " + usuario.getApellido1() + " " + usuario.getApellido2();
		reserva.setTitular(titular);
		
		reserva.setPrecioBase(evento.getPrecioBase());
		reserva.setUsuario(usuario);
		reserva.setEvento(evento);
		
		reserva.setPlazasReservadas(numEntradas);
		BigDecimal subtotal = reserva.getPrecioBase().multiply(BigDecimal.valueOf(numEntradas));
		BigDecimal total = subtotal.add(GASTOS_GESTION);
		
		reserva.setGastosGestion(GASTOS_GESTION);
		reserva.setSubtotal(subtotal);
		reserva.setTotal(total);

		reserva.setEstado(EstadoReserva.pendiente);
		
		if (reserva.getEstado().equals("confirmada")) {
			throw new RuntimeException("La reserva que estas intentando registrar ya ha sido pagada");
		}
		reservaRepo.save(reserva);
		
		
		
	}
	
	public int getNumeroReservas(int idUsuario, EstadoReserva estado) {
		return reservaRepo.countByIdUsuario(idUsuario, estado);
	}
	
	public List<Reservas> getAllReservas() {
		return reservaRepo.findAll();
	}
	
	public List<Reservas> getAllUserReservas(int idUsuario) {
		return reservaRepo.findByIdUsuario(idUsuario);
	}
	
	public List<Reservas> getReservasUsuarios(int idUsuario, EstadoReserva estado) {
		return reservaRepo.findByIdUsuario(idUsuario, estado);
	}
	
	public List<Reservas> getReservasNoConfirmadas(int idUsuario, EstadoReserva estado) {
		return reservaRepo.findByIdUsuarioNot(idUsuario, estado);
	}
	
	public Reservas getReserva(int idUsuario, int idReserva) {
		return reservaRepo.findReservaByIdUsuario(idUsuario, idReserva);
	}
	
	@Transactional
	public int setEstadoReserva(int idReserva, String estado) {
		if (idReserva == 0) {
			throw new RuntimeException("No existe la reserva");
		} else if (estado.isEmpty()) {
			throw new RuntimeException("No se ha aportado un estado para la reserva");
		}
		return reservaRepo.actualizarEstado(idReserva, estado);
	}
}
