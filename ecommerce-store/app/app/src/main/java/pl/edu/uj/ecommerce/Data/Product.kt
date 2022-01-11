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

object Products {
    var products = mutableListOf<ProductRealm>()

    init {
        getProductsFromDB()
    }

    fun productDetails(id : Int) : String {
        val product = Realm.getDefaultInstance().where(ProductRealm::class.java)
            .equalTo("id", id)
            .findFirst()

        if(product == null)
            return "product with $id ID not found"

        return "Product: " + product.name + ", price: " + product.price
    }

    fun getProductsFromDB() {
        products = Realm.getDefaultInstance().where<ProductRealm>().findAll()
    }

    fun getProductsFromDbIntoList() : List<Product> {
        return products.map { mapProduct(it) }
//        return Realm.getDefaultInstance().where<ProductRealm>().findAll().map { mapProduct(it) }
    }

}

fun deleteAllProductsFromDB() {
    Realm.getDefaultInstance().beginTransaction()
    Realm.getDefaultInstance().where(ProductRealm::class.java).findAll().deleteAllFromRealm()
    Realm.getDefaultInstance().commitTransaction()
}


fun getProductsIntoDB() {
    val service = RetrofitService.create()
    val call = service.getProductsCall()
    call.enqueue(object : Callback<List<Product>> {
        override fun onResponse(
            call: Call<List<Product>>,
            response: Response<List<Product>>
        ) {
            if (response.code() == 200) {
                val productResponse = response.body()!!

                for(prod in productResponse) {
                    val tmpProduct = ProductRealm().apply {
                        this.id = prod.id
                        this.description = prod.description
                        this.name = prod.name
                        this.price = prod.price
                    }

                    Realm.getDefaultInstance().executeTransactionAsync {
                        it.insertOrUpdate(tmpProduct)
                    }
                }

                Log.d("GET_PRODUCTS_FROM_DB", "Products get successful")

            }
        }

        override fun onFailure(call: Call<List<Product>>, t: Throwable) {
            Log.d("GET_PRODUCTS_FROM_DB", t.message.toString())
        }

    })
}



/*
    ADMIN FUNCTIONS
 */

fun postProduct(product: Product) {
    val service = RetrofitService.create()
    val call = service.postProductCall(product)
    call.enqueue(object : Callback<Product> {
        override fun onResponse(call: Call<Product>, response: Response<Product>) {
            Log.d("POST_PRODUCT", "Product post successful")
        }

        override fun onFailure(call: Call<Product>, t: Throwable) {
            Log.d("POST_PRODUCT", t.message.toString())
        }

    })
}

fun deleteProductById(id: Int) {
    val service = RetrofitService.create()
    val call = service.deleteProductByIdCall(id)
    call.enqueue(object : Callback<Product> {
        override fun onResponse(call: Call<Product>, response: Response<Product>) {
            Log.d("DELETE_PRODUCT", "success")
        }

        override fun onFailure(call: Call<Product>, t: Throwable) {
            Log.d("DELETE_PRODUCT", t.message.toString())
        }

    })
}

fun updateProduct(product: Product) {
    val service = RetrofitService.create()
    val call = service.updateProductCall(product)
    call.enqueue(object : Callback<Product> {
        override fun onResponse(call: Call<Product>, response: Response<Product>) {
            Log.d("UPDATE_PRODUCT", "success")
        }

        override fun onFailure(call: Call<Product>, t: Throwable) {
            Log.d("UPDATE_PRODUCT", t.message.toString())
        }

    })
}

fun deleteAllProducts() {
    val service = RetrofitService.create()
    val call = service.deleteAllProductsCall()
    call.enqueue(object : Callback<Product> {
        override fun onResponse(call: Call<Product>, response: Response<Product>) {
            Log.d("DELETE_PRODUCTS_ALL", "success")
        }

        override fun onFailure(call: Call<Product>, t: Throwable) {
            Log.d("DELETE_PRODUCTS_ALL", t.message.toString())
        }

    })
}




