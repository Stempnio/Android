package pl.edu.uj.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.edu.uj.models.*

fun Route.adminRouting() {
    route("/admin") {

        get("/{id}") {
            val id = call.parameters["id"]
            if(id != null) {
                val admin = getAdmin(id)
                if(admin != null)
                    call.respond(admin)
                else
                    call.respond(HttpStatusCode.NotFound)
            }
        }

        post {
            val admin = call.receive<Admin>()
            call.respond(addAdmin(admin))
        }

        delete("/{id}") {
            val id = call.parameters["id"]
            if(id!=null) {
                call.respond(deleteAdmin(id))
            }
        }
    }
}