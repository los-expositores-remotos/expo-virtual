package ar.edu.unq.services

import ar.edu.unq.modelo.banner.Banner
import ar.edu.unq.modelo.banner.BannerCategory

interface BannerService {
    fun crearBanner(banner: Banner)
    fun recuperarBanner(id: String): Banner
    fun recuperarTodosLosBanners(): List<Banner>
    fun recuperarBannersPorCategoria(bannerCategory: BannerCategory): List<Banner>
    fun recuperarTodosLosBanners(category: BannerCategory): List<Banner>
    fun deleteAll()
}
