# language: es
Característica: Eliminar una imagen de un producto
  Escenario: Al visualizar las imagenes de un producto, se debe poder eliminar
    Dado Un producto con las imagenes
          | www.images.com/image1.png |
          | www.images.com/image2.png |
    Cuando Elimino la imagen "www.images.com/image2.png"
    Entonces El producto contiene solo a la imagen "www.images.com/image1.png"

  Escenario: Si la eliminación se realiza satisfactoriamente, debe aparecer un cartel que lo indique.
    Dado Un producto con las imagenes
      | www.images.com/image1.png |
      | www.images.com/image2.png |
    Cuando Elimino la imagen "www.images.com/image2.png"
    Entonces El producto contiene solo a la imagen "www.images.com/image1.png"

  Escenario: La imagen no debe aparecer cuando se visualiza producto
    Dado Un producto con las imagenes
      | www.images.com/image1.png |
      | www.images.com/image2.png |
    Cuando Elimino la imagen "www.images.com/image2.png"
    Entonces El producto no contiene a la imagen "www.images.com/image2.png"
