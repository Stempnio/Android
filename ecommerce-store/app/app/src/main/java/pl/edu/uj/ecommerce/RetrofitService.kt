package pl.edu.uj.ecommerce

import pl.edu.uj.ecommerce.Data.CartItem
import pl.edu.uj.ecommerce.Data.Customer
import pl.edu.uj.ecommerce.Data.Order
import pl.edu.uj.ecommerce.Data.OrderDetails
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

//    Products
    @GET("product")
    fun getProductsCall() : Call<List<Product>>

//    Customer
    @GET("customer/{id}")
    fun getCustomerByIdCall(@Path("id") id : String) : Call<Customer>

    @POST("customer")
    fun postCustomerCall(@Body customer : Customer) : Call<Customer>

    @DELETE("customer/{customerId}")
    fun deleteCustomerCall(@Path("customerId") customerId: String) : Call<Customer>

//    Cart
    @GET("cart/{customerId}")
    fun getCartByIdCall(@Path("customerId") customerId : String) : Call<List<CartItem>>

    @POST("cart/{customerId}/{productId}")
    fun postCartItemCall(@Path("customerId") customerId: String, @Path("productId") productId : Int) : Call<CartItem>

    @DELETE("cart/{customerId}/{productId}")
    fun deleteCartItemCall(@Path("customerId") customerId: String, @Path("productId") productId: Int) : Call<CartItem>

    @DELETE("cart/{customerId}")
    fun deleteCartCall(@Path("customerId") customerId: String) : Call<List<CartItem>>

//    Orders
    @GET("order/customer/{customerId}")
    fun getCustomerOrdersCall(@Path("customerId") customerId: String) : Call<List<Order>>

    @POST("order/{customerId}")
    fun postCustomerOrderCall(@Path("customerId") customerId: String) : Call<Order>

//    Order details
    @GET("oderDetails/{orderId}")
    fun getOrderDetailsByIdCall(@Path("orderId") orderId : Int) : Call<List<OrderDetails>>

    @GET("orderDetails/customer/{customerId}")
    fun getCustomerOrderDetailsCall(@Path("customerId") customerId: String) : Call<List<OrderDetails>>


    companion object {

//        var BASE_URL = "https://1bae-185-25-121-195.ngrok.io/"
        var BASE_URL = "http://10.0.2.2:8080/"

        fun create() : RetrofitService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(RetrofitService::class.java)

        }
    }
}