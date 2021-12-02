package pl.edu.uj.models

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

data class Customer(val id : Int, val firstName : String, val lastName : String, val email : String)

object CustomerTable : Table() {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    val firstName = varchar("firstName", 50)
    val lastName = varchar("lastName", 50)
    val email = varchar("email", 50)

}

fun ResultRow.toCustomer() = Customer (
    id = this[CustomerTable.id],
    firstName = this[CustomerTable.firstName],
    lastName = this[CustomerTable.lastName],
    email = this[CustomerTable.email]
)

// TODO functions

fun addCustomer(customer : Customer) {
    transaction {
        CustomerTable.insert {
            it[id] = customer.id
            it[firstName] = customer.firstName
            it[lastName] = customer.lastName
            it[email] = customer.email
        }
    }
}

fun deleteCustomer(id: Int) {
    transaction {
        CustomerTable.deleteWhere { CustomerTable.id eq id }
    }
}

fun deleteAllCustomers() {
    transaction {
        CustomerTable.deleteAll()
    }
}

fun getAllCustomers() {
    return transaction {
        CustomerTable.selectAll().map { it.toCustomer() }
    }
}

fun getCustomer(id : Int) : Customer {
    return transaction {
        CustomerTable.select { CustomerTable.id eq id }.map { it.toCustomer() }
    }[0]
}
