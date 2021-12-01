package pl.edu.uj

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import pl.edu.uj.models.*
import pl.edu.uj.routes.configureRouting

fun main() {
    createDB()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()

        install(ContentNegotiation) {
            gson {
            }
        }

    }.start(wait = true)
}
