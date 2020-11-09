package ar.edu.unq

import ar.edu.unq.API.controllers.CompanyController
import ar.edu.unq.API.controllers.BannerController
import ar.edu.unq.API.controllers.ProductController
import ar.edu.unq.dao.mongodb.MongoBannerDAOImpl
import ar.edu.unq.API.controllers.SearchController
import ar.edu.unq.dao.mongodb.MongoProductoDAOImpl
import ar.edu.unq.dao.mongodb.MongoProveedorDAOImpl
import ar.edu.unq.services.impl.BannerServiceImpl
import ar.edu.unq.services.impl.ProductoServiceImpl
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.*

fun main(args: Array<String>) {

    val backendProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.PRODUCCION)
    val backendProductoService =
        ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.PRODUCCION)
    val backendBannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.PRODUCCION)
    val bannerController = BannerController(backendBannerService, backendProveedorService, backendProductoService)
    val productController = ProductController(backendProveedorService, backendProductoService)
    val companyController = CompanyController(backendProveedorService, backendProductoService)
    val searchController = SearchController(backendProveedorService, backendProductoService)

    val app = Javalin.create {
        it.defaultContentType = "application/json"
        it.enableCorsForAllOrigins()
    }

    app.start(7000)
    app.routes {

        path("search") {
            get(searchController::searchByText)
        }

        path("banners") {
            get(bannerController::banners)
            post(bannerController::addBanner)
            path(":bannerId") {
                delete(bannerController::deleteBanner)
            }
            path(":bannerCategory"){
                get(bannerController::bannersByCategory)
            }
        }

        path("companies") {
            get(companyController::allCompanies)
            post(companyController::createSupplier)
            path("images") {
                get(companyController::imagesCompanies)
            }
            path("names") {
                get(companyController::namesCompanies)
            }
            path("massive") {
                post(companyController::createMassive)
            }
            path("search"){
                get(companyController::searchCompanies)
            }
            path(":supplierId") {
                get(companyController::getSupplierById)
                delete(companyController::deleteSupplier)
                put(companyController::modifySupplier)
            }
        }

        path("products") {
            get(productController::allProducts)
            post(productController::addProduct)
            path("search") {
                get(productController::searchProducts)
            }
            path(":productId") {
                get(productController::getProductById)
                delete(productController::deleteProduct)
                put(productController::modifyProduct)  //verlo a fondo no funciona bien
            }
            path("supplier") {
                path(":supplierId") {
                    get(productController::getProductsBySuppId)
                }
            }
            /*
            path("bestSellers") {
                get(companyController::productsBestSellers)
            }
            path("newest") {
                get(companyController::productsNewest)
            }
            path("promoPrice") {
                get(companyController::productsWPromoPrice)
            }
            path("order") {
            path(":byLowerPrice") {
                get(companyController::orderByLowerPrice)
            }
            path(":byHigherPrice") {
                get(companyController::orderByHigherPrice)
            }
            path(":byOldest") {
                get(companyController::orderByOldest)
            }
            path(":byNewest") {
                get(companyController::orderByNewest)
            }
            path(":byBestSellers") {
                get(companyController::orderByBestSellers)
            }
            path(":byAlphabeticalOrderDesc") {
                get(companyController::orderByAlphabeticDesc)
            }
            path(":byAlphabeticalOrderAsc") {
                get(companyController::orderByAlphabeticAsc)
            }
        }*/
            //  path("/user") {
            //    get(mC.userController::getUser, mutableSetOf<Role>(Roles.USER))
        }
    }
}

