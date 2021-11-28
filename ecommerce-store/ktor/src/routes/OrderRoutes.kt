package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.routing.*

fun Route.orderRouting() {
    route("/order") {
        get {

        }

        get("{id}") {

        }

        post {

        }

        delete {

        }

        put {

        }
    }
}

fun Application.registerOrderRoutes() {
    routing {
        orderRouting()
    }
}