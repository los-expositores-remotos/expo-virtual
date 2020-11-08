package ar.edu.unq.services.impl

import ar.edu.unq.dao.BannerDAO
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory
import ar.edu.unq.services.BannerService
import ar.edu.unq.services.impl.exceptions.BannerExistenteException
import ar.edu.unq.services.impl.exceptions.BannerInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner.runTrx
import ar.edu.unq.services.runner.TransactionType

class BannerServiceImpl(private val bannerDAO: BannerDAO, private val dataBaseType: DataBaseType) : BannerService {
    override fun crearBanner(banner: Banner) {
        runTrx({
            this.asegurarQueBannerNoExista(banner.id.toString())
            this.bannerDAO.save(banner)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarBanner(id: String): Banner {
        return runTrx({
            this.obtenerBanner(id)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarTodosLosBanners(): List<Banner> {
        return runTrx({
            this.bannerDAO.getAll()
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarTodosLosBanners(category: BannerCategory): List<Banner> {
        return runTrx({
            this.bannerDAO.findEq("category", category.toString())
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun deleteAll() {
        runTrx({
            this.bannerDAO.deleteAll()
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun borrarBanner(id: String) {
        runTrx({
            this.obtenerBanner(id)
            this.bannerDAO.delete(id)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    private fun obtenerBanner(id: String): Banner {
        return this.bannerDAO.get(id) ?: throw BannerInexistenteException("El banner no existe")
    }

    private fun asegurarQueBannerNoExista(id: String) {
        if(this.bannerDAO.get(id) != null){
            throw BannerExistenteException("El banner ya existe")
        }
    }
}
