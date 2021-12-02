package pl.edu.uj.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

data class Order(val id: Int, val customerId: Int, val date: LocalDateTime)

object OrderTable : Table() {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    val customerId = integer("consumerId").references(CustomerTable.id)
    val date = datetime("date")
}

fun ResultRow.toOrder() = Order(
    id = this[OrderTable.id],
    customerId = this[OrderTable.customerId],
    date = this[OrderTable.date]
)
// TODO functions