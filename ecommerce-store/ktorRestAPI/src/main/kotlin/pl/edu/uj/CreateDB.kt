package pl.edu.uj

import com.google.gson.Gson
import io.netty.handler.codec.http.HttpMethod.POST
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import pl.edu.uj.models.*
import java.sql.Connection

fun dropExistingDB() {
    SchemaUtils.drop(ProductTable)
    SchemaUtils.drop(CartTable)
    SchemaUtils.drop(OrderTable)
    SchemaUtils.drop(CustomerTable)
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

        createSampleDB()
        //addProduct(Product(5, "name", 100))

//        println(Gson().toJson(ProductTable.selectAll().map { it.toProduct() }))

    }

}

fun createSampleDB() {
    val customer1 = Customer("cust1", "jan", "kowalski", "jankowalski@gmail.com")
    val customer2 = Customer("cust2", "pawel", "nowak", "pawelnowak@gmail.com")
    val product1 = Product("p1", 100)
    val product2 = Product( "p2", 100)
    val product3 = Product( "p3", 100)
    val product4 = Product( "p4", 100)

    addProduct(product1)
    addProduct(product2)
    addProduct(product3)
    addProduct(product4)

    addCustomer(customer1)
    addCustomer(customer2)
}