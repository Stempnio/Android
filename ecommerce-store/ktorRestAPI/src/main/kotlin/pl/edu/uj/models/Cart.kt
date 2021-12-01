package pl.edu.uj.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class Cart(val customerId : Int, val productId : Int, var quantity : Int)

object CartTable : Table() {
    val customerId = integer("customerId").references(CustomerTable.id)
    val productId = integer("productId").references(ProductTable.id)
    var quantity = integer("quantity")
}

fun ResultRow.toCart() = Cart(
    customerId = this[CartTable.customerId],
    productId = this[CartTable.productId],
    quantity = this[CartTable.quantity]
)

// TODO functions
