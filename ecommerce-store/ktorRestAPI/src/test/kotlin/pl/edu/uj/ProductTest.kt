package pl.edu.uj

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

fun TestApplicationEngine.postProductTest() {
    with(handleRequest(HttpMethod.Post, "/product") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        setBody(Gson().toJson(product1))
    }) {
        assertEquals(HttpStatusCode.OK, response.status())
    }
}

fun TestApplicationEngine.getProductTest() {
    handleRequest(HttpMethod.Get, "/product/${product1.id}").apply {
        assertEquals(Gson().toJson(product1), response.content)
        assertEquals(HttpStatusCode.OK, response.status())
    }
}

class ProductTest {

    @Test
    fun testPostProduct() {
        withTestApplication({ module(testing = true) }) {
            postProductTest()
        }
    }

    @Test
    fun testPostCustomerInsteadOfProduct() {
        withTestApplication({ module(testing = true) }) {
            with(handleRequest(HttpMethod.Post, "/product") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(customer))
            }) {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun testPostAndGetProduct() {
        withTestApplication({ module(testing = true) }) {
            postProductTest()
            getProductTest()
        }
    }

    @Test
    fun testUpdateCustomerInsteadOfProduct() {
        withTestApplication({ module(testing = true) }) {
            with(handleRequest(HttpMethod.Put, "/product") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(customer))
            }) {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun testUpdateAndGetProduct() {
        withTestApplication({ module(testing = true) }) {

            postProductTest()

            with(handleRequest(HttpMethod.Put, "/product"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(product1Updated))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/product/${product1.id}").apply {
                assertEquals(Gson().toJson(product1Updated), response.content)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testDeleteProduct() {
        withTestApplication({ module(testing = true) }) {

            postProductTest()

            handleRequest(HttpMethod.Delete, "/product/${product1.id}")

            handleRequest(HttpMethod.Get, "/product/${product1.id}").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }

    @Test
    fun testDeleteAllProducts() {
        withTestApplication({ module(testing = true) }) {

            postProductTest()

            handleRequest(HttpMethod.Delete, "/product")

            handleRequest(HttpMethod.Get, "/product").apply {
                assertEquals(Gson().toJson(emptyProductList), response.content)
            }
        }
    }
}