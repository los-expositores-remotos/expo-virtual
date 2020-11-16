package ar.edu.unq.services.impl


import ar.edu.unq.dao.AdminDAO
import ar.edu.unq.dao.UsuarioDAO
import ar.edu.unq.dao.mongodb.MongoAdminDAOImpl
import ar.edu.unq.modelo.Admin
import ar.edu.unq.modelo.Usuario
import ar.edu.unq.services.AdminService
import ar.edu.unq.services.UsuarioService
import ar.edu.unq.services.impl.exceptions.*
import ar.edu.unq.services.runner.DataBaseType
import ar.edu.unq.services.runner.TransactionRunner
import ar.edu.unq.services.runner.TransactionType

class AdminServiceImpl(
        private val adminDAO: AdminDAO,
        private val dataBaseType: DataBaseType
) : AdminService {

    override fun recuperarAdmin(id: String): Admin {
        return TransactionRunner.runTrx({
            this.obtenerAdmin(id)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarAdmin(userName: String?, password: String?): Admin {
        return TransactionRunner.runTrx({
            this.adminDAO.findEq("userName",userName).filter{
                it.password == password
            }.firstOrNull() ?: throw AdministradorInexistenteException("El Admin no existe")
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun crearAdmin(admin: Admin) {
        TransactionRunner.runTrx({
            this.asegurarQueAdminNoExista(admin.id.toString())
            this.adminDAO.save(admin)
        }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    override fun recuperarATodosLosAdmin(): Collection<Admin> {
        return TransactionRunner.runTrx({ this.adminDAO.getAll() }, listOf(TransactionType.MONGO), this.dataBaseType)
    }

    private fun obtenerAdmin(id: String): Admin{
        val adminRecuperado = this.adminDAO.get(id)
        return adminRecuperado?:throw AdministradorInexistenteException("El administrador no existe")
    }

    private fun asegurarQueAdminNoExista(id: String) {
        if(this.adminDAO.get(id) != null){
            throw AdministradorExistenteException("El administrador ya existe")
        }
    }

    override fun deleteAll() {
        TransactionRunner.runTrx({ this.adminDAO.deleteAll() }, listOf(TransactionType.MONGO), this.dataBaseType)
    }
}