package ar.edu.unq

import ar.edu.unq.API.CompanyController
import ar.edu.unq.API.SetScenario
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.*
import modelo.Expo

fun main(args: Array<String>) {

    val backend = SetScenario().getExpo()
    val companyController = CompanyController(backend)
    val app = Javalin.create {
        it.defaultContentType = "application/json"
        it.enableCorsForAllOrigins()
    }

    app.start(7000)
    app.routes {

        path("companies") {
            path("images") {
                get(companyController::imagesCompanies)
            }
            path("names") {
                get(companyController::namesCompanies)
            }
        }

        path("products") {
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

