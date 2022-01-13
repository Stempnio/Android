package pl.edu.uj.ecommerce.admin.functions

import android.util.Log
import pl.edu.uj.ecommerce.Data.Product
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun postProduct(product: Product) {
    val service = RetrofitService.create()
    val call = service.postProductCall(product)
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            Log.d("POST_PRODUCT", "Product post successful")
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("POST_PRODUCT", t.message.toString())
        }

    })
}

fun deleteProductById(id: Int) {
    val service = RetrofitService.create()
    val call = service.deleteProductByIdCall(id)
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            Log.d("DELETE_PRODUCT", "success")
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("DELETE_PRODUCT", t.message.toString())
        }

    })
}

fun updateProduct(product: Product) {
    val service = RetrofitService.create()
    val call = service.updateProductCall(product)
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            Log.d("UPDATE_PRODUCT", "success")
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("UPDATE_PRODUCT", t.message.toString())
        }

    })
}

fun deleteAllProducts() {
    val service = RetrofitService.create()
    val call = service.deleteAllProductsCall()
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            Log.d("DELETE_PRODUCTS_ALL", "success")
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Log.d("DELETE_PRODUCTS_ALL", t.message.toString())
        }

    })
}
