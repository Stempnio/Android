package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.edu.uj.models.customerStorage

fun Route.customerRouting() {
    route("/customer") {
        // gets all customers
        get {
//            if (customerStorage.isNotEmpty()) {
//                call.respond(customerStorage)
//            } else {
//                call.respondText("No customers found", status = HttpStatusCode.NotFound)
//            }
        }

        // gets cumstomer by id
        get("{id}") {
//            val id = call.parameters["id"] ?: return@get call.respondText(
//                "Missing or malformed id",
//                status = HttpStatusCode.BadRequest
//            )
//            val customer =
//                customerStorage.find { it.id == id } ?: return@get call.respondText(
//                    "No customer with id $id",
//                    status = HttpStatusCode.NotFound
//                )
//
//            call.respond(customer)
        }

        // ads new customer
        post {

        }

        // deletes customer by given id
        delete("{id}") {

        }

//        put {
//
//        }
    }
}

//fun Application.registerCustomerRoutes() {
//    routing {
//        customerRouting()
//    }
//}