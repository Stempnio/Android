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

// TODO quantity??
data class OrderDetails(val orderId : Int, val productId : Int)

object OrderDetailsTable : Table() {
    val orderId = integer("orderId").references(OrderTable.id)
    val productId = integer("productIt")
}

fun ResultRow.toOrderDetails() = OrderDetails(
    orderId = this[OrderDetailsTable.orderId],
    productId = this[OrderDetailsTable.productId]
)

// TODO functions