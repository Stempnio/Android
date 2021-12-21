package pl.edu.uj.ecommerce.Data

import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var CURRENT_CUSTOMER_ID = ""
val DEFAULT_CUSTOMER_ID = "default"

open class CustomerRealm : RealmObject() {

    @PrimaryKey
    var id : String = ""
    var firstName : String = ""
    var lastName : String = ""
    var email : String = ""
}

class Customer {
    var id : String = ""
    var firstName : String = ""
    var lastName : String = ""
    var email : String = ""
}

fun getCustomerByIdIntoDB(id : String) {
    val service = RetrofitService.create()
    val call = service.getCustomerByIdCall(id)
    call.enqueue(object : Callback<Customer> {
        override fun onResponse(
            call: Call<Customer>,
            response: Response<Customer>
        ) {
            if (response.code() == 200) {
                val customerResponse = response.body()!!

                val tmpCustomer = CustomerRealm().apply {
                    this.id = customerResponse.id
                    this.firstName = customerResponse.firstName
                    this.lastName = customerResponse.lastName
                    this.email = customerResponse.email
                }

                Realm.getDefaultInstance().executeTransactionAsync {
                    it.insertOrUpdate(tmpCustomer)
                }

            }
        }

        override fun onFailure(call: Call<Customer>, t: Throwable) {
            Log.d("GET CUSTOMER INTO DB FAILED", t.message.toString())
        }

    })
}
