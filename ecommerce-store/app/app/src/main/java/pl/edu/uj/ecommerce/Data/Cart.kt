package pl.edu.uj.ecommerce.Data

import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.where
import pl.edu.uj.ecommerce.Product
import pl.edu.uj.ecommerce.ProductRealm
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class CartItemRealm : RealmObject() {
    var customerId : String = ""
    var productId : Int = -1
    var quantity : Int = -1
}

class CartItem {
    var customerId : String = ""
    var productId : Int = -1
    var quantity : Int = -1
}

object Cart {
    var cartItems = mutableListOf<CartItemRealm>()

    init {
//        getCartItems()
//        totalPrice()
    }

    fun getCartItems() {
        cartItems = Realm.getDefaultInstance().where<CartItemRealm>().findAllAsync()
    }

    fun totalPrice() : Int {
        var price = 0
        for(p in cartItems) {
            val tmp = Realm.getDefaultInstance().where<ProductRealm>()
                .equalTo("id", p.productId).findFirstAsync() ?: return -1

            price += tmp.price * p.quantity
        }

        return price
    }

}

fun getCartIntoDB(customerId : String) {
    val service = RetrofitService.create()
    val call = service.getCartByIdCall(customerId)
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
                        it.insertOrUpdate(tmpItem)
                    }
                }

            }
        }

        override fun onFailure(call: Call<List<CartItem>>, t: Throwable) {
            //TODO onFailture get products
        }

    })
}
