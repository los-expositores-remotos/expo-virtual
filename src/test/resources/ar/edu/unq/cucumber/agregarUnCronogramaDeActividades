Feature: Agrega Un Cronograma De Actividades

  Scenario: Al presionar sobre el botón de agregar cronograma de actividades y cargar la imagen, el sistema se encarga de agregarlo, notificando que la acción ha sido exitosa
    Given un link a una imagen "www.images.com/schedule.jpg"
    Given una categoria "SCHEDULE"
    When creo al schedule con esos datos
    Then el schedule esta en la base de datos
    And sus datos son "www.images.com/schedule.jpg" y "SCHEDULE"

  Scenario: En el caso de presionar sobre el botón de agregar cronograma de actividades y no haber cargado una imagen, debe notificarse que fue una carga fallida
    Given un link a una imagen ""
    Given una categoria "SCHEDULE"
    When trato de crear el schedule
    Then lanza una excepcion
    And no existe el schedule