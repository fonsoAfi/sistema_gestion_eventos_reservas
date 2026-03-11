package ifw.daw.sger.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ifw.daw.sger.models.Checkout;
import ifw.daw.sger.models.EstadoCheckout;
import ifw.daw.sger.models.Reservas;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {

	boolean existsByReservaAndEstado(Reservas reserva, EstadoCheckout estado);
	
	@Query("""
			SELECT COALESCE(SUM(c.montoTotal), 0)
			FROM Checkout c
			WHERE c.estado = :estado
			""")
	BigDecimal sumarMontoPorEstado(@Param("estado") EstadoCheckout estado);
	
	List<Checkout> findByEstado(EstadoCheckout estado);
	
	long countByEstado(EstadoCheckout estado);
	
	Optional<Checkout> findByReservaAndEstado(Reservas reserva, EstadoCheckout estado);
}
