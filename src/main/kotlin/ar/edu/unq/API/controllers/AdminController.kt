package ar.edu.unq.API.controllers

import ar.edu.unq.API.*
import ar.edu.unq.services.AdminService
import ar.edu.unq.services.UsuarioService
import ar.edu.unq.services.impl.exceptions.AdministradorInexistenteException
import io.javalin.http.Context

class AdminController(private val backendAdminService: AdminService, val tokenJWT: TokenJWT, val jwtAccessManager: JWTAccessManager) {

    fun loginUserAdmin(ctx: Context){
        try {
            println("estoy al comienzo de loginUser")
            val adminLogin = ctx.bodyValidator<AdminLogin>()
                .check(
                    { it.userName != null && it.password != null},
                    "Invalid body : userName and password is required"
                )
                .get()
            println("pase el mapper estoy por validar el usuario")
            val admin = this.backendAdminService.recuperarAdmin(adminLogin.userName, adminLogin.password)
            println(admin.userName)
            ctx.header("Authorization", tokenJWT.generateTokenAdmin(admin))
            ctx.status(200)
            ctx.json(OkResultMapper("ok"))
        } catch (e: AdministradorInexistenteException) {
            ctx.status(404)
            ctx.json(ErrorViewMapper("error", "Admin not found"))
        }
    }


}