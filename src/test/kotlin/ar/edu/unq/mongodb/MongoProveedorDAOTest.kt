package ar.edu.unq.mongodb

import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
import kotlin.test.assertEquals

class MongoProveedorDAOTest: GenericMongoDAOTest<Proveedor>(MongoProveedorDAOImpl()) {
    override fun generarItems() {
        this.items.add(Proveedor("LaCompany",
                "www.imagenes.com/lacompany.png", "www.facebook.com/LaCompany",
                "www.instagram.com/LaCompany","www.lacompany.com"))
        this.items.add(Proveedor("LaSegundaCompany",
                "www.imagenes.com/lasegundacompany.png", "www.facebook.com/LaSegundaCompany",
                "www.instagram.com/LaSegundaCompany","www.lasegundacompany.com"))
        TransactionRunner.runTrx({
            this.dao.save(this.items)
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }

    override fun borrarNItems(n: Int) {
        for(i in 0..n){
            this.dao.delete(this.items[0].id.toString())
            this.items.removeAt(0)
        }
    }

    override fun encontrarItemsQueCumplenPropiedad() {
        val proveedoresRecuperados = TransactionRunner.runTrx({
            this.dao.findEq("companyName", "LaCompany")
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        assertEquals(this.items.filter { it.companyName == "LaCompany" }.toSet(), proveedoresRecuperados.toSet())
    }

    
}