Feature: Carga de Banner
#  Scenario: Al presionar el boton de carga de Banner se despliga el formulario
#    Given
#    When
#    Then
#
  Scenario: Si se completa el formulario correctamente, al presionar el boton de confirmacion de carga, se muestra una notificacion de carga exitosa
    Given Un link a una imagen "www.images.com/banner.jpg"
    Given Una categoria "HOME"
    When Creo al banner con esos datos
    Then El banner esta en la base de datos
    And Sus datos son "www.images.com/banner.jpg" y "HOME"

  Scenario: Si se completa el formulario incorrectamente al presionar el boton de confirmacion de carga, se muestra una notificacion de carga fallida
    Given Un link a una imagen ""
    Given Una categoria "HOME"
    When Trato de crear el banner
    Then Lanza una excepcion
    And No existe el banner
