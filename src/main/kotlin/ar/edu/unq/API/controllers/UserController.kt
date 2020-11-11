package ar.edu.unq.API.controllers

import ar.edu.unq.API.*
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.services.UsuarioService
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse

class UserController(private val backendUsuarioService: UsuarioService, val tokenJWT: TokenJWT, val jwtAccessManager: JWTAccessManager) {

    fun createUser(ctx: Context) {
        try {
            val newUser = ctx.bodyValidator<UserRegisterMapper>()
                .check(
                    { it.userName != null/* && it.email != null */&& it.password != null/* && it.image != null && it.creditCard != null */}
                    , "Invalid body : name , email, password, image and crediCard is required"
                )
                .get()
            val user = Usuario(
                /*idGenerator.nextUserId(), */newUser.userName!!,/* newUser.creditCard!!, newUser.image!!, newUser.email!!,
                */newUser.password!!/*, mutableListOf(), mutableListOf()*/
            )
            backendUsuarioService.crearUsuario(user)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: ExistsException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun loginUser(ctx: Context) {
        try {
            val userLogin = ctx.bodyValidator<UserLogin>()
                .check(
                    { it.userName != null && it.password != null },
                    "Invalid body : userName and password is required"
                )
                .get()
            val user = this.validarUser(userLogin.userName!!, userLogin.password!!)
            ctx.header("Authorization", tokenJWT.generateToken(user))
            ctx.status(200)
            ctx.json(OkResultMapper("ok"))
        } catch (e: NotFoundException) {
            ctx.status(404)
            ctx.json(ErrorViewMapper("error", "User not found"))
        }
    }

    private fun validarUser(user: String, password: String): Usuario {
        val users = backendUsuarioService.recuperarATodosLosUsuarios()
        return users.firstOrNull { it.userName == user && it.password == password } ?: throw NotFoundException(
            "usuario",
            "password",
            "User not found"
        )
    }

    fun getUser(ctx: Context) {
        try {
            val token = ctx.header("Authorization")!!
            val user = jwtAccessManager.getUser(token)
            ctx.json(UserViewMapper(user.userName))
        } catch (e: NotFoundException) {
            throw NotFoundResponse("User not found")
        }
    }
}