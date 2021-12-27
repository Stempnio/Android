package pl.edu.uj.models

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class Product(val name : String, val price : Int, val description : String, val id : Int = -1)

object ProductTable : Table() {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    val name = varchar("name", 50)
    val description = varchar("description", 300)
    val price = integer("price")

}

fun ResultRow.toProduct() = Product (
    id = this[ProductTable.id],
    name = this[ProductTable.name],
    price = this[ProductTable.price],
    description = this[ProductTable.description]
)

fun addProduct(product: Product) {
    transaction {
        ProductTable.insert {
            //it[id] = product.id
            it[name] = product.name
            it[price] = product.price
            it[description] = product.description
        }
    }
}

fun updateProduct(product: Product) {
    transaction {
        ProductTable.update({ ProductTable.id eq product.id }) {
            it[name] = product.name
            it[price] = product.price
            it[id] = product.id
            it[description] = product.description
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

fun getAllProducts() : List<Product> {
    return transaction {
        ProductTable.selectAll().map { it.toProduct() }
    }
}

fun getProduct(id : Int) : Product? {
    return transaction {
        ProductTable.select { ProductTable.id eq id }.map { it.toProduct() }.firstOrNull()
    }
}