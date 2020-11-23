# language: es
Característica: Eliminacion de banner

  Escenario: Al presionar el boton de eliminacion de Banner se despliegan todos los Banners
    Dados Un banner y una categoria "www.images.com/banner1.png" y "HOME"
    Dados Un banner y una categoria "www.images.com/banner2.png" y "CLASS"
    Cuando Creo los banners
    Entonces El banner "www.images.com/banner1.png" con categoria "HOME" existe
    Y El banner "www.images.com/banner2.png" con categoria "CLASS" existe

  Escenario: Si se presiona el icono de eliminacion de Banner se muestra una notificacion de eliminacion exitosa
    Dado Un banner existente
    Cuando Lo elimino
    Entonces El banner ya no existe