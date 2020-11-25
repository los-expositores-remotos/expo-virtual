# language: es

Característica: Registro de usuario

  Escenario:  El usuario ingresa sus datos y se registra
    Dado un nombre "Tobias"
    Dado un apellido "Towers"
    Dado un dni 39484178
    Cuando se registra un usuario
    Entonces el usuario se encuentra en la DB
    Y sus datos son "Tobias", "Towers", 39484178

  Escenario: Si el usuario ingresa un dni menor a un millón se le notifica y no puede registrarse
    Dado un nombre "nombre"
    Dado un apellido "apellido"
    Dado un dni 84178
    Cuando se quiere registrar un usuario
    Entonces el usuario no se puede registrar por ingresar un dni menor a un millon

  Escenario: Si un usuario registrado se quiere volver a registrar, se le notifica que ya lo está
    Dado un dni 39484178
    Cuando se intenta registrar nuevamente
    Entonces el usuario no se puede volver a registrar con un dni ya registrado

