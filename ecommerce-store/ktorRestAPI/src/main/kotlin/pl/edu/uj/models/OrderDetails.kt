package pl.edu.uj.models

import org.jetbrains.exposed.sql.*
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

fun getOrderDetails(orderId : Int) : List<OrderDetails> {
    return transaction {
        OrderDetailsTable.select { OrderDetailsTable.orderId eq orderId } . map {
            it.toOrderDetails()
        }
    }
}

fun getCustomerOrderDetails(customerId : String) : List<OrderDetails> {
    return transaction {
        (OrderDetailsTable innerJoin OrderTable)
            .slice(OrderDetailsTable.orderId, OrderDetailsTable.productId, OrderDetailsTable.quantity)
            .select { (OrderDetailsTable.orderId eq OrderTable.id) and (OrderTable.customerId eq customerId) }
            .map { it.toOrderDetails() }
    }
}

fun getAllOrderDetails() : List<OrderDetails> {
    return transaction {
        OrderDetailsTable.selectAll().map { it.toOrderDetails() }
    }
}

fun deleteOrderDetails(orderId : Int) {
    transaction {
        OrderDetailsTable.deleteWhere {
            OrderDetailsTable.orderId eq orderId
        }
    }
}

//fun deleteProductFromOrderDetails(orderId: Int, productId: Int) {
//    transaction {
//        OrderDetailsTable.deleteWhere {
//            OrderDetailsTable.orderId eq orderId and (OrderDetailsTable.productId eq productId)
//        }
//    }
//}