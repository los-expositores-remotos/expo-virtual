package ar.edu.unq.mongodb

import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.dao.mongodb.MongoProductoDAOImpl
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionRunner.runTrx
import ar.edu.unq.services.runner.TransactionType
import com.mongodb.client.model.Aggregates
import org.bson.Document
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class MongoProductoDAOTest : GenericMongoDAOTest<Producto>(MongoProductoDAOImpl()) {

    private val proveedorDAO: ProveedorDAO = MongoProveedorDAOImpl()
    private lateinit var proveedor: Proveedor

    override fun deleteColection() {
        super.deleteColection()
        val database = TransactionRunner.getTransaction().dataBase
        database.getCollection("Proveedor").drop()
    }

    override fun generarItems() {
        this.proveedor = Proveedor("Gibson", "foto","http://res.cloudinary.com/instaclongbarreiro/image/upload/v1606355739/lqbiomw4bllgi6yjb0s5.jpg", "https://www.facebook.com/GibsonES/", "https://www.instagram.com/gibsonguitar/?hl=es-la", "https://www.gibson.com/")
        this.items.add(Producto(this.proveedor.id, "Les Paul", "A electric guitar.", 7, 1000000, 800000, 10, 10, 10, 10))
        this.items.add(Producto(proveedor.id, "Les PaulB", "A electric guitar.", 7, 1000000, 800000, 10, 10, 10, 10))
        this.items.forEach { producto -> proveedor.addProduct(producto) }
        runTrx({ this.proveedorDAO.save(proveedor) }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }

    override fun deleteAll() {
        runTrx({ this.proveedorDAO.deleteAll() }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }

    override fun borrarNItems(n: Int) {
        for(i in 0..n){
            this.proveedor.removeProduct(this.items[0])
            this.items.removeAt(0)
        }
        this.proveedorDAO.update(proveedor, proveedor.id.toString())
    }

    @Test(expected = Exception::class)
    fun alIntentarGuardarProductosFalla() {
        runTrx({ this.dao.save(Producto()) }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }

    @Test(expected = Exception::class)
    fun alIntentarActualizarUnPoductoFalla() {
        this.items[0].itemName = "Hola"
        runTrx({
            this.dao.update(this.items[0], this.items[0].id.toString())
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }

    @Test(expected = Exception::class)
    fun alIntentarBorrarUnPoductoFalla() {
        runTrx({
            this.dao.delete(this.items[0].id.toString())
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }

    @Test(expected = Exception::class)
    fun alIntentarBorrarTodosLosProductosFalla() {
        runTrx({ this.dao.deleteAll() }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }

    override fun encontrarItemsQueCumplenPropiedad() {
        val productosRecuperados = runTrx({
            this.dao.findEq("itemName", "LesPaul")
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        assertEquals(this.items.filter { it.itemName == "LesPaul" }.toSet(), productosRecuperados.toSet())
    }

    @Test
    fun testSiLosAtributosDeLosElementosDeLaViewNoCoincidenSeCreaNuevamente() {
        runTrx({
            val database = TransactionRunner.getTransaction().dataBase
            database.getCollection("Producto").drop()
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)

        runTrx({
            val database = TransactionRunner.getTransaction().dataBase
            val proyectarProductos = Aggregates.project(Document("listProducts", "\$productos"))
            val separarProductos = Aggregates.unwind("\$listProducts")
            val proyectarProductosIndividuales = Aggregates.project(this.estructuraDeProductoFalsa())
            database.createView("Producto", "Proveedor", listOf(
                proyectarProductos,
                separarProductos,
                proyectarProductosIndividuales
            )
            )
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)

        var result = runTrx({
            val database = TransactionRunner.getTransaction().dataBase
            database.getCollection("Producto", Producto::class.java).find().toList()
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        Assert.assertNotEquals(this.items.toSet(), result.toSet())
        result = runTrx({ this.dao.getAll() }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        Assert.assertEquals(this.items.toSet(), result.toSet())
    }

    private fun estructuraDeProductoFalsa(): Document {
        return Document("__estructuraFalsa__", "\$SoyMasFalsaQueBilleteDeTresPesos")
    }
}