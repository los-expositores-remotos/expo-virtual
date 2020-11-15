Feature Login de Usuario

  Scenario: Se loguea con los datos ingresados
    Given un dni del usuario 39484178
    When el usuario ingresa su dni para loguearse
    Then el usuario se loguea

  Scenario: Solo se loguea un usuario registrado
    Given un dni del usuario 16900236
    When el usuario intenta loguearse
    Then el usuario no se puede loguear por no estar registrado

