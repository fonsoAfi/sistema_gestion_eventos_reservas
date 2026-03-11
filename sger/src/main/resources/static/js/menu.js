let lastScroll = 0;

function changeOpacity() {
	const currentScroll = window.scrollY;
	if (currentScroll > lastScroll) {
		document.getElementById("navbar").classList.add("transparency");
		document.getElementsByClassName("user-dropdown")[0].classList.add("transparency");
	} else if (currentScroll === lastScroll) {
		document.getElementById("navbar").classList.remove("transparency");
		document.getElementsByClassName("user-dropdown")[0].classList.remove("transparency");
	}
	
}

window.addEventListener("scroll", changeOpacity);

document.addEventListener("DOMContentLoaded", () => {
	const userIcon = document.querySelector('.icon-user');
	const userDropdown = document.querySelector('.user-dropdown');
	
	const hamburger = document.getElementById("hamburger");
	const menus = document.getElementsByClassName("menu");
		
	if (userIcon) {
		userIcon.addEventListener('click', (e) => {
		  e.stopPropagation();
		  userDropdown.classList.toggle('visible');
		  
		})
	
		document.body.addEventListener('click', () => {
		  userDropdown.classList.remove('visible');
		});
	}
	
	hamburger.addEventListener("click", () => {
		hamburger.classList.toggle("active");
		document.body.classList.toggle("menu-fullscreen");
	});
	
	const menuLinks = document.getElementsByClassName("menu-link");
	
	Array.prototype.forEach.call(menuLinks, function(link) {
		link.addEventListener("click", () => {
			if (document.body.classList.contains("menu-fullscreen")) {
				document.body.classList.remove("menu-fullscreen");
				hamburger.classList.remove("active");
			}
		});
	});
});
