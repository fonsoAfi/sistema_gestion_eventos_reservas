/*
document.getElementById('hamburger').addEventListener('click', function() {
  document.getElementById('navmenu').classList.toggle('active');
});*/

document.addEventListener("DOMContentLoaded", () => {
	const cards = document.querySelectorAll(".card-body");
	
	cards.forEach((card, index) => {
		console.log(index);
		console.log(card);
	});

	const pageSize = 6;

	let currentPage = 0;

	function showPage(page) {
		const start = page * pageSize;
		const end = start + pageSize;
		
		cards.forEach((card, index) => {
			if (index >= start && index < end) {
				card.classList.add("visible");
			} else {
				card.classList.remove("visible");
			}
		});
	}

	showPage(currentPage);
	/*
	const dropdownToggle = document.getElementById("perfilDropdown"); 
	const dropdownMenu = document.getElementById("perfilMenu");
	console.log(dropdownMenu);
	dropdownToggle.addEventListener('click', function(e) {
		e.preventDefault();
		e.stopPropagation();
		
		const isActive = dropdownMenu.classList.contains('active');
		
		document.querySelectorAll('.dropdown-menu').forEach(menu => {
			menu.classList.remove('active');
		});
		
		document.querySelectorAll('.dropdown-toggle').forEach(toggle => {
			toggle.classList.remove('active');
		});
		
		if (!isActive) {
			dropdownMenu.classList.add('active');
			dropdownToggle.classList.add('active');
		}
	});
	
	document.addEventListener('click', function(e) {
		if (!dropdownToggle.contains(e.target)) {
			dropdownMenu.classList.remove('active');
			dropdownToggle.classList.remove('active');
		}
	});
	
	document.querySelectorAll('.dropdown-link').forEach(link => {
		link.addEventListener('click', function() {
			dropdownMenu.classList.remove('active');
			dropdownToggle.classList.remove('active');
		});
	});*/
});
