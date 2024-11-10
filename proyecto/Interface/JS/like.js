function darLike(publicacionId, modoAnonimo = false) {
    const tipoLike = modoAnonimo ? "anónimo" : "normal";
    alert(`Like ${tipoLike} añadido a la publicación ${publicacionId}.`);
}

function quitarLike(publicacionId) {
    alert(`Like eliminado de la publicación ${publicacionId}.`);
}

document.querySelectorAll(".btn-like").forEach(button => {
    button.addEventListener("click", (event) => {
        const publicacionId = event.target.getAttribute("data-publicacion-id");
        const esAnonimo = event.target.classList.contains("anonimo");
        
        if (esAnonimo) {
            darLike(publicacionId, true);
        } else {
            darLike(publicacionId, false);
        }
    });
});

document.querySelectorAll(".btn-quitar-like").forEach(button => {
    button.addEventListener("click", (event) => {
        const publicacionId = event.target.getAttribute("data-publicacion-id");
        quitarLike(publicacionId);
    });
});