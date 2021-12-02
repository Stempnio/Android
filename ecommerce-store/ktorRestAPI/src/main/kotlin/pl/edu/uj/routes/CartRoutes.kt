package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.edu.uj.models.*

fun Route.cartRouting() {
    route("/cart") {

        // gets all products from cart from every customer
        get {
            call.respond(getAllCarts())
        }

        // gets customer's cart
        get("/{customer_id}") {
            val customerId = call.parameters["customer_id"]
            if(customerId != null) {
                call.respond(getCustomerCart(customerId.toInt()))
            }
        }


        // puts product in customer's cart
        post("/{customer_id}/{product_id}") {
            val customerId = call.parameters["customer_id"]
            val productId = call.parameters["product_id"]
            if(customerId != null && productId != null) {
                call.respond(addToCart(customerId.toInt(), productId.toInt()))
            }
        }

        // deteles all products from cart from all customers
        delete {
            call.respond(deleteAllCarts())
        }

        // deletes a product from cart
        delete("/{customer_id}/{product_id}") {
            val customerId = call.parameters["customer_id"]
            val productId = call.parameters["product_id"]
            if(customerId != null && productId != null) {
                call.respond(deleteFromCart(customerId.toInt(), productId.toInt()))
            }
        }


    }
}