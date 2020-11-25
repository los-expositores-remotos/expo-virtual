# language: es

Característica: Login de Usuario

  Escenario: Se loguea con los datos ingresados
    Dado un dni del usuario 39484178
    Cuando el usuario ingresa su dni para loguearse
    Entonces el usuario se loguea

  Escenario: Solo se loguea un usuario registrado
    Dado un dni del usuario 16900236
    Cuando el usuario intenta loguearse
    Entonces el usuario no se puede loguear por no estar registrado

