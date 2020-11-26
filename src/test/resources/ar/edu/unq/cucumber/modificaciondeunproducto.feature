# language: es

Característica: Modificación de un Producto
  Escenario: Como administrador quiero modificar un producto para que el usuario lo  vea correctamente

    Dado Un nombre de un producto "producto"
    Dada Una descripción "descripcion"
    Dado Un stock 10
    Dado Un precio normal 100
    Dado Un precio promocional 90
    Cuando Creo un producto con estos datos
    Cuando Cuando modifico su nombre por "sarasa"
    Entonces sus datos son "sarasa", "descripcion", 10, 100, 90
