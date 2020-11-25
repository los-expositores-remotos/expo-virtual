# language: es

Característica: Formulario de carga de proveedor

  Escenario: Poder crear un nuevo proveedor con sus datos personales
    Dado Un proveedor con los siguientes datos
          | companyName   | proveedor         |
          | companyImage  | www.imagen.com    |
          | facebook      | www.facebook.com  |
          | instagram     | www.instagram.com |
          | web           | www.proveedor.com |
    Cuando Creo al proveedor con esos datos
    Entonces El proveedor "proveedor" se encuentra en la base de datos
    Y Sus datos son
         | companyImage  | www.imagen.com    |
         | facebook      | www.facebook.com  |
         | instagram     | www.instagram.com |
         | web           | www.proveedor.com |

  Escenario: Poder agregarle los productos del proveedor
    Dado Un proveedor "proveedor" sin ningun producto
    Cuando Le agrego el producto "producto1"
    Cuando Le agrego el producto "producto2"
    Entonces Los productos del proveedor recuperado son "producto1" y "producto2"
