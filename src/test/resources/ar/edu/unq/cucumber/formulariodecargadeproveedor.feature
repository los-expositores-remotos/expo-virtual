Feature: Formulario de carga de proveedor

  Scenario: Poder crear un nuevo proveedor con sus datos personales
    Given Un proveedor con los siguientes datos
          | companyName   | proveedor         |
          | companyImage  | www.imagen.com    |
          | facebook      | www.facebook.com  |
          | instagram     | www.instagram.com |
          | web           | www.proveedor.com |
    When Creo al proveedor con esos datos
    Then El proveedor "proveedor" se encuentra en la base de datos
    And Sus datos son
         | companyImage  | www.imagen.com    |
         | facebook      | www.facebook.com  |
         | instagram     | www.instagram.com |
         | web           | www.proveedor.com |

  Scenario: Poder agregarle los productos del proveedor
    Given Un proveedor "proveedor" sin ningun producto
    When Le agrego el producto "producto1"
    When Le agrego el producto "producto2"
    Then Los productos del proveedor recuperado son "producto1" y "producto2"
