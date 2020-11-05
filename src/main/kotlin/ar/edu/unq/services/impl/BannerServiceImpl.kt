package ar.edu.unq.services.impl

import ar.edu.unq.dao.BannerDAO
import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.services.BannerService
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner.runTrx
import ar.edu.unq.services.runner.TransactionType

class BannerServiceImpl(private val bannerDAO: BannerDAO, private val dataBaseType: DataBaseType) : BannerService {
    override fun crearBanner(banner: Banner) {
        runTrx({
            this.bannerDAO.save(banner)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarBanner(id: String): Banner {
        return runTrx({
            this.bannerDAO.get(id)
        }, listOf(TransactionType.MONGO), this.dataBaseType)!!
    }

    override fun recuperarTodosLosBanners(): List<Banner> {
        return runTrx({
            this.bannerDAO.getAll()
        }, listOf(TransactionType.MONGO), this.dataBaseType)!!
    }

}
