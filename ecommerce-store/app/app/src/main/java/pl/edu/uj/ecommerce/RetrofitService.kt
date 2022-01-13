package pl.edu.uj.ecommerce

import pl.edu.uj.ecommerce.Data.*
import pl.edu.uj.ecommerce.admin.Admin
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

//    Products
    @GET("product")
    fun getProductsCall() : Call<List<Product>>

    @GET("product/{id}")
    fun getProductByIdCall(@Path("id") id : Int) : Call<Product>

    @POST("product")
    fun postProductCall(@Body product: Product) : Call<Unit>

    @PUT("product")
    fun updateProductCall(@Body product: Product) : Call<Unit>

    @DELETE("product/{id}")
    fun deleteProductByIdCall(@Path("id") productId: Int) : Call<Unit>

    @DELETE("product")
    fun deleteAllProductsCall() : Call<Unit>

//    Customer
    @GET("customer")
    fun getAllCustomersCall() : Call<List<Customer>>

    @GET("customer/{id}")
    fun getCustomerByIdCall(@Path("id") id : String) : Call<Customer>

    @POST("customer")
    fun postCustomerCall(@Body customer : Customer) : Call<Unit>

    @PUT("customer")
    fun putCustomerCall(@Body customer : Customer) : Call<Unit>

    @DELETE("customer/{customerId}")
    fun deleteCustomerCall(@Path("customerId") customerId: String) : Call<Unit>

    @DELETE("customer")
    fun deleteAllCustomersCall() : Call<Unit>

//    Cart
    @GET("cart")
    fun getAllCartItemsCall() : Call<List<CartItem>>

    @GET("cart/{customerId}")
    fun getCartByIdCall(@Path("customerId") customerId : String) : Call<List<CartItem>>

    @POST("cart/{customerId}/{productId}")
    fun postCartItemCall(@Path("customerId") customerId: String, @Path("productId") productId : Int) : Call<Unit>

    @DELETE("cart/{customerId}/{productId}")
    fun deleteCartItemCall(@Path("customerId") customerId: String, @Path("productId") productId: Int) : Call<Unit>

    @DELETE("cart/{customerId}")
    fun deleteCustomerCartCall(@Path("customerId") customerId: String) : Call<Unit>

    @DELETE("cart")
    fun deleteAllCarts() : Call<Unit>

//    Orders
    @GET("order")
    fun getAllOrdersCall() : Call<List<Order>>

    @GET("order/customer/{customerId}")
    fun getCustomerOrdersCall(@Path("customerId") customerId: String) : Call<List<Order>>

    @POST("order/{customerId}")
    fun postCustomerOrderCall(@Path("customerId") customerId: String) : Call<Unit>

    @DELETE("order/{id}")
    fun deleteOrderByIdCall(@Path("id") orderId: Int) : Call<Unit>

    @DELETE("order")
    fun deleteAllOrders() : Call<Unit>

//    Order details
    @GET("orderDetails/{orderId}")
    fun getOrderDetailsByIdCall(@Path("orderId") orderId : Int) : Call<List<OrderDetails>>

    @GET("orderDetails/customer/{customerId}")
    fun getCustomerOrderDetailsCall(@Path("customerId") customerId: String) : Call<List<OrderDetails>>

//    Admin
    @GET("admin/{id}")
    fun getAdminByIdCall(@Path("id") id : String) : Call<Admin>

    @POST("admin")
    fun postAdminCall(@Body admin: Admin) : Call<Unit>

    @DELETE("admin/{id}")
    fun deleteAdminCall(@Path("id") id : String) : Call<Unit>


    companion object {

//        var BASE_URL = "https://74ce-185-25-121-195.ngrok.io/"
        var BASE_URL = "http://10.0.2.2:8080/"

        fun create() : RetrofitService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(RetrofitService::class.java)

        }

        fun createTest() : RetrofitService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://127.0.0.1:8080/")
                .build()

            return retrofit.create(RetrofitService::class.java)
        }
    }
}