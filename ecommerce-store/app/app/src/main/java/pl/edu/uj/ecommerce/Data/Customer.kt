package pl.edu.uj.ecommerce.Data

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    val call = service.geCustomerByIdCall(id)
    call.enqueue(object : Callback<List<Customer>> {
        override fun onResponse(
            call: Call<List<Customer>>,
            response: Response<List<Customer>>
        ) {
            if (response.code() == 200) {
                val customerResponse = response.body()!!

                for(cust in customerResponse) {
                    val tmpCustomer = CustomerRealm().apply {
                        this.id = cust.id
                        this.firstName = cust.firstName
                        this.lastName = cust.lastName
                        this.email = cust.email
                    }

                    Realm.getDefaultInstance().executeTransactionAsync {
                        it.insertOrUpdate(tmpCustomer)
                    }
                }
            }
        }

        override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
            //TODO onFailture get products
        }

    })
}