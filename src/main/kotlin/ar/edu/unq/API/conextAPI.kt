package ar.edu.unq

import ar.edu.unq.API.controllers.CompanyController
import ar.edu.unq.API.SetScenario
import ar.edu.unq.API.controllers.BannerController
import ar.edu.unq.API.controllers.ProductController
import ar.edu.unq.API.controllers.SupplierController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.*

fun main(args: Array<String>) {

    val backend = SetScenario().getExpo()
    val companyController = CompanyController(backend)
    val supplierController = SupplierController(backend)
    val bannerController = BannerController(backend)
    val productController = ProductController(backend)
    val app = Javalin.create {
        it.defaultContentType = "application/json"
        it.enableCorsForAllOrigins()
    }

    app.start(7000)
    app.routes {

        path("companies") {
            get(supplierController::allSupliers)
            path("images") {
                get(companyController::imagesCompanies)
            }
            path("names") {
                get(companyController::namesCompanies)
            }
        }

        path("products") {
            post(productController::addProduct)
            path(":productId") {
    //            get(productController::getProductById)
                delete(productController::deleteProduct)
    //            put(productController::modifyProduct)
            }

            get(companyController::allProducts)
            path(":supplierId") {
                get(supplierController::getProductsBySuppId)
            }
            path("bestSellers") {
                get(companyController::producstBestSellers)
            }
            path("newest") {
                get(companyController::productsNewest)
            }
            path("promoPrice") {
                get(companyController::productsWPromoPrice)
            }
        }

        path("banners") {
            get(bannerController::allBanners)
            post(bannerController::agregarBanner)
            path(":bannerId") {
                delete(bannerController::deleteBanner)
            }
        }

        path("supplier") {
            post(supplierController::createSupplier)
            path(":supplierId") {
                get(supplierController::getSupplierById)
                delete(supplierController::deleteSupplier)
                put(supplierController::modifySupplier)
            }
        }

/*        path("order") {
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

