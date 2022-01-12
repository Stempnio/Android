package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.edu.uj.models.*

fun Route.customerRouting() {

    route("/customer") {

        // get all customers
        get {
            call.respond(getAllCustomers())
        }


        // gets customer by given id
        get("/{id}") {
            val id = call.parameters["id"]
            if(id != null) {
                val customer = getCustomer(id)
                if(customer != null)
                    call.respond(customer)
                else
                    call.respond(HttpStatusCode.NotFound)
            }
        }

        // update customer
        put {
            try {
                val customer = call.receive<Customer>()
                call.respond(updateCustomer(customer))
            } catch (e : Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }
        }

        // adds customer
        post {
            try {
                val customer = call.receive<Customer>()
                call.respond(addCustomer(customer))
            } catch (e : Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }
        }

        // deletes all customers
        delete {
            call.respond(deleteAllCustomers())
        }

        // deletes customer by given id
        delete("/{id}") {
            val id = call.parameters["id"]
            if(id != null)
                call.respond(deleteCustomer(id))
        }

    }
}
