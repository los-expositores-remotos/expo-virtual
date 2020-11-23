# language: es
Característica: Carga de un producto

    Escenario: Al ingresar todo los campos se carga el producto al proveedor,  Al ingresar todo los campos y
    presionar el botón de agregar se carga el producto al proveedor y se recibe una notificación de carga exitosa

        Dado un nombre de un producto "producto"
        Dada una descripción "descripcion"
        Dado un stock 10
        Dado un precio normal 100
        Dado un precio promocional 90
        Dada una longitud 11
        Dado un ancho 12
        Dado un alto 13
        Dado un peso en kilos 1
        Cuando creo un producto con estos datos
        Entonces el producto cargado "producto" se encuentra en la base de datos
        Y Sus datos son "producto", "descripcion", 10, 100, 90, 11, 12, 13, 1


