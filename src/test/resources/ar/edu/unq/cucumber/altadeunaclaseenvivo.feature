Feature: Alta de una Clase en vivo

  Scenario: Al presionar sobre el botón de agregar clase en vivo y cargar la imagen, el sistema se encarga de agregar dicha clase notificando que la acción ha sido exitosa
    Given el link a una imagen "www.images.com/class.jpg"
    Given la categoria "CLASS"
    When creo la clase con esos datos
    Then la clase esta en la base de datos
    And los datos de la clase son "www.images.com/class.jpg" y "CLASS"

  Scenario: En el caso de presionar sobre el botón de agregar clase en vivo y no haber cargado una imagen, debe notificarse que fue una carga fallida
    Given el link a una imagen ""
    Given la categoria "CLASS"
    When trato de crear la clase
    Then arroja una excepcion
    And no existe la clase