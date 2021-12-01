package pl.edu.uj

import com.google.gson.Gson
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


        //addProduct(Product(5, "name", 100))

//        println(Gson().toJson(ProductTable.selectAll().map { it.toProduct() }))

    }

}