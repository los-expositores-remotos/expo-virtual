package ar.edu.unq.services.impl


import ar.edu.unq.dao.AdminDAO
import ar.edu.unq.dao.UsuarioDAO
import ar.edu.unq.dao.mongodb.MongoAdminDAOImpl
import ar.edu.unq.modelo.Admin
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.services.UsuarioService
import ar.edu.unq.services.impl.exceptions.AdministradorInexistenteException
import ar.edu.unq.services.impl.exceptions.ProveedorExistenteException
import ar.edu.unq.services.impl.exceptions.UsuarioExistenteException
import ar.edu.unq.services.impl.exceptions.UsuarioInexistenteException
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType

class UsuarioServiceImpl(
    private val usuarioDAO: UsuarioDAO,
    private val dataBaseType: DataBaseType
) : UsuarioService {

    private val adminDAO: AdminDAO = MongoAdminDAOImpl()

    override fun recuperarUsuario(id: String): Usuario {
        return TransactionRunner.runTrx({
            this.obtenerUsuario(id)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarUsuario(dni: Int): Usuario {
        return TransactionRunner.runTrx({
            this.usuarioDAO.findEq("dni",dni).firstOrNull()?: throw UsuarioInexistenteException("El usuario no existe")
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun crearUsuario(usuario: Usuario) {
        TransactionRunner.runTrx({
            this.asegurarQueUsuarioNoExista(usuario.id.toString())
            this.usuarioDAO.save(usuario)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarATodosLosUsuarios(): Collection<Usuario> {
        return TransactionRunner.runTrx({ this.usuarioDAO.getAll() }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarAdmin(userName: String?, password: String?): Admin {
        return TransactionRunner.runTrx({
            this.adminDAO.findEq("userName",userName).filter{
                it.password == password
            }.firstOrNull() ?: throw AdministradorInexistenteException("El Admin no existe")
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    private fun obtenerUsuario(id: String): Usuario{
        val usuarioRecuperado = this.usuarioDAO.get(id)
        return usuarioRecuperado?:throw UsuarioInexistenteException("El usuario no existe")
    }
    private fun asegurarQueUsuarioNoExista(id: String) {
        if(this.usuarioDAO.get(id) != null){
            throw UsuarioExistenteException("El usuario ya existe")
        }
    }

    override fun deleteAll() {
        TransactionRunner.runTrx({ this.usuarioDAO.deleteAll() }, listOf(TransactionType.MONGO), this.dataBaseType)
    }
}