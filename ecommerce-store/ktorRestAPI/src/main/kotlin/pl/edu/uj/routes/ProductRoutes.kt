package pl.edu.uj.routes

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.edu.uj.models.*

fun Route.productRouting() {
    route("/product") {
        // gets all products
        get {
            call.respond(getAllProducts())
        }

//        // gets all products
//        get {
//            call.respond(Gson().toJson(getAllProducts()))
//        }

        // gets product by given id
        get("/{id}") {
            val id = call.parameters["id"]
            if(id != null) {
                val prod = getProduct(id.toInt())
                if(prod != null)
                    call.respond(prod)
                else
                    call.respond(HttpStatusCode.NotFound)
            }
        }

        // update product
        put {
            val product = call.receive<Product>()
            call.respond(updateProduct(product))
        }

        // adds product
        post {
            val product = call.receive<Product>()
            call.respond(addProduct(product))
        }

        // deletes all products
        delete {
            call.respond(deleteAllProducts())
        }

        // deletes product
        delete("/{id}") {
            val id = call.parameters["id"]
            if(id != null)
                call.respond(deleteProduct(id.toInt()))
        }
    }
}
