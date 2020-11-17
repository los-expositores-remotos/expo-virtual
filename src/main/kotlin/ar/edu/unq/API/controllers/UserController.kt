package ar.edu.unq.API.controllers

import ar.edu.unq.API.*
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.services.UsuarioService
import ar.edu.unq.services.impl.exceptions.UsuarioConDniInvalidoException
import ar.edu.unq.services.impl.exceptions.UsuarioExistenteException
import ar.edu.unq.services.impl.exceptions.UsuarioInexistenteException
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse

class UserController(private val backendUsuarioService: UsuarioService, val tokenJWT: TokenJWT, val jwtAccessManager: JWTAccessManager) {

    fun createUser(ctx: Context) {
        try {
            val newUser = ctx.bodyValidator<UserRegisterMapper>()
                .check(
                    { it.nombre != null && it.apellido != null && it.dni != null}
                    , "Invalid body : nombre, apellido y dni is required"
                )
                .get()
            val user = Usuario(newUser.nombre!!,newUser.apellido!!, newUser.dni!!)
            backendUsuarioService.crearUsuario(user)
            ctx.status(201)
            ctx.json(OkResultMapper("ok"))
        } catch (e: UsuarioExistenteException) {
            throw BadRequestResponse(e.message.toString())
        } catch (e: UsuarioConDniInvalidoException) {
            throw BadRequestResponse(e.message.toString())
        }
    }

    fun loginUser(ctx: Context) {
        try {
            println("estoy al comienzo de loginUser")
            val userLogin = ctx.bodyValidator<UserLogin>()
                .check(
                    { it.dni != null  },
                    "Invalid body : dni is required"
                )
                .get()
            println("pase el mapper estoy por validar el usuario")
            val user = backendUsuarioService.recuperarUsuario(userLogin.dni!!)
            ctx.header("Authorization", tokenJWT.generateToken(user))
            ctx.status(200)
            ctx.json(UserViewMapper(user.nombre, user.apellido))
        } catch (e: UsuarioInexistenteException) {
            ctx.status(404)
            ctx.json(ErrorViewMapper("error", "User not found"))
        }
    }

    fun getUser(ctx: Context) {
        try {
            val token = ctx.header("Authorization")!!
            val user = jwtAccessManager.getUser(token)
            ctx.json(UserViewMapper(user.nombre,user.apellido))
        } catch (e: UsuarioInexistenteException) {
            throw NotFoundResponse("User not found")
        }
    }


}