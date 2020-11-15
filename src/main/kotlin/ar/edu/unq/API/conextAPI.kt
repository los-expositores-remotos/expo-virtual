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
/*    val searchController = SearchController(backendProveedorService, backendProductoService)*/
    val userController = UserController(backendUsuarioService, tokenJWT, jwtAccessManager)
    val adminController = AdminController(backendUsuarioService, tokenJWT, jwtAccessManager)

 //   println(backendUsuarioService.recuperarAdmin("KikitoGonzalez","muajaja").userName)

/*    runTrx({
        MongoAdminDAOImpl().save(Admin("admin","admin"))
    }, listOf(TransactionType.MONGO),DataBaseType.PRODUCCION)*/

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
            get(userController::getUser, mutableSetOf<Role>(Roles.USER, Roles.ADMIN, Roles.ANYONE))
        }
/*            path("search") {
            get(searchController::searchByText, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN))
        }*/

        path("banners") {
            get(bannerController::banners, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN))
            post(bannerController::addBanner, mutableSetOf<Role>(Roles.ADMIN, Roles.ANYONE))
            path(":bannerId") {
                delete(bannerController::deleteBanner, mutableSetOf<Role>(Roles.ADMIN, Roles.ANYONE))
            }
            path(":bannerCategory"){
                get(bannerController::bannersByCategory, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN, Roles.ANYONE))
            }
        }

        path("companies") {
            get(companyController::allCompanies, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN))
            post(companyController::createSupplier, mutableSetOf<Role>(Roles.ADMIN, Roles.ANYONE))
            path("images") {
                get(companyController::imagesCompanies, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN))
            }
            path("names") {
                get(companyController::namesCompanies, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN))
            }
            path("massive") {
                post(companyController::createMassive, mutableSetOf<Role>(Roles.ADMIN, Roles.ANYONE))
            }
            path(":supplierId") {
                get(companyController::getSupplierById, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN))
                delete(companyController::deleteSupplier, mutableSetOf<Role>(Roles.ADMIN, Roles.ANYONE))
                put(companyController::modifySupplier, mutableSetOf<Role>(Roles.ANYONE, Roles.ADMIN))
            }
        }

        path("products") {
            get(productController::allProducts, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN))
            post(productController::addProduct, mutableSetOf<Role>(Roles.ADMIN, Roles.ANYONE))
            path("search") {
                get(productController::searchProducts, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN))
            }
            path(":productId") {
                get(productController::getProductById, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN))
                delete(productController::deleteProduct, mutableSetOf<Role>(Roles.ADMIN, Roles.ANYONE))
                put(productController::modifyProduct, mutableSetOf<Role>(Roles.ADMIN, Roles.ANYONE))
            }
            path("supplier") {
                path(":supplierId") {
                    get(productController::getProductsBySuppId, mutableSetOf<Role>(Roles.ANYONE,Roles.USER,Roles.ADMIN))
                }
            }
            path("massive") {
                post(productController::createMassive, mutableSetOf<Role>(Roles.ADMIN, Roles.ANYONE))
            }
        }
    }
}