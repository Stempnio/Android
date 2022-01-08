package pl.edu.uj

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Before
import pl.edu.uj.models.Cart
import kotlin.test.Test
import kotlin.test.assertEquals

class CartTest {

    val customerTest = CustomerTest()
    val customer = customerTest.customer

    val productTest = ProductTest()
    val product = productTest.product1

    val cart = mutableListOf(Cart(customer.id, product.id, 1))
    val emptyCart = mutableListOf<Cart>()

    @Before
    fun setup() {
        customerTest.testPostCustomer()
        productTest.testPostAndGetProduct()
    }

    @Test
    fun testPostAndGetCartItem() {
        withTestApplication({ module(testing = true) }) {
            with(handleRequest(HttpMethod.Post, "/cart/${customer.id}/${product.id}")) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/cart/${customer.id}").apply {
                assertEquals(Gson().toJson(cart), response.content)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testDeleteCartItem() {
        withTestApplication({ module(testing = true) }) {

            testPostAndGetCartItem()

            handleRequest(HttpMethod.Delete, "/cart/${customer.id}/${product.id}")

            handleRequest(HttpMethod.Get, "/cart/${customer.id}").apply {
                assertEquals(Gson().toJson(emptyCart), response.content)
            }
        }
    }

    @Test
    fun testDeleteAllCarts() {
        withTestApplication({ module(testing = true) }) {

            testPostAndGetCartItem()

            handleRequest(HttpMethod.Delete, "/cart")

            handleRequest(HttpMethod.Get, "/cart").apply {
                assertEquals(Gson().toJson(emptyCart), response.content)
            }
        }
    }
}