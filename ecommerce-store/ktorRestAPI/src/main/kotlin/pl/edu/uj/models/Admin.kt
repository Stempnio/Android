package pl.edu.uj.models

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class Admin(val id : String, val email : String, val password : String)

object AdminTable : Table() {
    val id = varchar("id", 20)
    override val primaryKey = PrimaryKey(id)
    val email = varchar("email", 50)
    val password = varchar("password", 50)
}


fun ResultRow.toAdmin() = Admin (
    id = this[AdminTable.id],
    email = this[AdminTable.email],
    password = this[AdminTable.password]
)

fun getAdmin(id : String) : Admin? {
    return transaction {
        AdminTable.select { AdminTable.id eq id }.map { it.toAdmin() }.singleOrNull()
    }
}

fun addAdmin(admin: Admin) {
    transaction {
        AdminTable.insert {
            it[id] = admin.id
            it[email] = admin.email
            it[password] = admin.password
        }
    }
}

fun deleteAdmin(id : String) {
    transaction {
        AdminTable.deleteWhere { AdminTable.id eq id }
    }
}
