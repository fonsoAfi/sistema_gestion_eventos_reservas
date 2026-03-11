window.onload = function() {
	mostrarSeccion();
	
	mostrarVentanaModal();
	
	mostrarTextArea();
	
	cerrarVentanaModal();
	
	document.getElementById("btn-cerrar-modal").addEventListener("click", cerrarVentanaModal);
	
	enviarFormModal();
	
	solicitarReembolso();
};

function mostrarSeccion() {
	const content = document.getElementById("content");
	const secciones = content.getElementsByClassName("seccion");
	cargarSeccionActiva(secciones);
	
	const aside_links = content.getElementsByClassName("aside-link");
	
	Array.from(aside_links).forEach(aside_link => {
		aside_link.addEventListener("click", (e) => {
			e.preventDefault();
			const idAncla = aside_link.getAttribute("href").substring(1);
			const seccion = document.getElementById(idAncla);
			
			if (seccion) {	
				borrarSeccionActiva(secciones);			
				seccion.classList.add("active");
				sessionStorage.setItem("seccionActiva", idAncla);
			}
			
		});
	});
}

function borrarSeccionActiva(secciones) {
	Array.from(secciones).forEach(seccion => seccion.classList.remove("active"));
}

function cargarSeccionActiva(secciones) {
	const key = sessionStorage.getItem("seccionActiva");
	if (key) {
		const ultimaSeccion = document.getElementById(key);
		if (ultimaSeccion) {
			borrarSeccionActiva(secciones);
			ultimaSeccion.classList.add("active");
		}
	} else {
		secciones[0].classList.add("active");
	}
}

function mostrarVentanaModal() {
	linksCancelar = document.getElementsByClassName("cancelar");
	Array.from(linksCancelar).forEach(linkCancelar => {
		linkCancelar.addEventListener("click", (e) => {
			e.preventDefault();
			const idReserva = linkCancelar.dataset.id;
			document.getElementById("idReserva").value = idReserva;
			document.getElementById("modal-cancelar").classList.remove("hidden");	
		});
	});
	
}

function mostrarTextArea() {
	document.getElementById("motivo").addEventListener("change", function() {
		const textArea = document.getElementById("otroMotivo");
		if (this.value === "otro") {
			textArea.classList.remove("hidden");
			textArea.value = "";
		} else {
			textArea.classList.add("hidden");
			textArea.value = this.value;
		}
	});
}

function cerrarVentanaModal() {
		
	const form = document.getElementById("form-cancelar");
	const ventanaModal = document.getElementById("modal-cancelar");
	const textArea = document.getElementById("otroMotivo");
	const mensajeError = document.getElementById("mensajeError");

	form.reset();
	ventanaModal.classList.add("hidden");		
		
	if (textArea) {			
		textArea.classList.add("hidden");
	}
		
	if (mensajeError) {
		mensajeError.classList.add("hidden");
	}	
}

function enviarFormModal() {
	
	const formCancelar = document.getElementById("form-cancelar");
	
	formCancelar.addEventListener("submit", (e) => {
		e.preventDefault();
		

		const select = document.getElementById("motivo");
		const textarea = document.getElementById("otroMotivo");
		const formData = new FormData(formCancelar);

		if(select.value === "otro"){
			if(textarea.value.trim() === "") {
				mostrarErrorModal("Debes escribir el motivo de cancelación");
				return; 
			}
			console.log(formData);
			formData.set("motivo", textarea.value);
		}

		const idReserva = formData.get("idReserva");

		fetch(`/profile/asistente/reservas/cancelar/${idReserva}`, {
		    method: 'POST',
			body: formData,
		    headers: {
		        'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content,
		        'X-Requested-With': 'XMLHttpRequest'
		    }
		})
		.then(async response => {
		    if(response.ok) {
		        const fila = document.getElementById(`reserva-${idReserva}`);
				if (fila) {					
			        fila.querySelector('.estado').textContent = 'cancelada';
				}
		        fila.querySelector("#i-cancelar-container-" + idReserva).style.display = 'none';
				cerrarVentanaModal();
		    } else {
		        const error = await response.text();
				mostrarErrorModal(error);
		    }
		})
		.catch(err => alert('Error al cancelar: ' + err));
	});
	

}

function mostrarErrorModal(error) {
	const mensajeError = document.getElementById("mensajeError");
	mensajeError.textContent = error;
	mensajeError.classList.remove("hidden");
}

function solicitarReembolso() {

	const botones = document.querySelectorAll(".reembolso");

	botones.forEach(btn => {
		btn.addEventListener("click", async function(e){
			e.preventDefault();
			
			const idReserva = this.dataset.id;

			const confirmar = confirm("¿Seguro que quieres solicitar el reembolso?");

			if(!confirmar){
				return;
			}

			const formData = new FormData();
			formData.append("idReserva", idReserva);

			try{
				const response = await fetch(`/profile/asistente/reservas/reembolsar/${idReserva}`,{
					method:"POST",
					body:formData,
					headers:{
						'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content,
						'X-Requested-With': 'XMLHttpRequest'
					}
				});

				if(response.ok){

					alert("Solicitud de reembolso enviada");

					const fila = document.getElementById(`reserva-${idReserva}`);

					if(fila){
						fila.querySelector(".estado").textContent = "reembolsada";
					}

				}else{
					const error = await response.text();
					alert(error);
				}

			}catch(err){
				alert("Error al solicitar el reembolso");
			}
		});
	});
}