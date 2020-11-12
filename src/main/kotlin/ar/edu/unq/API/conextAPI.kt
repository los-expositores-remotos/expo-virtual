package ar.edu.unq

import ar.edu.unq.API.JWTAccessManager
import ar.edu.unq.API.TokenJWT
import ar.edu.unq.API.controllers.*
import ar.edu.unq.dao.mongodb.*
import ar.edu.unq.modelo.Admin
import ar.edu.unq.services.impl.BannerServiceImpl
import ar.edu.unq.services.impl.ProductoServiceImpl
import ar.edu.unq.services.impl.ProveedorServiceImpl
import ar.edu.unq.services.impl.UsuarioServiceImpl
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner.runTrx
import ar.edu.unq.services.runner.TransactionType
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.security.Role

enum class Roles : Role {
    ANYONE, USER, ADMIN
}

fun main(args: Array<String>) {






    val backendProveedorService = ProveedorServiceImpl(MongoProveedorDAOImpl(), DataBaseType.PRODUCCION)
    val backendProductoService =
        ProductoServiceImpl(MongoProveedorDAOImpl(), MongoProductoDAOImpl(), DataBaseType.PRODUCCION)
    val backendBannerService = BannerServiceImpl(MongoBannerDAOImpl(), DataBaseType.PRODUCCION)
    val backendUsuarioService = UsuarioServiceImpl(MongoUsuarioDAOImpl(), DataBaseType.PRODUCCION)
    val tokenJWT = TokenJWT()
    val jwtAccessManager = JWTAccessManager(tokenJWT, backendUsuarioService)
    val bannerController = BannerController(backendBannerService, backendProveedorService, backendProductoService)
    val productController = ProductController(backendProveedorService, backendProductoService)
    val companyController = CompanyController(backendProveedorService, backendProductoService)
    val searchController = SearchController(backendProveedorService, backendProductoService)
    val userController = UserController(backendUsuarioService, tokenJWT, jwtAccessManager)
    val adminController = AdminController(backendUsuarioService, tokenJWT, jwtAccessManager)

    println(backendUsuarioService.recuperarAdmin("KikitoGonzalez","muajaja").userName)

    runTrx({
        MongoAdminDAOImpl().save(Admin("admin","admin"))
    }, listOf(TransactionType.MONGO),DataBaseType.PRODUCCION)

    val app = Javalin.create {
        it.defaultContentType = "application/json"
        it.accessManager(jwtAccessManager)
        it.enableCorsForAllOrigins()
    }

    app.before {
        it.header("Access-Control-Expose-Headers", "*")
        it.header("Access-Control-Allow-Origin", "*")
    }

    app.start(7000)
    app.routes {
        path("/register") {
            post(userController::createUser, mutableSetOf<Role>(Roles.ANYONE))
        }
        path("/login") {
            post(userController::loginUser, mutableSetOf<Role>(Roles.ANYONE))
            path("/admin") {
                post(adminController::loginUserAdmin, mutableSetOf<Role>(Roles.ANYONE))
            }
        }

        path("/user") {
            get(userController::getUser, mutableSetOf<Role>(Roles.USER, Roles.ADMIN))
        }
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

