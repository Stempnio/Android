package pl.edu.uj.models

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

data class Order(val customerId: String, val date: String, val id: Int = -1)

object OrderTable : Table() {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    val customerId = varchar("consumerId", 20).references(CustomerTable.id)
    val date = varchar("date", 30)
}

fun ResultRow.toOrder() = Order(
    id = this[OrderTable.id],
    customerId = this[OrderTable.customerId],
    date = this[OrderTable.date]
)

fun getAllOrders() : List<Order> {
    return transaction {
        OrderTable.selectAll().map { it.toOrder() }
    }
}

fun getCustomerOrders(customerId: String) : List<Order> {
    return transaction {
        OrderTable.select { OrderTable.customerId eq customerId }.map { it.toOrder() }
    }
}

fun getOrder(id : Int) : Order? {
    return transaction {
        OrderTable.select { OrderTable.id eq id }.map { it.toOrder() }.singleOrNull()
    }
}

fun placeOrder(customerId: String) {
    transaction {
        val customerCart = getCustomerCart(customerId)
        val orderId : Int
        if (customerCart.isNotEmpty()) {
            orderId = OrderTable.insert {
                it[OrderTable.customerId] = customerId
                it[date] = LocalDateTime.now().toString()
            } get OrderTable.id

            for(cart in customerCart) {
                insertOrderDetailsRow(orderId, cart.productId, cart.quantity)
            }

            // after placing an order delete all products from cart
            deleteCustomerCart(customerId)

        } else {
            // TODO empty cart
        }

    }

}


fun deleteOrder(orderId : Int) {
    transaction {
        OrderTable.deleteWhere { OrderTable.id eq orderId }
        deleteOrderDetails(orderId)
    }
}