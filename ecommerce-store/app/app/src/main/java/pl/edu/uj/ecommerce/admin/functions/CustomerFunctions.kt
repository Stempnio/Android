package pl.edu.uj.ecommerce.admin.functions

import android.util.Log
import pl.edu.uj.ecommerce.Data.Customer
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun deleteCustomerById(id : String) {
    val service = RetrofitService.create()
    val call = service.deleteCustomerCall(id)
    call.enqueue(object : Callback<Customer> {
        override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
            if(response.isSuccessful)
                Log.d("DELETE_CUSTOMER_BY_ID", "success")
        }

        override fun onFailure(call: Call<Customer>, t: Throwable) {
            Log.d("DELETE_CUSTOMER_BY_ID", t.message.toString())
        }

    })
}

fun deleteAllCustomers() {
    val service = RetrofitService.create()
    val call = service.deleteAllCustomersCall()
    call.enqueue(object : Callback<List<Customer>> {
        override fun onResponse(call: Call<List<Customer>>, response: Response<List<Customer>>) {
            if(response.isSuccessful)
                Log.d("DELETE_ALL_CUSTOMERS", "success")
        }

        override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
            Log.d("DELETE_ALL_CUSTOMERS", t.message.toString())
        }

    })
}

