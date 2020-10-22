package ar.edu.unq

import modelo.Producto
import modelo.Proveedor
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import services.impl.ProveedorServiceImp
import kotlin.test.assertEquals

class FeedServiceTest {

//    private var serviceData: DataService = DataServiceImp(MongoDataDAO())
//    private val serviceProveedor: ProveedorServiceImp = ProveedorServiceImp()
    private val mongo = MongoConnection()

    @Before
    fun setUp() {
//        proveedorA = Proveedor("Gibson")
//        serviceProveedor.crearPatogeno(proveedorA)
    }

    @Test
    fun returnACollection(){
        val result = mongo.getCollection("Proveedores", Producto::class.java)
        Assert.assertEquals("Proveedores", result!!.namespace.collectionName)
    }

    @Test
    fun createAndReturnACollection(){
        //mongo.createCollection("Proveedores") // cambiar nombre hasta saber tirar un drop de la coleccion
        val result = mongo.getCollection("Proveedores", Producto::class.java)
        Assert.assertEquals("Proveedores", result!!.namespace.collectionName)
    }

    @After
    fun dropAll() {
        //serviceData.eliminarTodo()
    }
}
