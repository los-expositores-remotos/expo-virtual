Feature: Eliminar una imagen de un producto
  Scenario: Al visualizar las imagenes de un producto, se debe poder eliminar
    Given Un producto con las imagenes
          | www.images.com/image1.png |
          | www.images.com/image2.png |
    When Elimino la imagen "www.images.com/image2.png"
    Then El producto contiene solo a la imagen "www.images.com/image1.png"

  Scenario: Si la eliminación se realiza satisfactoriamente, debe aparecer un cartel que lo indique.
    Given Un producto con las imagenes
      | www.images.com/image1.png |
      | www.images.com/image2.png |
    When Elimino la imagen "www.images.com/image2.png"
    Then El producto contiene solo a la imagen "www.images.com/image1.png"

  Scenario: La imagen no debe aparecer cuando se visualiza producto
    Given Un producto con las imagenes
      | www.images.com/image1.png |
      | www.images.com/image2.png |
    When Elimino la imagen "www.images.com/image2.png"
    Then El producto no contiene a la imagen "www.images.com/image2.png"
