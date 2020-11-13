Feature: Registro de usuario

  Scenario:  El usuario ingresa sus datos y se registra
    Given un nombre "nombre"
    Given un apellido "apellido"
    Given un dni 39484178
    When se registra un usuario
    Then el usuario se encuentra en la DB
    And sus datos son "nombre", "apellido", 39484178

