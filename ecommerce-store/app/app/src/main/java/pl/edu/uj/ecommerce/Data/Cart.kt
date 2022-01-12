package pl.edu.uj.ecommerce.Data

import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class CartItemRealm : RealmObject() {
    var customerId : String = ""
    @PrimaryKey
    var productId : Int = -1
    var quantity : Int = -1
}

class CartItem {
    var customerId : String = ""
    var productId : Int = -1
    var quantity : Int = -1
}

fun isCartEmpty() : Boolean {

    val count = Realm.getDefaultInstance().where(CartItemRealm::class.java)
        .equalTo("customerId", CURRENT_CUSTOMER_ID)
        .count()

    return count < 1
}

fun cartToString() : String {
        var result = ""

        Realm.getDefaultInstance()
            .where(CartItemRealm::class.java)
            .equalTo("customerId", CURRENT_CUSTOMER_ID)
            .findAll().forEach {
                result += "Product: " + Products.productDetails(it.productId) + ", quantity: " + it.quantity + "\n\n"
            }

        return result
}

fun carTotalPrice() : Int {
    var result = 0

    Realm.getDefaultInstance()
        .where(CartItemRealm::class.java)
        .equalTo("customerId", CURRENT_CUSTOMER_ID)
        .findAll().forEach {
            result += ((Realm.getDefaultInstance().where(ProductRealm::class.java)
                .equalTo("id", it.productId)
                .findFirst()?.price ?: 0) * it.quantity)
        }

    return result
}

fun removeCartItem(productId: Int) {
        val service = RetrofitService.create()
        val call = service.deleteCartItemCall(CURRENT_CUSTOMER_ID, productId)
        call.enqueue(object : Callback<CartItem> {
            override fun onResponse(call: Call<CartItem>, response: Response<CartItem>) {
                if(response.isSuccessful)
                    Log.d("ITEM DELETE SUCCESS", response.message())
            }

            override fun onFailure(call: Call<CartItem>, t: Throwable) {
                Log.d("ITEM DELETE FAIL", t.message.toString())
            }

        })

        Realm.getDefaultInstance().beginTransaction()

        Realm.getDefaultInstance().where(CartItemRealm::class.java)
            .equalTo("customerId", CURRENT_CUSTOMER_ID)
            .equalTo("productId", productId)
            .findAll().forEach {
                it.quantity -=1
            }

        Realm.getDefaultInstance().where(CartItemRealm::class.java)
            .equalTo("customerId", CURRENT_CUSTOMER_ID)
            .lessThan("quantity", 1)
            .findAll()
            .deleteAllFromRealm()


        Realm.getDefaultInstance().commitTransaction()
    }

fun postCart(productId : Int) {
    val service = RetrofitService.create()
    val call = service.postCartItemCall(CURRENT_CUSTOMER_ID, productId)
    call.enqueue(object : Callback<CartItem> {
        override fun onResponse(call: Call<CartItem>, response: Response<CartItem>) {
            Log.d("CART ITEM POSTED SUCCESSFULLY", response.message().toString())
        }

        override fun onFailure(call: Call<CartItem>, t: Throwable) {
            Log.d("CART ITEM POST FAILED", t.message.toString())
        }

    })

    //getCartIntoDB

    // refresh cart so it is up to date
    refreshCart()
}

fun refreshCart() {
    Realm.getDefaultInstance().beginTransaction()
    Realm.getDefaultInstance().where(CartItemRealm::class.java)
        .equalTo("customerId", CURRENT_CUSTOMER_ID)
        .findAll().deleteAllFromRealm()
    Realm.getDefaultInstance().commitTransaction()
    getCartIntoDB()
}

fun getCartIntoDB() {
    val service = RetrofitService.create()
    val call = service.getCartByIdCall(CURRENT_CUSTOMER_ID)
    call.enqueue(object : Callback<List<CartItem>> {
        override fun onResponse(
            call: Call<List<CartItem>>,
            response: Response<List<CartItem>>
        ) {
            if (response.code() == 200) {
                val cartResponse = response.body()!!

                for(item in cartResponse) {
                    val tmpItem = CartItemRealm().apply {
                        this.customerId = item.customerId
                        this.productId = item.productId
                        this.quantity = item.quantity
                    }

                    Realm.getDefaultInstance().executeTransactionAsync {
                        it.copyToRealmOrUpdate(tmpItem)
                    }
                }

                Log.d("GET_CART_FROM_DB", "Cart get successful")
            }

        }

        override fun onFailure(call: Call<List<CartItem>>, t: Throwable) {
            Log.d("GET_CART_FROM_DB", t.message.toString())
        }

    })
}

