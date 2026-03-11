document.addEventListener("DOMContentLoaded", () => {
	const selectCantidad = document.getElementById("cantidad");
	const precioBase = parseFloat(document.getElementById("precioBase").textContent);
	const subtotal = document.getElementById("subtotal");
	const cantidadEntradas = document.getElementById("cantidadEntradas");
	const gastosGestion = document.getElementById("gastosGestion");
	const total = document.getElementById("total");
	
	function actualizarCantidadEntradas() {
		cantidadEntradas.textContent = selectCantidad.value;
	}
	
	function actualizarTotal() {
		const cantidad = parseInt(selectCantidad.value);
		const subtotalReserva = (precioBase * cantidad);
		subtotal.textContent = subtotalReserva.toFixed(2) + '€';
		
		const gastosGest = parseFloat(gastosGestion.textContent);
		const totalReserva = subtotalReserva + gastosGest;
		total.textContent = totalReserva.toFixed(2) + '€';
	}
	
	selectCantidad.addEventListener("change" , () => {
		actualizarCantidadEntradas();
		actualizarTotal();
	});
	
	actualizarTotal();
	
});