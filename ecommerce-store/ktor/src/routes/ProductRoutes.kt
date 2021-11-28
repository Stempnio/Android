package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.productRouting() {
    route("/product") {
        get {
            call.respondText("product", status = HttpStatusCode.NotFound)
        }

        get("{id}") {
//            call.respondText(call.parameters["id"].toString())
            call.respondText("No product with if found")
        }

        post {

        }

        delete("{id}") {

        }

        put {

        }
    }
}

fun Application.registerProductRoutes() {
    routing {
        productRouting()
    }
}