package ar.edu.unq.services

import ar.edu.unq.modelo.Admin
import ar.edu.unq.modelo.Proveedor
import ar.edu.unq.modelo.Usuario

interface UsuarioService {
    fun recuperarUsuario(id: String): Usuario
    fun recuperarUsuario(dni: Int): Usuario
    fun crearUsuario(usuario: Usuario)
    fun recuperarATodosLosUsuarios(): Collection<Usuario>
    fun deleteAll()


}