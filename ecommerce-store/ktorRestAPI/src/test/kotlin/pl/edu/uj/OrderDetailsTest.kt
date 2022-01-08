package pl.edu.uj

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class OrderDetailsTest {

    @Test
    fun testGetOrderDetails() {
        withTestApplication({ module(testing = true) }) {
            postCustomerTest()
            postProductTest()
            postCartItemTest()
            postOrderTest()

            handleRequest(HttpMethod.Get, "/orderDetails/1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(Gson().toJson(orderDetails1), response.content)
            }
        }
    }

    @Test
    fun testGetCustomerOrderDetails() {
        withTestApplication({ module(testing = true) }) {
            postCustomerTest()
            postProductTest()
            postCartItemTest()
            postOrderTest()

            handleRequest(HttpMethod.Get, "/orderDetails/customer/${customer.id}").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(Gson().toJson(orderDetails1), response.content)
            }
        }
    }

    @Test
    fun testGetAllOrderDetails() {
        withTestApplication({ module(testing = true) }) {
            postCustomerTest()
            postProductTest()
            postCartItemTest()
            postOrderTest()

            handleRequest(HttpMethod.Get, "/orderDetails").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(Gson().toJson(orderDetails1), response.content)
            }
        }
    }
}