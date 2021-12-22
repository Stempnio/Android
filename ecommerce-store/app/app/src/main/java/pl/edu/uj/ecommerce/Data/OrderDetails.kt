package pl.edu.uj.ecommerce.Data

import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class OrderDetailsRealm : RealmObject() {
    var orderId : Int = -1
    var productId : Int = -1
    var quantity : Int = -1
}

class OrderDetails {
    var orderId : Int = -1
    var productId : Int = -1
    var quantity : Int = -1
}


fun getOrderDetailsIntoDB(customerId : String) {
    val service = RetrofitService.create()
    val call = service.getCustomerOrderDetailsCall(customerId)
    call.enqueue(object : Callback<List<OrderDetails>> {
        override fun onResponse(
            call: Call<List<OrderDetails>>,
            response: Response<List<OrderDetails>>
        ) {
            if (response.code() == 200) {
                val orderResponse = response.body()!!

                for (item in orderResponse) {
                    val tmpItem = OrderDetailsRealm().apply {
                        this.orderId = item.orderId
                        this.productId = item.productId
                        this.quantity = item.quantity
                    }

                    Realm.getDefaultInstance().executeTransactionAsync {
                        it.insertOrUpdate(tmpItem)
                    }
                }

                Log.d("GET_ORDERS_DETAILS_FROM_DB", "Order details get successful")
            }

        }

        override fun onFailure(call: Call<List<OrderDetails>>, t: Throwable) {
            Log.d("GET_ORDERS_DETAILS_FROM_DB", t.message.toString())
        }

    })
}