package ar.edu.unq.services.impl


import ar.edu.unq.dao.UsuarioDAO
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.services.UsuarioService
import ar.edu.unq.services.impl.exceptions.UsuarioInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType

class UsuarioServiceImpl(
    private val usuarioDAO: UsuarioDAO,
    private val dataBaseType: DataBaseType
) : UsuarioService {

    override fun recuperarUsuario(id: String): Usuario {
        return TransactionRunner.runTrx({
            this.obtenerUsuario(id)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }
    private fun obtenerUsuario(id: String): Usuario{
        val usuarioRecuperado = this.usuarioDAO.get(id)
        return usuarioRecuperado?:throw UsuarioInexistenteException("El usuario no existe")
    }

}