package pl.edu.uj.ecommerce

import pl.edu.uj.ecommerce.Data.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

//    Products
    @GET("product")
    fun getProductsCall() : Call<List<Product>>

    @GET("product/{id}")
    fun getProductByIdCall(@Path("id") id : String) : Call<Product>

    @POST("product")
    fun postProductCall(@Body product: Product) : Call<Product>

    @PUT("product")
    fun updateProductCall(@Body product: Product) : Call<Product>

    @DELETE("product/{id}")
    fun deleteProductByIdCall(@Body productId: Int) : Call<Product>

    @DELETE("product")
    fun deleteAllProductsCall()

//    Customer
    @GET("customer")
    fun getAllCustomersCall() : Call<List<Customer>>

    @GET("customer/{id}")
    fun getCustomerByIdCall(@Path("id") id : String) : Call<Customer>

    @POST("customer")
    fun postCustomerCall(@Body customer : Customer) : Call<Customer>

    @PUT("customer")
    fun putCustomerCall(@Body customer : Customer) : Call<Customer>

    @DELETE("customer/{customerId}")
    fun deleteCustomerCall(@Path("customerId") customerId: String) : Call<Customer>

    @DELETE("customer")
    fun deleteAllCustomersCall() : Call<List<Customer>>

//    Cart
    @GET("cart")
    fun getAllCartItemsCall() : Call<List<CartItem>>

    @GET("cart/{customerId}")
    fun getCartByIdCall(@Path("customerId") customerId : String) : Call<List<CartItem>>

    @POST("cart/{customerId}/{productId}")
    fun postCartItemCall(@Path("customerId") customerId: String, @Path("productId") productId : Int) : Call<CartItem>

    @DELETE("cart/{customerId}/{productId}")
    fun deleteCartItemCall(@Path("customerId") customerId: String, @Path("productId") productId: Int) : Call<CartItem>

    @DELETE("cart/{customerId}")
    fun deleteCustomerCartCall(@Path("customerId") customerId: String) : Call<List<CartItem>>

    @DELETE("cart")
    fun deleteAllCarts()

//    Orders
    @GET("order")
    fun getAllOrdersCall() : Call<List<Order>>

    @GET("order/customer/{customerId}")
    fun getCustomerOrdersCall(@Path("customerId") customerId: String) : Call<List<Order>>

    @POST("order/{customerId}")
    fun postCustomerOrderCall(@Path("customerId") customerId: String) : Call<Order>

    @DELETE("order/{id}")
    fun deleteOrderByIdCall(@Path("id") orderId: Int) : Call<Order>

//    Order details
    @GET("orderDetails")
    fun getAllOrderDetailsCall() : Call<List<OrderDetails>>

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