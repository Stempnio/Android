package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.edu.uj.models.getAllOrders
import pl.edu.uj.models.getCustomerOrders
import pl.edu.uj.models.getOrder
import pl.edu.uj.models.placeOrder

fun Route.orderRouting() {
    route("/order") {
        // gets all orders
        get {
            call.respond(getAllOrders())
        }

        // gets all customer orders
        get("/{customer_id}") {
            val id = call.parameters["id"]
            if(id != null)
                call.respond(getCustomerOrders(id.toInt()))
        }

        // gets order by given id
        get("{id}") {
            val id = call.parameters["id"]
            if(id != null)
                call.respond(getOrder(id.toInt()))
        }

        // adds new order
        post("/{customer_id}") {
            val id = call.parameters["customer_id"]
            if(id != null)
                call.respond(placeOrder(id.toInt()))
        }

//        // deletes an order by given id
//        delete("{id}") {
//
//        }

    }
}
