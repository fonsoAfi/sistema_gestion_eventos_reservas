package ifw.daw.sger.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ifw.daw.sger.models.Rol;
import ifw.daw.sger.models.Usuarios;
import jakarta.transaction.Transactional;

@Repository
public interface AuthRepository extends JpaRepository<Usuarios, Integer> {
	
	List<Usuarios> findUserByMail(String mail);
	
	List<Usuarios> findByRol(Rol rol);
	
	@Query("SELECT u.idUsuario FROM Usuarios u WHERE u.mail = :mail")
	public int findIdByMail(@Param("mail") String mail);
	
	@Query("SELECT new ifw.daw.sger.models.Usuarios(u.idUsuario, u.mail, u.clave, u.rol) FROM Usuarios u WHERE u.mail = :mail")
	public Usuarios findCredsByMail(@Param("mail") String mail);
	
	@Modifying
	@Transactional
	@Query("UPDATE Usuarios SET ultimoAcceso = :ultimoAcceso WHERE mail = :mail")
	public void updateUltimoAcceso(@Param("ultimoAcceso") LocalDateTime ultimoAcceso, @Param("mail") String mail);
	
	@Modifying
	@Transactional
	@Query("UPDATE Usuarios SET fechaRegistro = :fechaRegistro WHERE mail = :mail")
	public void updateFechaRegistro(@Param("fechaRegistro") LocalDateTime fechaRegistro, @Param("mail") String mail);
	
	@Modifying
	@Transactional
	@Query("UPDATE Usuarios SET clave = :clave WHERE mail = :mail")
	public void updateClave(@Param("clave") String clave, @Param("mail") String mail);

	@Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Usuarios WHERE mail = :mail")
	public boolean existsEmail(@Param("mail") String mail);
	
	@Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Usuarios WHERE nombrePerfil = :nombrePerfil")
	public boolean existsNombrePerfil(@Param("nombrePerfil") String nombrePerfil);
	
	@Query("SELECT idUsuario FROM Usuarios WHERE mail = :mail")
	public String selectIdUsuario(@Param("mail") String mail);

}
