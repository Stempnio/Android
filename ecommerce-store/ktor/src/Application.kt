package pl.edu.uj

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import pl.edu.uj.routes.productRouting
import pl.edu.uj.routes.registerCustomerRoutes
import pl.edu.uj.routes.registerOrderRoutes
import pl.edu.uj.routes.registerProductRoutes

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        gson {}
    }
    registerProductRoutes()
    registerCustomerRoutes()
    registerOrderRoutes()

}



