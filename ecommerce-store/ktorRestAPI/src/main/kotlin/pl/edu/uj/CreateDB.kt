package pl.edu.uj

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import pl.edu.uj.models.*
import java.sql.Connection

fun dropExistingDB() {
    SchemaUtils.drop(ProductTable)
    SchemaUtils.drop(CartTable)
    SchemaUtils.drop(OrderTable)
    SchemaUtils.drop(CustomerTable)
    SchemaUtils.drop(OrderDetailsTable)
}

fun createDB() {

    Database.connect("jdbc:sqlite:./data/db.sqlite", "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    transaction {

        dropExistingDB()

        SchemaUtils.create(ProductTable)
        SchemaUtils.create(CartTable)
        SchemaUtils.create(OrderTable)
        SchemaUtils.create(CustomerTable)
        SchemaUtils.create(OrderDetailsTable)

        createSampleDB()

    }

}

fun createSampleDB() {
    val customer1 = Customer("cust1", "jan", "kowalski", "jankowalski@gmail.com")
    val customer2 = Customer("cust2", "pawel", "nowak", "pawelnowak@gmail.com")
    val product1 = Product("p1", 100, "product 1")
    val product2 = Product( "p2", 100, "product 2")
    val product3 = Product( "p3", 100, "product 3")
    val product4 = Product( "p4", 100, "product 4")

    addProduct(product1)
    addProduct(product2)
    addProduct(product3)
    addProduct(product4)

    addCustomer(customer1)
    addCustomer(customer2)

    addToCart("cust1", 1)
    addToCart("cust1", 3)
    addToCart("cust1", 3)

    addToCart("cust2", 1)
    addToCart("cust2", 2)
    addToCart("cust2", 3)
    addToCart("cust2", 1)

    placeOrder("cust1")
}