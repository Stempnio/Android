package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.routing.*

fun Route.orderRouting() {
    route("/order") {
        // gets all orders
        get {

        }

        // gets all customer orders
        get("/{customer_id}") {

        }

        // gets order by given id
        get("{id}") {

        }

        // adds new order
        post {

        }

        // deletes an order by given id
        delete("{id}") {

        }

//        put {
//
//        }
    }
}

//fun Application.registerOrderRoutes() {
//    routing {
//        orderRouting()
//    }
//}