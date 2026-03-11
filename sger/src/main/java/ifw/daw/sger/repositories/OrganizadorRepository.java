package ifw.daw.sger.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ifw.daw.sger.models.Organizadores;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizadores, Integer> {
	
	boolean existsByIdUsuario(int id_usuario);
	
	// Comprobar si el organizador existe
	@Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Organizadores WHERE nombreOrganizador = :nombreOrganizador")
	boolean existsOrganizador(@Param("nombreOrganizador") String nombreOrganizador);
	
	Optional<Organizadores> findUsuarioByIdUsuario(int idUsuario);
	
}
