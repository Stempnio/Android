package pl.edu.uj.models

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class Customer(val id : String, val firstName : String, val lastName : String, val email : String)

object CustomerTable : Table() {
    val id = varchar("id", 20)
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

fun deleteCustomer(id: String) {
    transaction {
        CustomerTable.deleteWhere { CustomerTable.id eq id }
    }
}

fun deleteAllCustomers() {
    transaction {
        CustomerTable.deleteAll()
    }
}

fun getAllCustomers() : List<Customer> {
    return transaction {
        CustomerTable.selectAll().map { it.toCustomer() }
    }
}

fun getCustomer(id : String) : List<Customer> {
    return transaction {
        CustomerTable.select { CustomerTable.id eq id }.map { it.toCustomer() }
    }
}

fun updateCustomer(customer : Customer) {
    transaction {
        CustomerTable.update({ CustomerTable.id eq customer.id }) {
            it[id] = customer.id
            it[firstName] = customer.firstName
            it[lastName] = customer.lastName
            it[email] = customer.email
        }
    }
}
