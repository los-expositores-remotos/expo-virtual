# language: es
Característica: Eliminacion de Producto

  Escenario: Al seleccionar un proveedor y se despliegan todos los productos del proveedor
    Dado un proveedor en la base datos
    Cuando se le agrega un producto "producto1"
    Cuando se le agrega otro producto "producto2"
    Entonces recupero el proveedor y sus productos son "producto1", "producto2"




  Escenario: Al presionar el icono de eliminar se elimina el producto y se recibe una notificación de baja exitosa

    Dado dado un producto "producto1"
    Cuando elimino el "producto1"
    Entonces no figura mas en la base de datos
