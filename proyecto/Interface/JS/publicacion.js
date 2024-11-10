function crearPublicacion(modoAnonimo = false) {
    const texto = document.getElementById("input-publicacion").value;
    if (!texto.trim()) {
        alert("No se puede publicar un texto vacío.");
        return;
    }

    const publicacion = {
        texto: texto,
        anonimo: modoAnonimo
    };

    alert(modoAnonimo ? "Publicación anónima creada." : "Publicación normal creada.");
    document.getElementById("input-publicacion").value = "";
}

document.getElementById("btn-publicar-normal").addEventListener("click", () => {
    crearPublicacion(false);
});

document.getElementById("btn-publicar-anonimo").addEventListener("click", () => {
    crearPublicacion(true);
});

function eliminarPublicacion(publicacionId) {
    if (confirm("¿Estás seguro de que deseas eliminar esta publicación?")) {
        alert("Publicación eliminada.");
    }
}

function revelarIdentidad(publicacionId) {
    if (confirm("¿Quieres revelar tu identidad en esta publicación?")) {
        alert("Identidad revelada en la publicación.");
    }
}