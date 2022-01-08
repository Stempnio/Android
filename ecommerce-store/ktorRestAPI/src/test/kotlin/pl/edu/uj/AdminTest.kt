package pl.edu.uj

import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import pl.edu.uj.models.Admin
import kotlin.test.assertEquals

class AdminTest {

    private val admin = Admin("adminTest", "adminTest@gmail.com", "1234")

    @Test
    fun testDeleteAdmin() {

        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Delete, "/admin/${admin.id}").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testPostAdmin() {
        withTestApplication({ module(testing = true) }) {
            with(handleRequest(HttpMethod.Post, "/admin"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(admin))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testPostAndGetAdmin() {

        withTestApplication({ module(testing = true) }) {

            with(handleRequest(HttpMethod.Post, "/admin"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(admin))
            }) {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/admin/${admin.id}").apply {
                assertEquals(Gson().toJson(admin),
                    response.content
                )
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(Gson().toJson(admin), response.content.toString())
            }
        }
    }
}