package pl.edu.uj.models

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class Product(val id : Int, val name : String, val price : Int)

object ProductTable : Table() {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    val name = varchar("name", 50)
    val price = integer("price")

}

fun ResultRow.toProduct() = Product (
    id = this[ProductTable.id],
    name = this[ProductTable.name],
    price = this[ProductTable.price]
)

fun addProduct(product: Product) {
    transaction {
        ProductTable.insert {
            it[id] = product.id
            it[name] = product.name
            it[price] = product.price
        }
    }
}

fun deleteProduct(id: Int) {
    transaction {
        ProductTable.deleteWhere { ProductTable.id eq id }
    }
}

fun deleteAllProducts() {
    transaction {
        ProductTable.deleteAll()
    }
}

fun getAllProducts() {
    return transaction {
        ProductTable.selectAll().map { it.toProduct() }
    }
}

fun getProduct(id : Int) : Product {
    return transaction {
        ProductTable.select { ProductTable.id eq id }.map { it.toProduct() }
    }[0]
}