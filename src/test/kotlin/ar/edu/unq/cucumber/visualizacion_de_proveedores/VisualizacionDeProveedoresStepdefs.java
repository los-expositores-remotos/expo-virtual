package ar.edu.unq.cucumber.visualizacion_de_proveedores;

import ar.edu.unq.modelo.Proveedor;
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
//    private ProveedorService proveedorService = new ProveedorServiceImpl(new MongoProveedorDAOImpl(), DataBaseType.TEST);
//    private ProductoService productoService = new ProductoServiceImpl(new MongoProveedorDAOImpl(), DataBaseType.TEST);
//    private String proveedorId;
//    private String producto;
//
//    @Given("^Un proveedor sin productos$")
//    public void unProveedorSinProductos() {
//        Proveedor proveedor = new Proveedor("Gibson", "data:image/jpeg;base64,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", "https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/");
//        this.proveedorId = proveedor.getId().toString();
//        this.proveedorService.crearProveedor(proveedor);
//    }
//
//    @When("^Agrego un producto \"([^\"]*)\"$")
//    public void agregoUnProducto(String producto) {
//        this.proveedorService.nuevoProducto(this.proveedorId, producto);
//    }
//
//    @Then("^Los productos del proveedor son \"([^\"]*)\" y \"([^\"]*)\"$")
//    public void losProductosDelProveedorSonY(String producto1, String producto2) throws Throwable {
//        List<Producto> productos = new ArrayList<Producto>();
//        productos.add(this.productoService.obtenerProducto(producto1));
//        productos.add(this.productoService.obtenerProducto(producto2));
//        //assertEquals(productos, this.proveedorService.obtenerProveedor(this.proveedorId).getProductos());
//    }
//
//    @Given("^Un producto vacio$")
//    public void unProductoVacio() {
//        this.producto = proveedorService.nuevoProducto(this.proveedorId, "ElProducto").getId().toString();
//    }
//
//    @When("^Le asigno la descripcion \"([^\"]*)\"$")
//    public void leAsignoLaDescripcion(String descripcion) throws Throwable {
//        Producto producto = this.productoService.obtenerProducto(this.producto);
//        producto.setDescription(descripcion);
//        this.productoService.actualizarProducto(producto);
//    }
//
//    @When("^ALe asigno el precio (\\d+)$")
//    public void aleAsignoElPrecio(int precio) {
//        Producto producto = this.productoService.obtenerProducto(this.producto);
//        producto.setItemPrice(precio);
//        this.productoService.actualizarProducto(producto);
//    }
//
//    @Then("^La descripción del producto es \"([^\"]*)\"$")
//    public void laDescripciónDelProductoEs(String descripcion) throws Throwable {
//        Producto producto = this.productoService.obtenerProducto(this.producto);
//        assertEquals(descripcion, producto.getDescription());
//    }
//
//    @And("^El precio del producto es (\\d+)$")
//    public void elPrecioDelProductoEs(int precio) {
//        Producto producto = this.productoService.obtenerProducto(this.producto);
//        assertEquals(precio, producto.getItemPrice());
//    }
}
