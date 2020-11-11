package ar.edu.unq.services

import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.modelo.Usuario

interface UsuarioService {
    fun recuperarUsuario(id: String): Usuario
    fun crearUsuario(user: Usuario)
    fun recuperarATodosLosUsuarios(): Collection<Usuario>
}