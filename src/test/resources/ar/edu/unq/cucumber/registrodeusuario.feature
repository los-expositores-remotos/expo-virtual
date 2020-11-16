Feature: Registro de usuario

  Scenario:  El usuario ingresa sus datos y se registra
    Given un nombre "Tobias"
    Given un apellido "Towers"
    Given un dni 39484178
    When se registra un usuario
    Then el usuario se encuentra en la DB
    And sus datos son "Tobias", "Towers", 39484178

  Scenario: Si el usuario ingresa un dni menor a un millón se le notifica y no puede registrarse
    Given un nombre "nombre"
    Given un apellido "apellido"
    Given un dni 84178
    When se quiere registrar un usuario
    Then el usuario no se puede registrar por ingresar un dni menor a un millon

    Scenario: Si un usuario registrado se quiere volver a registrar, se le notifica que ya lo está
      Given un dni 39484178
      Given el usuario que se quiere volver a registrar
      When se registra
      Then el usuario no se puede volver a registrar con un dni ya registrado

