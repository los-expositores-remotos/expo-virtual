# language: es
Característica: Alta de una Clase en vivo

  Escenario: Al presionar sobre el botón de agregar clase en vivo y cargar la imagen, el sistema se encarga de agregar dicha clase notificando que la acción ha sido exitosa
    Dado el link a una imagen "www.images.com/class.jpg"
    Dada la categoria "CLASS"
    Cuando creo la clase con esos datos
    Entonces la clase esta en la base de datos
    Y los datos de la clase son "www.images.com/class.jpg" y "CLASS"

  Escenario: En el caso de presionar sobre el botón de agregar clase en vivo y no haber cargado una imagen, debe notificarse que fue una carga fallida
    Dado el link a una imagen ""
    Dada la categoria "CLASS"
    Cuando trato de crear la clase
    Entonces arroja una excepcion
    Y no existe la clase