package pl.edu.uj.ecommerce.admin.functions

import android.util.Log
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun deleteCustomerCart(customerId : String) {
    val service = RetrofitService.create()
    val call = service.deleteCustomerCartCall(customerId)
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if(response.isSuccessful)
                Log.d("DELETE_CUSTOMER_CART", "success")
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("DELETE_CUSTOMER_CART", t.message.toString())
        }

    })
}

fun deleteAllCarts() {
    val service = RetrofitService.create()
    val call = service.deleteAllCarts()
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            Log.d("DELETE_ALL_CARTS", "success")
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("DELETE_ALL_CARTS", t.message.toString())
        }

    })
}