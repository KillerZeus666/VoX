document.getElementById("btn__iniciar-sesion").addEventListener("click", () => {
    document.querySelector(".formulario__register").style.display = "none";
    document.querySelector(".formulario__login").style.display = "block";
});

document.getElementById("btn__registrarse").addEventListener("click", () => {
    document.querySelector(".formulario__login").style.display = "none";
    document.querySelector(".formulario__register").style.display = "block";
});

document.querySelector(".formulario__login button").addEventListener("click", (event) => {
    event.preventDefault();
    const email = document.querySelector(".formulario__login input[type='text']").value;
    const password = document.querySelector(".formulario__login input[type='password']").value;

    // Simulación de autenticación
    if (email && password) {
        alert("Inicio de sesión exitoso");
        window.location.href = "feed.html";
    } else {
        alert("Por favor, ingrese su correo y contraseña.");
    }
});

document.querySelector(".formulario__register button").addEventListener("click", (event) => {
    event.preventDefault();
    const name = document.querySelector(".formulario__register input[placeholder='Nombre Completo']").value;
    const email = document.querySelector(".formulario__register input[placeholder='Correo Electrónico']").value;
    const username = document.querySelector(".formulario__register input[placeholder='Usuario']").value;
    const password = document.querySelector(".formulario__register input[placeholder='Contraseña']").value;

    if (name && email && username && password) {
        alert("Registro exitoso");
        window.location.href = "feed.html";
    } else {
        alert("Por favor, complete todos los campos.");
    }
});
