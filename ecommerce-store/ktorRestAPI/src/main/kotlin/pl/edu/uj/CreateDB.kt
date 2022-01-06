package pl.edu.uj

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Schema
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
        SchemaUtils.create(AdminTable)

        createSampleDB()

    }

}

fun createSampleDB() {

    val admin1 = Admin("admin1", "pkowalski@gmail.com", "admin")
    addAdmin(admin1)

    val customer1 = Customer("cust1", "jan", "kowalski", "jankowalski@gmail.com", "1234")
    val customer2 = Customer("cust2", "pawel", "nowak", "pawelnowak@gmail.com", "1234")
    val customer3 = Customer("bartek", "bartek", "szwaja", "szwaja@gmail.com", "1234")
    val product1 = Product("laptop", 1000, "product 1")
    val product2 = Product( "pralka", 500, "product 2")
    val product3 = Product( "lodowka", 300, "product 3")
    val product4 = Product( "ksiazka", 20, "product 4")
    val product5 = Product( "rower", 500, "product 5")
    val product6 = Product( "glosniki", 250, "product 6")

    addProduct(product1)
    addProduct(product2)
    addProduct(product3)
    addProduct(product4)
    addProduct(product5)
    addProduct(product6)


    addCustomer(customer1)
    addCustomer(customer2)
    addCustomer(customer3)

    addToCart("cust1", 1)
    addToCart("cust1", 3)
    addToCart("cust1", 3)

    addToCart("cust2", 1)
    addToCart("cust2", 2)
    addToCart("cust2", 3)
    addToCart("cust2", 1)

    placeOrder("cust1")
}