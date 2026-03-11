package ifw.daw.sger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifw.daw.sger.models.EstadoVenta;
import ifw.daw.sger.models.Ventas;

public interface VentasRepository extends JpaRepository<Ventas, Integer> {

	long countByEstado(EstadoVenta estado);
	
}
