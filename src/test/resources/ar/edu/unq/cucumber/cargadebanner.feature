# language: es
Característica: Carga de Banner
#  Scenario: Al presionar el boton de carga de Banner se despliga el formulario
#    Given
#    When
#    Then
#
  Escenario: Si se completa el formulario correctamente, al presionar el boton de confirmacion de carga, se muestra una notificacion de carga exitosa
    Dado Un link a una imagen "www.images.com/banner.jpg"
    Dada Una categoria "HOME"
    Cuando Creo al banner con esos datos
    Entonces El banner esta en la base de datos
    Y Sus datos son "www.images.com/banner.jpg" y "HOME"

  Escenario: Si se completa el formulario incorrectamente al presionar el boton de confirmacion de carga, se muestra una notificacion de carga fallida
    Dado Un link a una imagen ""
    Dada Una categoria "HOME"
    Cuando Trato de crear el banner
    Entonces Lanza una excepcion
    Y No existe el banner
