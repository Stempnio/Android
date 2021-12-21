package pl.edu.uj.ecommerce

import pl.edu.uj.ecommerce.Data.CartItem
import pl.edu.uj.ecommerce.Data.Customer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

    @GET("product")
    fun getProductsCall() : Call<List<Product>>

    @GET("customer/{id}")
    fun getCustomerByIdCall(@Path("id") id : String) : Call<Customer>

    //TODO customer registration
    @POST("customer")
    fun postCustomerCall(@Body customer : Customer) : Call<Customer>

    @GET("cart/{customerId}")
    fun getCartByIdCall(@Path("customerId") customerId : String) : Call<List<CartItem>>

    @POST("cart/{customerId}/{productId}")
    fun postCartItemCall(@Path("customerId") customerId: String, @Path("productId") productId : Int) : Call<CartItem>

    @DELETE("cart/{customerId}/{productId}")
    fun deleteCartItemCall(@Path("customerId") customerId: String, @Path("productId") productId: Int) : Call<CartItem>

    //TODO order
    //TODO orderdetails

    companion object {

        var BASE_URL = "https://7fe9-185-25-121-195.ngrok.io/"

        fun create() : RetrofitService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(RetrofitService::class.java)

        }
    }
}