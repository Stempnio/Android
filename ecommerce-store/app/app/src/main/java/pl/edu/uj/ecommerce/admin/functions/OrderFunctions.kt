package pl.edu.uj.ecommerce.admin.functions

import android.util.Log
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun deleteOrderById(id : Int) {
    val service = RetrofitService.create()
    val call = service.deleteOrderByIdCall(id)
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            Log.d("DELETE_ORDER_BY_ID", response.message().toString())
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("DELETE_ORDER_BY_ID", t.message.toString())
        }

    })
}

fun deleteAllOrders() {
    val service = RetrofitService.create()
    val call = service.deleteAllOrders()
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            Log.d("DELETE_ALL_ORDERS", response.message().toString())
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("DELETE_ALL_ORDERS", t.message.toString())
        }

    })
}