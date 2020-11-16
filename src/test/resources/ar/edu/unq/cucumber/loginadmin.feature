Feature: Login Admin

  Scenario:  Ingresar su usuario y su password.
    Given un usuario "agustina"
    Given un password "pinero"
    When el admin ingresa su usuario y password
    Then se loguea a la aplicacion

  Scenario: Si ingresa los datos de un administrador no registrado se lo notifica con un cartel que lo manifieste
    Given un usuario "pepita"
    Given un password "sarasa"
    When el admin intenta loguearse
    Then el admin no se puede loguear por no estar registrado

