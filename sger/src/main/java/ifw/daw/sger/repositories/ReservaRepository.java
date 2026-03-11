package ifw.daw.sger.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ifw.daw.sger.dto.ReservasPorEventoDTO;
import ifw.daw.sger.models.EstadoReserva;
import ifw.daw.sger.models.Reservas;
import jakarta.transaction.Transactional;

public interface ReservaRepository extends JpaRepository<Reservas, Integer> {

	List<Reservas> findByEstado(String estado);
	
	@Query("""
			SELECT COALESCE(SUM(r.plazasReservadas), 0)
			FROM Reservas r
			WHERE r.estado = :estado
			""")
	int sumarPlazasReservadasPorEstado(@Param("estado") String estado);
	
	int countByIdReserva(int idReserva);
	
	@Query("SELECT COUNT(r) FROM Reservas r WHERE r.usuario.id = :id AND r.estado = :estado")
	int countByIdUsuario(@Param("id") int idUsuario, @Param("estado") EstadoReserva estado);
	
	int countByEstado(EstadoReserva estado);
	
	@Query("""
			SELECT new ifw.daw.sger.dto.ReservasPorEventoDTO(
				e.idEvento, 
				e.tituloEvento,
				COUNT(r),
				e.plazasDisponibles
			)
			FROM Reservas r
			JOIN r.evento e
			GROUP BY e.idEvento, e.tituloEvento
			ORDER BY e.idEvento
	""")
	List<ReservasPorEventoDTO> countReservasByEvento();
	
	@Query("SELECT r FROM Reservas r WHERE r.usuario.id = :id AND r.estado = :estado")
	List<Reservas> findByIdUsuario(@Param("id") int idUsuario, @Param("estado") EstadoReserva estado);
	
	@Query("SELECT r FROM Reservas r WHERE r.usuario.id = :id")
	List<Reservas> findByIdUsuario(@Param("id") int idUsuario);
	
	@Query("SELECT r FROM Reservas r WHERE r.usuario.id = :id AND r.estado != :estado")
	List<Reservas> findByIdUsuarioNot(@Param("id") int idUsuario, @Param("estado") EstadoReserva estado);
	
    @Modifying
    @Transactional
    @Query(
        value = "UPDATE reservas SET estado = :estado WHERE id_reserva = :id_reserva",
        nativeQuery = true
    )
    int actualizarEstado(@Param("id_reserva") int idReserva, @Param("estado") String estado);
    
    @Query("SELECT DISTINCT r FROM Reservas r WHERE r.usuario.idUsuario = :idUsuario AND r.idReserva = :idReserva")
    Reservas findReservaByIdUsuario(@Param("idUsuario") int idUsuario, @Param("idReserva") int idReserva);
}
