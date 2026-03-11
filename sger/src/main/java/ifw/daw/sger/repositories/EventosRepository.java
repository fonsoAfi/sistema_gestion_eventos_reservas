package ifw.daw.sger.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ifw.daw.sger.models.Categorias;
import ifw.daw.sger.models.Eventos;
import ifw.daw.sger.models.Ubicaciones;

public interface EventosRepository extends JpaRepository<Eventos, Integer> {
	
	@Query("""
		SELECT DISTINCT e
		FROM Eventos e
		JOIN FETCH e.categoria
		LEFT JOIN FETCH e.ubicacion
		WHERE e.organizador.idUsuario = :idUsuario
	""")
	List<Eventos> findEventosOrganizadorCompleto(@Param("idUsuario") int idUsuario);
	
	@Query("SELECT e FROM Eventos e WHERE e.tituloEvento = :tituloEvento")
	Optional<Eventos> findByTituloEvento(@Param("tituloEvento") String tituloEvento);
	
	int countByIdEvento(int idEvento);
	
	List<Eventos> findByCategoria(Categorias categoria);
	
	List<Eventos> findByUbicacion(Ubicaciones ubicacion);
	
	List<Eventos> findByFechaInicio(LocalDateTime fechaInicio);
	
	@Query(value="SELECT * FROM eventos WHERE " +
				 "YEAR(fecha_inicio) = YEAR(CURRENT_DATE) " +
				 "ORDER BY fecha_inicio ASC LIMIT 6", nativeQuery = true)
	List<Eventos> findEventosAnioActual();
}
