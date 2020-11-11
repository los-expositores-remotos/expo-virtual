package ar.edu.unq.API

import ar.edu.unq.modelo.Usuario
import javalinjwt.JWTProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import javalinjwt.JWTGenerator

class UserGenerator : JWTGenerator<Usuario> {
    override fun generate(user: Usuario, algorithm: Algorithm): String {
        val token = JWT.create().withClaim("id", user.id.toString())
        return token.sign(algorithm)
    }
}

class NotFoundToken: Exception()

class TokenJWT {

    val algorithm = Algorithm.HMAC256("very_secret")
    val generator = UserGenerator()
    val verifier = JWT.require(algorithm).build()
    val provider = JWTProvider(algorithm, generator, verifier)

    fun generateToken(user: Usuario): String {
        return provider.generateToken(user)
    }

    fun validate(token: String): String {
        val token = provider.validateToken(token)
        if (!token.isPresent) throw NotFoundToken()
        return token.get().getClaim("id").asString()
    }
}