package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.edu.uj.models.*

fun Route.orderRouting() {
    route("/order") {
        // gets all orders
        get {
            call.respond(getAllOrders())
        }

        // gets all customer orders
        get("/customer/{customer_id}") {
            val id = call.parameters["customer_id"]
            if(id != null)
                call.respond(getCustomerOrders(id))
        }

        // gets order by given id
        get("/{id}") {
            try {
                val id = call.parameters["id"]
                if (id != null) {
                    val order = getOrder(id.toInt())
                    if (order != null)
                        call.respond(order)
                    else
                        call.respond(HttpStatusCode.NotFound)
                }
            } catch (e : Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }
        }

        // adds new order
        post("/{customer_id}") {
            try {
                val id = call.parameters["customer_id"]
                if (id != null)
                    call.respond(placeOrder(id))
            } catch (e : Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }

        }

        // deletes an order by given id
        delete("/{id}") {
            val id = call.parameters["id"]
            if(id != null)
                call.respond(deleteOrder(id.toInt()))
        }

        delete {
            call.respond(deleteAllOrders())
        }

    }
}
