package pl.edu.uj

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import pl.edu.uj.models.Product
import kotlin.test.assertEquals

class ProductTest {

    private val product1 = Product("productTest1",
        100,
        "productTest1",
        1)

    private val product1Updated = Product("productTestUpdated",
        1000,
        "productTest1",
        1)

    @Test
    fun testPostAndGetProduct() {
        withTestApplication({ module(testing = true) }) {

            with(handleRequest(HttpMethod.Post, "/product"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(product1))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/product/${product1.id}").apply {
                assertEquals(Gson().toJson(product1), response.content)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testUpdateAndGetProduct() {
        withTestApplication({ module(testing = true) }) {

            with(handleRequest(HttpMethod.Post, "/product"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(product1))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

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

            with(handleRequest(HttpMethod.Post, "/product"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(product1))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Delete, "/product/${product1.id}")

            handleRequest(HttpMethod.Get, "/product/${product1.id}").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }
}