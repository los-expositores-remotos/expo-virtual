Feature: Formulario de carga de proveedor

  Scenario: Poder crear un nuevo proveedor con sus datos personales
    Given Un nombre de proveedor "proveedor"
    Given Un enlace a una imagen "www.imagen.com"
    Given Un enlace a facebook "www.facebook.com"
    Given Un enlace a instagram "www.instagram.com"
    Given Un enlace a su pagina web "www.proveedor.com"
    When Creo al proveedor con esos datos
    Then El proveedor "proveedor" se encuentra en la base de datos
    And Sus datos son "www.imagen.com", "www.facebook.com", "www.instagram.com", "www.proveedor.com"

  Scenario: Poder agregarle los productos del proveedor
    Given Un proveedor "proveedor" sin ningun productos
    When Le agrego el producto "producto1"
    When Le agrego el producto "producto2"
    Then Los productos del proveedor recuperado son "producto1" y "producto2"
