package pl.edu.uj

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Before
import pl.edu.uj.models.Customer
import pl.edu.uj.models.Order
import kotlin.test.Test
import kotlin.test.assertEquals

class OrderTest {

    fun TestApplicationEngine.postOrderTest() {
        with(handleRequest(HttpMethod.Post, "/order/${customer.id}")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    fun TestApplicationEngine.getOrderTest() {
        handleRequest(HttpMethod.Get, "/order/customer/${customer.id}").apply {
            assertEquals(Gson().toJson(emptyCart), response.content)
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun testPostAndGetOrder() {

        withTestApplication({ module(testing = true) }) {

            postOrderTest()
            getOrderTest()
        }
    }

    @Test
    fun testDeleteOrder() {

        withTestApplication({ module(testing = true) }) {

            postOrderTest()

            handleRequest(HttpMethod.Delete, "/order/0")

            handleRequest(HttpMethod.Get, "/order/0").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }

    @Test
    fun testDeleteAllOrders() {

        withTestApplication({ module(testing = true) }) {
            postOrderTest()

            handleRequest(HttpMethod.Delete, "/order")

            handleRequest(HttpMethod.Get, "/order").apply {
                assertEquals(Gson().toJson(emptyOrderList), response.content)
            }
        }
    }
}