package uj.edu.pl

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import uj.edu.pl.routes.configureRouting
import java.sql.Connection

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()

        install(ContentNegotiation) {
            gson {
            }
        }

    }.start(wait = true)

    Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel =
        Connection.TRANSACTION_SERIALIZABLE

        transaction {
        SchemaUtils.create(testTable)
        testTable.insert {
            it[name] = "testCity1"
        }

        println("${testTable.selectAll()}")
    }
}

object testTable: Table() {
    val name = varchar("name", 50)
}