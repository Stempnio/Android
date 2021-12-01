package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.cartRouting() {
    route("/cart") {
        // gets all products from cart
        get {
//           call.respond(
//            transaction {
//                testTable.selectAll()
//            })
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