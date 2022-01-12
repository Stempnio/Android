package pl.edu.uj.ecommerce.Data

import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.where
import pl.edu.uj.ecommerce.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ProductRealm : RealmObject() {
    var name : String = ""
    var price : Int = 0
    var description : String = ""

    @PrimaryKey
    var id : Int = -1
}

class Product {
    var name : String = ""
    var price : Int = -1
    var description : String = ""
    var id : Int = 0
}

fun mapProduct(productRealm: ProductRealm) : Product {
    return Product().apply {
        this.name = productRealm.name
        this.price = productRealm.price
        this.description = productRealm.description
        this.id = productRealm.id
    }
}

fun getProductsFromRealmIntoList() : List<Product> {
    return Realm.getDefaultInstance()
        .where<ProductRealm>()
        .findAll()
        .map { mapProduct(it) }
}

fun productDetails(id : Int) : String {
    val product = Realm.getDefaultInstance().where(ProductRealm::class.java)
        .equalTo("id", id)
        .findFirst()

    if(product == null)
        return "product with $id ID not found"

    return "Product: " + product.name + ", price: " + product.price
}

fun deleteAllProductsFromRealm() {
    Realm.getDefaultInstance().beginTransaction()
    Realm.getDefaultInstance().where(ProductRealm::class.java).findAll().deleteAllFromRealm()
    Realm.getDefaultInstance().commitTransaction()
}


fun getProductsIntoRealm() {
    val service = RetrofitService.create()
    val call = service.getProductsCall()
    call.enqueue(object : Callback<List<Product>> {
        override fun onResponse(
            call: Call<List<Product>>,
            response: Response<List<Product>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                val productResponse = response.body()!!

                for(prod in productResponse) {
                    Realm.getDefaultInstance().executeTransactionAsync {
                        it.insertOrUpdate(ProductRealm().apply {
                            this.id = prod.id
                            this.description = prod.description
                            this.name = prod.name
                            this.price = prod.price
                        })
                    }
                }

                Log.d("GET_PRODUCTS_FROM_DB", "Products get successful")
            } else {
                Log.d("GET_PRODUCTS_FROM_DB", "Products get fail")
            }
        }

        override fun onFailure(call: Call<List<Product>>, t: Throwable) {
            Log.d("GET_PRODUCTS_FROM_DB", t.message.toString())
        }

    })
}

fun refreshProducts() {
    deleteAllProductsFromRealm()
    getProductsIntoRealm()
}





