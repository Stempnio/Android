package pl.edu.uj

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

fun TestApplicationEngine.postCustomerTest() {
    with(handleRequest(HttpMethod.Post, "/customer"){
        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        setBody(Gson().toJson(customer))
    }) {
        assertEquals(HttpStatusCode.OK, response.status())
    }
}

fun TestApplicationEngine.getCustomerTest() {
    handleRequest(HttpMethod.Get, "/customer/${customer.id}").apply {
        assertEquals(Gson().toJson(customer), response.content)
        assertEquals(HttpStatusCode.OK, response.status())
    }
}

class CustomerTest {

    @Test
    fun testPostCustomer() {
        withTestApplication({ module(testing = true) }) {
            postCustomerTest()
        }
    }

    @Test
    fun testPostAndGetCustomer() {
        withTestApplication({ module(testing = true) }) {
            postCustomerTest()
            getCustomerTest()
        }
    }

    @Test
    fun updateCustomer() {
        withTestApplication({ module(testing = true) }) {

            postCustomerTest()

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
    fun testGetNonExistingCustomer() {
        withTestApplication({ module(testing = true) }) {

            handleRequest(HttpMethod.Get, "/customer/${customer.id}").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
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

    @Test
    fun testDeleteAllCustomers() {
        withTestApplication({ module(testing = true) }) {

            postCustomerTest()

            handleRequest(HttpMethod.Delete, "/customer").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/customer").apply {
                assertEquals(Gson().toJson(emptyCustomerList), response.content)
            }
        }
    }
}
