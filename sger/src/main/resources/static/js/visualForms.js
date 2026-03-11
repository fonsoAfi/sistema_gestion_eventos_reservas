document.addEventListener("DOMContentLoaded", function(e) {
	displayPaso(e, pasoCompletado);
});

function displayPaso(e, pasoCompletado) {
	const paso = document.getElementsByClassName("paso");
	const btnPaso = document.getElementsByClassName("btn-paso");
	const className= "active"
	
	paso[0].classList.add(className);
	if (pasoCompletado) {
		for (let i = 0; i < btnPaso.length; i++) {
			btnPaso[i].addEventListener("click", (e) => {
				e.preventDefault();
				paso[i].classList.toggle(className);
				if ((i + 1) === btnPaso.length)  {
					paso[i-1].classList.toggle(className);
				} else {
					paso[i+1].classList.toggle(className);
				}
			});
		}	
	}
}