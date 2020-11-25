# language: es

Característica: Login Admin

  Escenario:  Ingresar su usuario y su password.
    Dado un usuario "agustina"
    Dado un password "pinero"
    Cuando el admin ingresa su usuario y password
    Entonces se loguea a la aplicacion

  Escenario: Si ingresa los datos de un administrador no registrado se lo notifica con un cartel que lo manifieste
    Dado un usuario "pepita"
    Dado un password "sarasa"
    Cuando el admin intenta loguearse
    Entonces el admin no se puede loguear por no estar registrado

