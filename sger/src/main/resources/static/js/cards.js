const carteles = document.getElementsByClassName("cartel");
const cartelesPorpagina = 9;
let paginaActual = 1;

document.addEventListener("DOMContentLoaded", () => {
	Array.prototype.forEach.call(carteles, function(cartel) {
		cartel.addEventListener("click", () => {
			console.log(cartel);
			var src = cartel.getAttribute("src");
			console.log(src);
			var modal = document.createElement("div");
			modal.className = "lightbox-modal";
			
			var img_wrapper = document.createElement("div");
			img_wrapper.className = "lightbox-img-wrapper";
			
			var img = document.createElement("img");
			img.src = src;
			img.className = "lightbox-img";
			
			var cerrar = document.createElement("span");
			cerrar.innerHTML = "X";
			cerrar.className = "lightbox-cerrar";
			
			img_wrapper.appendChild(img)
			modal.appendChild(img_wrapper);
			modal.appendChild(cerrar);
			document.body.appendChild(modal);
			console.log(img);
			
			cerrar.addEventListener("click", function(e) {
				e.stopPropagation();
				document.body.removeChild(modal);
			});
			
			modal.addEventListener("click", function() {
				document.body.removeChild(modal);
			});
		})
	});
});

function setupPagination() {
	
	const pagination = document.getElementById("pagination");
	pagination.innerHTML = "";
	
	const numeroPaginas = Math.ceil(carteles.length / cartelesPorpagina);
	for (let i = 1; i <= numeroPaginas; i++) {
		const btn = document.createElement("button");
		btn.innerHTML = i;
		
		btn.addEventListener("click", () => {
			paginaActual = i;
		});
		pagination.appendChild(btn);	
	}
}

