package pl.edu.uj.ecommerce.Data

import android.util.Log
import android.widget.Toast
import io.realm.Realm
import io.realm.Realm.getApplicationContext
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var CURRENT_CUSTOMER_ID = ""
const val DEFAULT_CUSTOMER_ID = "default"

open class CustomerRealm : RealmObject() {

    @PrimaryKey
    var id : String = ""
    var firstName : String = ""
    var lastName : String = ""
    var email : String = ""
    var password : String = ""
}

class Customer {
    var id : String = ""
    var firstName : String = ""
    var lastName : String = ""
    var email : String = ""
    var password : String = ""
}

fun putCustomerIntoRealm(customer: Customer) {
    Realm.getDefaultInstance().executeTransactionAsync {
        it.insertOrUpdate(CustomerRealm().apply {
            this.id = customer.id
            this.firstName = customer.firstName
            this.lastName = customer.lastName
            this.email = customer.email
            this.password = customer.password
        })
    }
}

fun getCustomerByIdIntoRealm(id : String) {
    val service = RetrofitService.create()
    val call = service.getCustomerByIdCall(id)
    call.enqueue(object : Callback<Customer> {
        override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
            if (response.isSuccessful && response.body() != null) {
                val customerResponse = response.body()!!

                putCustomerIntoRealm(customerResponse)
                Log.d("GET CUSTOMER INTO REALM SUCCESS", response.message())
            } else {
                Log.e("GET CUSTOMER INTO REALM FAILED", response.message())
            }
        }

        override fun onFailure(call: Call<Customer>, t: Throwable) {
            Log.d("GET CUSTOMER INTO REALM FAILED", t.message.toString())
        }

    })
}

fun postCustomer(customer: Customer) {
    val service = RetrofitService.create()
    val call = service.postCustomerCall(customer)
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if(response.isSuccessful) {
                Toast.makeText(getApplicationContext(), "Successfully registered!", Toast.LENGTH_SHORT).show()
                Log.d("POST CUSTOMER SUCCESS", response.message())
            } else {
                Toast.makeText(getApplicationContext(), "Error occurred while registration! Given username may be already taken", Toast.LENGTH_SHORT).show()
                Log.d("POST CUSTOMER FAIL", response.message())
            }

        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Toast.makeText(getApplicationContext(), "Error occurred while registration! ${t.message.toString()}", Toast.LENGTH_SHORT).show()
            Log.d("POST CUSTOMER FAIL", t.message.toString())
        }

    })
}

fun updateCustomer(customer : Customer) {
    val service = RetrofitService.create()
    val call = service.putCustomerCall(customer)
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if(response.isSuccessful) {
                Toast.makeText(getApplicationContext(), "Successfully changed password!", Toast.LENGTH_SHORT).show()
                Log.d("PUT CUSTOMER SUCCESS", response.message())
            } else {
                Toast.makeText(getApplicationContext(), "Error occurred while updating password!", Toast.LENGTH_SHORT).show()
                Log.d("PUT CUSTOMER FAIL", response.message())
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Toast.makeText(getApplicationContext(), "Error occurred while updating password!", Toast.LENGTH_SHORT).show()
            Log.d("PUT CUSTOMER FAIL", t.message.toString())
        }

    })
}

fun deleteCustomer() {
    val service = RetrofitService.create()
    val call = service.deleteCustomerCall(CURRENT_CUSTOMER_ID)
    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if(response.isSuccessful) {
                deleteCurrentCustomerFromRealm()
                CURRENT_CUSTOMER_ID = DEFAULT_CUSTOMER_ID
                Toast.makeText(getApplicationContext(), "Successfully deleted account!", Toast.LENGTH_SHORT).show()
                Log.d("DELETE CUSTOMER SUCCESS", response.message())
            } else {
                Toast.makeText(getApplicationContext(), "Error occurred while deleting account!", Toast.LENGTH_SHORT).show()
                Log.d("DELETE CUSTOMER FAIL", response.message())
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Toast.makeText(getApplicationContext(), "Error occurred while deleting account!", Toast.LENGTH_SHORT).show()
            Log.d("DELETE CUSTOMER FAIL", t.message.toString())
        }

    })
}

fun getCurrentCustomer() : Customer {
    val custRealm =  Realm.getDefaultInstance().where(CustomerRealm::class.java)
        .equalTo("id", CURRENT_CUSTOMER_ID)
        .findFirst()

    if(custRealm != null) {
        return Customer().apply {
            this.id = custRealm.id
            this.password = custRealm.password
            this.firstName = custRealm.firstName
            this.lastName = custRealm.lastName
            this.email = custRealm.email
        }
    } else {
        // if error occurs (current customer cant be found) return empty customer
        return Customer()
    }
}

fun deleteCurrentCustomerFromRealm() {
    Realm.getDefaultInstance().beginTransaction()
    Realm.getDefaultInstance().where(CustomerRealm::class.java)
        .equalTo("id", CURRENT_CUSTOMER_ID)
        .findAll().deleteAllFromRealm()
    Realm.getDefaultInstance().commitTransaction()
}


