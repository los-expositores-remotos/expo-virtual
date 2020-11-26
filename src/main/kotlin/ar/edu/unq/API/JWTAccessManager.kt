package ar.edu.unq.API

import ar.edu.unq.Roles
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.services.UsuarioService
import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse

class JWTAccessManager(val tokenJWT: TokenJWT, private val backendUsuarioService: UsuarioService) : AccessManager {

    fun getUser(token: String): Usuario {
        try {
            val userId = tokenJWT.validate(token)
            return backendUsuarioService.recuperarUsuario(userId)
        } catch (e: NotFoundToken) {
            throw  UnauthorizedResponse("Token not found")
        } catch (e: NotFoundException) {
            throw  UnauthorizedResponse("Invalid Token")
        } catch (e: IllegalArgumentException) {
            throw  UnauthorizedResponse("Invalid Token")
        }
    }

    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authorization")
        when {
            token == null && roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            token == null -> throw UnauthorizedResponse("Token not found")
            roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            roles.contains(Roles.USER) -> {
                getUser(token)
                handler.handle(ctx)
            }
            roles.contains(Roles.ADMIN) -> {
                getUser(token)
                handler.handle(ctx)
            }
        }
    }
}