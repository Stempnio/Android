package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.productRouting() {
    route("/product") {
        // gets all products
        get {
//            call.respondText("product", status = HttpStatusCode.NotFound)
        }

        // gets product by given id
        get("{id}") {
//            call.respondText(call.parameters["id"].toString())
            call.respondText("No product with id found")
        }

        // adds product
        post {

        }

        // deletes product
        delete("{id}") {

        }

//        put {
//
//        }
    }
}

//fun Application.registerProductRoutes() {
//    routing {
//        productRouting()
//    }
//}