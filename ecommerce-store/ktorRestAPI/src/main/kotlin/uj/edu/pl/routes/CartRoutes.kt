package pl.edu.uj.routes

import io.ktor.routing.*

fun Route.cartRouting() {
    route("/cart") {
        // gets all products from cart
        get {

        }

        // puts product in cart
        post("{id}") {

        }

        // deteles all products from cart
        delete {

        }

        // deletes a product from cart
        delete("{id}") {

        }

//        put {
//
//        }


    }
}