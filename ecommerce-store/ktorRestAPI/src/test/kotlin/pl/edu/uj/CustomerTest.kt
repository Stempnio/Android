package pl.edu.uj

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import pl.edu.uj.models.Customer
import kotlin.test.assertEquals


class CustomerTest {

    val customer = Customer("customerTest",
        "jan",
        "kowalski",
        "kowalski@gmail.com",
        "1234")

    private val customerUpdated = Customer("customerTest",
        "janek",
        "kowalski",
        "kowalskiii@gmail.com",
        "1234")

    @Test
    fun updateCustomer() {
        withTestApplication({ module(testing = true) }) {
            testPostAndGetCustomer()

            with(handleRequest(HttpMethod.Put, "/customer"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(customerUpdated))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/customer/${customer.id}").apply {
                assertEquals(Gson().toJson(customerUpdated), response.content)
                assertEquals(HttpStatusCode.OK, response.status())
            }

        }
    }

    @Test
    fun testPostCustomer() {
        withTestApplication({ module(testing = true) }) {
            with(handleRequest(HttpMethod.Post, "/customer"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(customer))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testPostAndGetCustomer() {

        withTestApplication({ module(testing = true) }) {

            with(handleRequest(HttpMethod.Post, "/customer"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(customer))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/customer/${customer.id}").apply {
                assertEquals(Gson().toJson(customer), response.content)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testDeleteCustomer() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Delete, "/customer/${customer.id}").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
