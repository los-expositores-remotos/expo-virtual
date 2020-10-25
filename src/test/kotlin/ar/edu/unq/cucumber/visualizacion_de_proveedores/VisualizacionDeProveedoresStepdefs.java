package ar.edu.unq.cucumber.visualizacion_de_proveedores;

import ar.edu.unq.services.ProductoService;
import ar.edu.unq.services.impl.ProductoServiceImpl;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl;
import ar.edu.unq.modelo.Producto;
import ar.edu.unq.services.ProveedorService;
import ar.edu.unq.services.impl.ProveedorServiceImpl;
import ar.edu.unq.services.runner.DataBaseType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VisualizacionDeProveedoresStepdefs {
    private ProveedorService proveedorService = new ProveedorServiceImpl(new MongoProveedorDAOImpl(), DataBaseType.TEST);
    private ProductoService productoService = new ProductoServiceImpl(new MongoProveedorDAOImpl(), DataBaseType.TEST);
    private String proveedor;
    private String producto;

    @Given("^Un proveedor sin productos$")
    public void unProveedorSinProductos() {
        this.proveedor = this.proveedorService.crearProveedor("LaCompany").getId().toString();
    }

    @When("^Agrego un producto \"([^\"]*)\"$")
    public void agregoUnProducto(String producto) {
        this.proveedorService.nuevoProducto(this.proveedor, producto);
    }

    @Then("^Los productos del proveedor son \"([^\"]*)\" y \"([^\"]*)\"$")
    public void losProductosDelProveedorSonY(String producto1, String producto2) throws Throwable {
        List<Producto> productos = new ArrayList<Producto>();
        productos.add(this.productoService.obtenerProducto(producto1));
        productos.add(this.productoService.obtenerProducto(producto2));
        assertEquals(productos, this.proveedorService.obtenerProveedor(this.proveedor).getProductos());
    }

    @Given("^Un producto vacio$")
    public void unProductoVacio() {
        this.producto = proveedorService.nuevoProducto(this.proveedor, "ElProducto").getId().toString();
    }

    @When("^Le asigno la descripcion \"([^\"]*)\"$")
    public void leAsignoLaDescripcion(String descripcion) throws Throwable {
        Producto producto = this.productoService.obtenerProducto(this.producto);
        producto.setDescription(descripcion);
        this.productoService.actualizarProducto(producto);
    }

    @When("^ALe asigno el precio (\\d+)$")
    public void aleAsignoElPrecio(int precio) {
        Producto producto = this.productoService.obtenerProducto(this.producto);
        producto.setItemPrice(precio);
        this.productoService.actualizarProducto(producto);
    }

    @Then("^La descripción del producto es \"([^\"]*)\"$")
    public void laDescripciónDelProductoEs(String descripcion) throws Throwable {
        Producto producto = this.productoService.obtenerProducto(this.producto);
        assertEquals(descripcion, producto.getDescription());
    }

    @And("^El precio del producto es (\\d+)$")
    public void elPrecioDelProductoEs(int precio) {
        Producto producto = this.productoService.obtenerProducto(this.producto);
        assertEquals(precio, producto.getItemPrice());
    }
}
