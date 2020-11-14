Feature Login de Usuario

  Scenario: Se loguea con los datos ingresados
    Given un dni del usuario
    When el usuario ingresa sus datos de logueo
    Then el usuario se loguea

  Scenario: Solo se loguea un usuario registrado
    Given Un dni del usuario
    When El usuario intenta loguearse
    Then El usuario no se puede loguear por no estar registrado