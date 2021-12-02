package pl.edu.uj.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

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