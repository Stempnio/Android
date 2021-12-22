package pl.edu.uj.ecommerce.Data
import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.util.*

open class OrderRealm : RealmObject() {
    var customerId : String = ""
    //TODO date format
//    var date : Date = Date()
    var date : String = ""
    @PrimaryKey
    var id : Int = -1

}

class Order {
    var customerId : String = ""
//    var date : Date = Date()
    var date : String = ""
    var id : Int = -1
}


fun getOrdersIntoDB(customerId : String) {
    val service = RetrofitService.create()
    val call = service.getCustomerOrdersCall(customerId)
    call.enqueue(object : Callback<List<Order>> {
        override fun onResponse(
            call: Call<List<Order>>,
            response: Response<List<Order>>
        ) {
            if (response.code() == 200) {
                val orderResponse = response.body()!!

                for (item in orderResponse) {
                    val tmpItem = OrderRealm().apply {
                        this.customerId = item.customerId
                        this.date = item.date.toString()
                        this.id = item.id
                    }

                    Realm.getDefaultInstance().executeTransactionAsync {
                        it.insertOrUpdate(tmpItem)
                    }
                }

                Log.d("GET_ORDERS_FROM_DB", "Orders get successful")
            }

        }

        override fun onFailure(call: Call<List<Order>>, t: Throwable) {
            Log.d("GET_ORDERS_FROM_DB", t.message.toString())
        }

    })
}