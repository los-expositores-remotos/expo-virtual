# language: es
Característica: Eliminar una clase en vivo

  Escenario: Poder eliminar una clase en vivo
    Dados Una clase y una categoria "www.images.com/claseaovivo1.png" y "CLASS"
    Cuando Creo la clase
    Entonces La clase "www.images.com/claseaovivo1.png" con categoria "CLASS" existe
    Y Lo deleteo
    Y La clase ya no existe