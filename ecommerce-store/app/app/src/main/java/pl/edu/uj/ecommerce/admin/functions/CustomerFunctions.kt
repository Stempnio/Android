package pl.edu.uj.ecommerce.admin.functions

import android.util.Log
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun deleteCustomerById(id : String) {
    val service = RetrofitService.create()
    val call = service.deleteCustomerCall(id)
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if(response.isSuccessful)
                Log.d("DELETE_CUSTOMER_BY_ID", "success")
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("DELETE_CUSTOMER_BY_ID", t.message.toString())
        }

    })
}

fun deleteAllCustomers() {
    val service = RetrofitService.create()
    val call = service.deleteAllCustomersCall()
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if(response.isSuccessful)
                Log.d("DELETE_ALL_CUSTOMERS", "success")
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("DELETE_ALL_CUSTOMERS", t.message.toString())
        }

    })
}

