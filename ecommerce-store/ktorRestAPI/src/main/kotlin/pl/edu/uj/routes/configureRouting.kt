package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.routing.*
import pl.edu.uj.routes.cartRouting
import pl.edu.uj.routes.customerRouting
import pl.edu.uj.routes.orderRouting
import pl.edu.uj.routes.productRouting

fun Application.configureRouting() {
    routing {
        productRouting()
        orderRouting()
        customerRouting()
        cartRouting()
    }
}