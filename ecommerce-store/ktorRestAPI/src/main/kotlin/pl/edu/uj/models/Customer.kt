package pl.edu.uj.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

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

val customerStorage = mutableListOf<Customer>()
