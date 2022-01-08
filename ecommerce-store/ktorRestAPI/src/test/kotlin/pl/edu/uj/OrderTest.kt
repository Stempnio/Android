package pl.edu.uj

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.http.*
import io.ktor.server.testing.*
import pl.edu.uj.models.Order
import kotlin.test.Test
import kotlin.test.assertEquals

fun TestApplicationEngine.postOrderTest() {
    with(handleRequest(HttpMethod.Post, "/order/${customer.id}")) {
        assertEquals(HttpStatusCode.OK, response.status())

    }
}

fun TestApplicationEngine.getOrderTest() {
    handleRequest(HttpMethod.Get, "/order/customer/${customer.id}").apply {
        assertEquals(HttpStatusCode.OK, response.status())

        val gson = Gson()
        val itemType = object : TypeToken<List<Order>>() {}.type
        val orderList = gson.fromJson<List<Order>>(response.content, itemType)

        if(orderList != null && orderList.isNotEmpty()) {
            assertEquals(order.id, orderList[0].id)
            assertEquals(order.customerId, orderList[0].customerId)
        }
    }
}

class OrderTest {

    @Test
    fun testPostAndGetOrder() {
        withTestApplication({ module(testing = true) }) {
            postCustomerTest()
            postProductTest()
            postCartItemTest()

            postOrderTest()
            getOrderTest()
        }
    }

    @Test
    fun testDeleteOrder() {

        withTestApplication({ module(testing = true) }) {

            postCustomerTest()
            postProductTest()
            postCartItemTest()

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
            postCustomerTest()
            postProductTest()
            postCartItemTest()

            postOrderTest()

            handleRequest(HttpMethod.Delete, "/order")

            handleRequest(HttpMethod.Get, "/order").apply {
                assertEquals(Gson().toJson(emptyOrderList), response.content)
            }
        }
    }
}