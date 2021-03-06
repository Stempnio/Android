package pl.edu.uj

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

fun TestApplicationEngine.postCartItemTest() {
    with(handleRequest(HttpMethod.Post, "/cart/${customer.id}/${product1.id}")) {
        assertEquals(HttpStatusCode.OK, response.status())
    }
}

fun TestApplicationEngine.getCustomerCartTest() {
    handleRequest(HttpMethod.Get, "/cart/${customer.id}").apply {
        assertEquals(Gson().toJson(cart), response.content)
        assertEquals(HttpStatusCode.OK, response.status())
    }
}

class CartTest {

    @Test
    fun testPostAndGetCartItem() {
        withTestApplication({ module(testing = true) }) {
            postCustomerTest()
            postProductTest()

            postCartItemTest()
            getCustomerCartTest()
        }
    }

    @Test
    fun testPostAndGetCartItem2Quantity() {
        withTestApplication({ module(testing = true) }) {
            postCustomerTest()
            postProductTest()

            postCartItemTest()
            postCartItemTest()

            handleRequest(HttpMethod.Get, "/cart/${customer.id}").apply {
                assertEquals(Gson().toJson(cart2quantity), response.content)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testPostCartItemMalformedProductId() {
        withTestApplication({ module(testing = true) }) {
            with(handleRequest(HttpMethod.Post, "/cart/${customer.id}/adsf")) {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun testGetAllCartsEmpty() {
        withTestApplication({ module(testing = true) }) {
            with(handleRequest(HttpMethod.Post, "/cart")) {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }


    @Test
    fun testDeleteCartItem() {
        withTestApplication({ module(testing = true) }) {

            postCustomerTest()
            postProductTest()

            postCartItemTest()

            handleRequest(HttpMethod.Delete, "/cart/${customer.id}/${product1.id}")

            handleRequest(HttpMethod.Get, "/cart/${customer.id}").apply {
                assertEquals(Gson().toJson(emptyCart), response.content)
            }
        }
    }

    @Test
    fun testDeleteCustomerCart() {
        withTestApplication({ module(testing = true) }) {

            postCustomerTest()
            postProductTest()

            postCartItemTest()

            handleRequest(HttpMethod.Delete, "/cart/${customer.id}")

            handleRequest(HttpMethod.Get, "/cart/${customer.id}").apply {
                assertEquals(Gson().toJson(emptyCart), response.content)
            }
        }
    }

    @Test
    fun testDeleteAllCarts() {
        withTestApplication({ module(testing = true) }) {

            postCustomerTest()
            postProductTest()

            postCartItemTest()

            handleRequest(HttpMethod.Delete, "/cart")

            handleRequest(HttpMethod.Get, "/cart").apply {
                assertEquals(Gson().toJson(emptyCart), response.content)
            }
        }
    }
}