package pl.edu.uj.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

data class OrderDetails(val orderId : Int, val productId : Int, val quantity : Int)

object OrderDetailsTable : Table() {
    val orderId = integer("orderId").references(OrderTable.id)
    val productId = integer("productIt").references(ProductTable.id)
    val quantity = integer("quantity")
}

fun ResultRow.toOrderDetails() = OrderDetails(
    orderId = this[OrderDetailsTable.orderId],
    productId = this[OrderDetailsTable.productId],
    quantity = this[OrderDetailsTable.quantity]
)

fun insertOrderDetailsRow(orderId: Int, productId: Int, quantity: Int) {
    transaction {
        OrderDetailsTable.insert {
            it[OrderDetailsTable.orderId] = orderId
            it[OrderDetailsTable.productId] = productId
            it[OrderDetailsTable.quantity] = quantity
        }
    }
}