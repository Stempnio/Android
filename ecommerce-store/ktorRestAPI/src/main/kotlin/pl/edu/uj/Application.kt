package pl.edu.uj

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
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
