document.addEventListener("DOMContentLoaded", () => {
    const btnNuevo = document.getElementById("btnNuevoEvento");
    const modal = document.getElementById("modalEvento");
    const btnCerrar = document.getElementById("cerrarModal");


    btnNuevo.addEventListener("click", () => {
        modal.classList.remove("hidden");
    });

    btnCerrar.addEventListener("click", () => {
        modal.classList.add("hidden");
    });

    modal.addEventListener("click", (e) => {
        if (e.target === modal) {
            modal.classList.add("hidden");
        }
    });
});