package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        productRouting()
        orderRouting()
        customerRouting()
        cartRouting()
        orderDetailsRouting()
        adminRouting()
    }
}