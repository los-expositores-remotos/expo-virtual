package ar.edu.unq.dao.mongodb

import ar.edu.unq.dao.ProveedorDAO
import ar.edu.unq.modelo.Producto
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.types.ObjectId

class MongoProveedorDAOImpl : ProveedorDAO, GenericMongoDAO<Proveedor>(Proveedor::class.java) {
    val productoDAO = MongoProductoDAOImpl()

    override fun mapToDocument(obj: Proveedor): Document {
        val document = Document()
        document["id"] = obj.id.toString()
        document["companyName"] = obj.companyName
        document["companyImage"] = obj.companyImage
        document["facebook"] = obj.facebook
        document["instagram"] = obj.instagram
        document["web"] = obj.web
        return document
    }


    override fun mapFromDocument(document: Document): Proveedor {
        val proveedor = Proveedor()
   //     proveedor.id = ObjectId(document["id"].toString())
        proveedor.companyName = document["companyName"].toString()
        proveedor.companyImage = document["companyImage"].toString()
        proveedor.facebook = document["facebook"].toString()
        proveedor.instagram = document["instagram"].toString()
        proveedor.web = document["web"].toString()
        return proveedor
    }

    override fun saveInTrx(proveedor: Proveedor, dataBaseType: DataBaseType) {
        this.saveInTrx(listOf(proveedor), dataBaseType)
    }

    override fun saveInTrx(proveedores: List<Proveedor>, dataBaseType: DataBaseType) {
        TransactionRunner.runTrx({
            for(proveedor in proveedores){
                this.save(proveedor)
            }
        }, listOf(TransactionType.MONGO), dataBaseType)
        this.productoDAO.saveOrUpdate(proveedores.flatMap { proveedor -> proveedor.productos }, dataBaseType)
    }

    override fun updateInTrx(proveedor: Proveedor, id: String?, dataBaseType: DataBaseType){
        TransactionRunner.runTrx({ this.update(proveedor, proveedor.id.toString()) }, listOf(TransactionType.MONGO), dataBaseType)
        this.productoDAO.saveOrUpdate(proveedor.productos, dataBaseType)
    }

    override fun getAllInTrx(dataBaseType: DataBaseType): List<Proveedor> {
        val proveedores = TransactionRunner.runTrx({ this.getAll() }, listOf(TransactionType.MONGO), dataBaseType)
        this.completar(proveedores, dataBaseType)
        return proveedores
    }

    override fun getInTrx(id: String?, dataBaseType: DataBaseType): Proveedor? {
        return getByInTrx("id", id, dataBaseType)
    }

    override fun getByInTrx(property:String, value: String?, dataBaseType: DataBaseType): Proveedor?{
        val proveedor = TransactionRunner.runTrx({ this.getBy(property, value) }, listOf(TransactionType.MONGO), dataBaseType)
        this.completarProveedor(proveedor, dataBaseType)
        return proveedor
    }

    override fun <E> findEqInTrx(field:String, value:E, dataBaseType: DataBaseType): List<Proveedor> {
        return findInTrx(Filters.eq(field, value),dataBaseType)
    }

    override fun findInTrx(filter: Bson, dataBaseType: DataBaseType): List<Proveedor> {
        val proveedores = TransactionRunner.runTrx({ this.find(filter) }, listOf(TransactionType.MONGO), dataBaseType)
        this.completar(proveedores, dataBaseType)
        return proveedores
    }

    private fun completar(proveedores: List<Proveedor>, dataBaseType: DataBaseType){
        TransactionRunner.runTrx({
            for(proveedor in proveedores){
                proveedor.productos = this.productoDAO.findEq("idProveedor", proveedor.id.toString()).toMutableList()
            }
        }, listOf(TransactionType.MONGO), dataBaseType)
    }

    private fun completarProveedor(proveedor: Proveedor?, dataBaseType: DataBaseType){
        proveedor?.productos = TransactionRunner.runTrx({ this.productoDAO.findEq("idProveedor", proveedor?.id.toString()).toMutableList() }, listOf(TransactionType.MONGO), dataBaseType)
    }
}