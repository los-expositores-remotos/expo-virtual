package ar.edu.unq.mongodb

import ar.edu.unq.dao.mongodb.MongoBannerDAOImpl
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType
import kotlin.test.assertEquals

class MongoBannerDAOTest : GenericMongoDAOTest<Banner>(MongoBannerDAOImpl()) {
    override fun generarItems() {
        val i = 2
        BannerCategory.values().forEach {category ->
            (1..i).forEach {
                this.items.add(Banner("www.images.com/banner$category$it.png", category))
            }
            i.inc()
        }
        TransactionRunner.runTrx({ this.dao.save(this.items) }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }

    override fun encontrarItemsQueCumplenPropiedad() {
        val bannersRecuperados = TransactionRunner.runTrx({
            this.dao.findEq("category", BannerCategory.HOME.toString())
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
        assertEquals(this.items.filter { it.category == BannerCategory.HOME }.toSet(), bannersRecuperados.toSet())
    }

    override fun borrarNItems(n: Int) {
        TransactionRunner.runTrx({
            for(i in 0..n){
                this.dao.delete(this.items[0].id.toString())
                this.items.removeAt(0)
            }
        }, listOf(TransactionType.MONGO), DataBaseType.TEST)
    }
}