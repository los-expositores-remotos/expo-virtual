Feature: Registro de usuario

  Scenario:  El usuario ingresa sus datos y se registra
    Given un nombre "Tobias"
    Given un apellido "Towers"
    Given un dni 39484178
    When se registra un usuario
    Then el usuario se encuentra en la DB
    And sus datos son "Tobias", "Towers", 39484178

  Scenario: Si el usuario ingresa un dni inválido se le notifica y no puede registrarse
    Given un nombre "nombre"
    Given un apellido "apellido"
    Given un dni 84178
    When se quiere registrar un usuario
    Then el usuario no se puede registrar por ingresar un dni invalido

