Feature: Eliminacion de Producto

  Scenario: Al seleccionar un proveedor y se despliegan todos los productos del proveedor
    Given un proveedor en la base datos
    When se le agrega un producto "producto1"
    When se le agrega otro producto "producto2"
    Then recupero el proveedor y sus productos son "producto1", "producto2"




  Scenario: Al presionar el icono de eliminar se elimina el producto y se recibe una notificación de baja exitosa

    Given dado un producto "producto1"
    When elimino el "producto1"
    Then no figura mas en la base de datos
