package pl.edu.uj.routes

import io.ktor.application.*
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
                call.respond(customer)
            }
        }

        // update customer
        put {
            val customer = call.receive<Customer>()
            call.respond(updateCustomer(customer))
        }

        // adds customer
        post {
            val customer = call.receive<Customer>()
            call.respond(addCustomer(customer))
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
