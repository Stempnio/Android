package pl.edu.uj.models

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class Cart(val customerId : String, val productId : Int, var quantity : Int)

object CartTable : Table() {
    val customerId = varchar("customerId", 30).references(CustomerTable.id)
    val productId = integer("productId").references(ProductTable.id)
    override val primaryKey = PrimaryKey(customerId, productId)

    var quantity = integer("quantity")
}

fun ResultRow.toCart() = Cart(
    customerId = this[CartTable.customerId],
    productId = this[CartTable.productId],
    quantity = this[CartTable.quantity]
)

fun getAllCarts() : List<Cart> {
    return transaction {
        CartTable.selectAll().map { it.toCart() }
    }
}

fun getCustomerCart(customerId : String) : List<Cart> {
    return transaction {
        CartTable.select { CartTable.customerId eq customerId }.map { it.toCart() }
    }
}

fun deleteCustomerCart(customerId: String) {
    transaction {
        CartTable.deleteWhere { CartTable.customerId eq customerId }
    }
}

fun deleteFromCart(customerId: String, productId: Int) {
    transaction {
        CartTable.deleteWhere { CartTable.customerId eq customerId and (CartTable.productId eq productId) }
    }
}

fun addToCart(customerId: String, productId: Int) {
    transaction {
        val count = CartTable.select { CartTable.customerId eq  customerId and (CartTable.productId eq productId) }.count()

        // product already in cart, increase quantity
        if(count > 0) {
            CartTable.update({ (CartTable.customerId eq customerId) and (CartTable.productId eq productId) }) {
                with(SqlExpressionBuilder) {
                    it[quantity] = quantity + 1
                }
            }
        } else {
            CartTable.insert {
                it[CartTable.customerId] = customerId
                it[CartTable.productId] = productId
                it[quantity] = 0

            }
        }
    }
}

fun deleteAllCarts() {
    transaction {
        CartTable.deleteAll()
    }
}
