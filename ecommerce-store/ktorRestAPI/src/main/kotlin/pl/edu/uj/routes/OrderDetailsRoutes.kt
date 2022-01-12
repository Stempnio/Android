package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.edu.uj.models.getAllOrderDetails
import pl.edu.uj.models.getCustomerOrderDetails
import pl.edu.uj.models.getOrderDetails

fun Route.orderDetailsRouting() {
    route("/orderDetails") {

        get {
            call.respond(getAllOrderDetails())
        }

        get("/{order_id}") {
            try {
                val id = call.parameters["order_id"]
                if (id != null) {
                    call.respond(getOrderDetails(id.toInt()))
                }
            } catch (e : Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }
        }

        get("/customer/{customer_id}") {
            val customerId = call.parameters["customer_id"]
            if(customerId != null) {
                call.respond(getCustomerOrderDetails(customerId))
            }
        }


        /*
        deleting order details possible only from OrderRoutes.
        there is no way to update order details, once the order is placed no
        further changes are possible.
         */
    }
}