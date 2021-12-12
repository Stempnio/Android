package pl.edu.uj.ecommerce

import pl.edu.uj.ecommerce.Data.Cart
import pl.edu.uj.ecommerce.Data.CartItem
import pl.edu.uj.ecommerce.Data.Customer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("product")
    fun getProductsCall() : Call<List<Product>>

    @GET("customer/{id}")
    fun geCustomerByIdCall(@Path("id") id : String) : Call<List<Customer>>

    @GET("cart/{customerId}")
    fun getCartByIdCall(@Path("customerId") customerId : String) : Call<List<CartItem>>

    //TODO order
    //TODO orderdetails

    companion object {

        var BASE_URL = "http://ad2b-185-25-121-220.ngrok.io/"

        fun create() : RetrofitService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(RetrofitService::class.java)

        }
    }
}