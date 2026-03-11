window.onload = iniciar;
const className = "active";	

function iniciar() {
	const forms = document.getElementsByClassName("auth-form");
	
	Array.from(forms).forEach(form => {
		if (form.id === "login-form") {
			
			form.addEventListener("submit", (e) => {
				if (validarFormLogin(e, form)) {
					console.log("Login OK");
					form.submit();
				}
			});
			
		} else if (form.id === "signin-form") {
			const pasos = form.getElementsByClassName("paso");
			
			if (pasos.length > 0) {				
				pasos[0].classList.add(className);
			}
			
			const botones = form.getElementsByClassName("btn-paso");
			
			Array.from(botones).forEach(btn => {
				if (btn) {
					btn.addEventListener("click", (e) => {
						const pasoActivo = form.getElementsByClassName("paso active")[0];
						if (btn.classList.contains("siguiente")) {							
							if (validarFormSignin(e, form)) {
								avanzarPaso(pasoActivo);
							}
						} else if (btn.classList.contains("anterior")) {
							e.preventDefault();
							retrocederPaso(pasoActivo);
						}
					});				
				}
			});
			form.addEventListener("submit", (e) => {
				if (validarFormSignin(e, form)) {
					form.submit();
				}
			})
		}
	});	
}


function validarFormLogin(e, form) {
	
	let formValido = true;
	
	e.preventDefault();
	const campos = form.getElementsByClassName("campo");
		
	Array.from(campos).forEach(campo => {

		if (!comprobarCampoRellenado(campo)) {
			formValido = false;
			return;
		}
			
		if (campo.id === "mail") {
			formValido = (validarMail(campo));
		}
			
		if (campo.id === "clave") {
			formValido = (comprobarClave(campo));
		}
	});
	
	return formValido;
}

function avanzarPaso(pasoActivo) {
	const siguientePaso = pasoActivo.nextElementSibling;
	if (siguientePaso) {
		pasoActivo.classList.remove("active");
		siguientePaso.classList.add("active");			
	}
}

function retrocederPaso(pasoActivo) {
	const anteriorPaso = pasoActivo.previousElementSibling;
	if (anteriorPaso) {
		pasoActivo.classList.remove("active");
		anteriorPaso.classList.add("active");	
	}
}

function validarFormSignin(e, form) {
	
    e.preventDefault();
    const pasoActivo = form.getElementsByClassName("paso active")[0];
	
	const campos = pasoActivo.getElementsByClassName("campo");
    
    let pasoValido = true;
    Array.from(campos).forEach(campo => {
		console.log(campo);

		if (campo.name === "rol") {
			if (!validarRol(form, campo.name)) pasoValido = false;
			return;
		}
		
        if (!comprobarCampoRellenado(campo)) {
            pasoValido = false;
            return; 
        }

        switch (campo.id) {
            case "nombreReal":
            case "apellido1":
            case "apellido2":
                if (!validarNombrePropio(campo)) pasoValido = false;
                break;

            case "nombrePerfil":
                if (!validarNombrePerfil(campo)) pasoValido = false;
                break;

            case "mail":
                if (!validarMail(campo)) pasoValido = false;
                break;

            case "clave":
                if (!comprobarClave(campo)) pasoValido = false;
                break;
				
			case "telefono":
				if (!validarTelefono(campo)) pasoValido = false;
				break;
				
			case "fechaNacimiento":
				if (!validarFechaNacimiento(campo)) pasoValido = false;
				break;
				
			case "pais":
				if (!validarPais(campo)) pasoValido = false;
				break;
        }
    });
	
	return pasoValido;
}

function comprobarCampoRellenado(elemento) {
	console.log(elemento);
	const valor = elemento.value.trim();
	
	if (!valor) {
		mensaje = "*Campo obligatorio";
		error(elemento, mensaje);
		return false;
	}
	
	borrarError(elemento);
	return true;
}

function validarMail(elemento) {
	const mail = elemento.value.trim();
	const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
			
	if (!regex.test(mail)) {
		mensaje = "*Formato mail es correcto incorrecto";
		error(elemento, mensaje);
		return false;
	}
	
	borrarError(elemento);
	return true;	
}

function comprobarClave(elemento) {
	
	const clave = elemento.value.trim();
	const lonMinima = 8;

	if (clave.length < lonMinima) {
		mensaje = "*La longitud minima de la clave es de " + lonMinima + " carateres";
		error(elemento, mensaje);
		return false;
	} 
		
	if (/\s/.test(clave)) {
		mensaje = "*La clave debe contener espacios";
		error(elemento, mensaje);
		return false;
	}
		
	if (!/[a-z]/.test(clave)) {
		mensaje = "*La clave debe contener al menos una minuscula";
		error(elemento, mensaje);
		return false;
	}
		
	if (!/[A-Z]/.test(clave)) {
		mensaje = "*La clave debe contener al menos una mayuscula";
		error(elemento, mensaje);
		return false;
	}
		
	if (!/\d/.test(clave)) {
		mensaje = "*La clave debe contener al menos un digito";
		error(elemento, mensaje);
		return false;
	}
	
	borrarError(elemento);
	return true;
}

function validarNombrePropio(elemento) {
	
	const nombrePropio = elemento.value.trim();

	if (nombrePropio.length < 2) {
		mensaje = "*Debe tener al menos 2 letras";
		error(elemento, mensaje);
		return false;
	}
		
	if (/\s/.test(nombrePropio)) {
		mensaje = "*No puede contener espacios";
		error(elemento, mensaje);
		return false;
	}
		
	if (!/^\p{L}+$/u.test(nombrePropio)) {
		mensaje = "*Solo puede tener letras";
		error(elemento, mensaje);
		return false;
	}
	
	borrarError(elemento);
	return true;
}

function validarNombrePerfil(elemento) {
	
	const nombrePerfil = elemento.value.trim();

	if (nombrePerfil.length < 3 || nombrePerfil.length > 20) {
		mensaje = "*No contiene entre 3 y 20 caracteres";
		error(elemento, mensaje);
		return false;
	}	
		
	if (/\s/.test(nombrePerfil)) {
		mensaje = "*No puede contener espacios";
		error(elemento, mensaje);
		return false;
	}
			
	if (nombrePerfil.startsWith("_")) {
		mensaje = "*No puede empezar con un '_'";
		error(elemento, mensaje);
		return false;
	}
		
	if (nombrePerfil.endsWith("_")) {
		mensaje = "*No puede terminar con un '_'";
		error(elemento, mensaje);
		return false;
	}
		
	const count = (nombrePerfil.match(/_/g) || []).length;
	if (count > 1) {
		mensaje = "*Solo se permite un '_' en el nombre";
		error(elemento, mensaje);
		return false;
	}
		
	if (!/^[a-zA-Z0-9_]+$/.test(nombrePerfil)) {
		mensaje = "*Se permiten letras, números y un '_'";
		error(elemento, mensaje);
		return false;
	}
		
	borrarError(elemento);
	return true;
}

function validarTelefono(elemento) {
	
	const telefono = elemento.value.trim();
	
	if (!/^\d+$/.test(telefono)) {
	    error(elemento, "*Solo se permiten números");
	    return false;
	}
	
	if (telefono.length < 9 || telefono.length > 15) {
		error(elemento, "*El telefono debe tener entre 9 y 15 digitos");
		return false;
	}
	
	borrarError(elemento);
	return true;
	
}

function validarFechaNacimiento(elemento) {
	const fechaNacimiento = elemento.value.trim();
	
	const regex = /^\d{4}-\d{2}-\d{2}$/;
	console.log(fechaNacimiento);
	if (!regex.test(fechaNacimiento)) {
		mensaje = "*El formato debe ser DD/MM/YYYY";
	    error(elemento, mensaje);
	    return false;
	}
	
	const fecha = new Date(fechaNacimiento);
	if (isNaN(fecha.getTime()) || fecha.getFullYear() < 1900) {
	    error(elemento, "*Fecha inválida");
	    return false;
	}

	const hoy = new Date();
	let edad = hoy.getFullYear() - fecha.getFullYear();
	const m = hoy.getMonth() - fecha.getMonth();
	if (m < 0 || (m === 0 && hoy.getDate() < fecha.getDate())) {
	    edad--;
	}

	if (edad < 18) {
	    error(elemento, "*Debes tener al menos 18 años");
	    return false;
	}

	borrarError(elemento);
	return true;
}

function validarPais(elemento) {
	
	const paisSelect = elemento.value;
	const paises = elemento.options;
	let encontrado = false;
	
	if (!paisSelect) {
		mensaje = "*Debes seleccionar un pais";
		error(elemento, mensaje);
		return false;
	}
	
	let i = 0;
	while (!encontrado && i < paises.length) {
		encontrado = (paises[i].value === paisSelect);
		if (encontrado) {
			mensaje = "*Pais seleccionado no encontrado";
			error(elemento, mensaje);
			return false;
		}
		i++;
	}
	
	borrarError(elemento);
	return true;
}

function validarRol(form, nombreRadio) {
	
	console.log(form);
	const roles = document.getElementsByName(nombreRadio);
	
	const rolChecked = Array.from(roles).some(r => r.checked);
	const mensajeErrorElement = form.getElementsByClassName("roles")[0].firstElementChild;
	if(!rolChecked) {
		mensaje = "*Debes de seleccionar un rol";
		mensajeErrorElement.innerHTML = mensaje;
		return false;
	}
	mensajeErrorElement.innerHTML = "";
	return true;
}

function error(elemento, mensaje) {
	elemento.previousElementSibling.innerHTML = mensaje;
	elemento.classList.add("error");
	elemento.focus();		
}

function borrarError(elemento) {		
	elemento.previousElementSibling.innerHTML = "";
	elemento.classList.remove("error");
}