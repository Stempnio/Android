package pl.edu.uj.ecommerce

import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.where
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
   // @SerializedName("name")
    var name : String = ""
    //@SerializedName("price")
    var price : Int = -1
    //@SerializedName("description")
    var description : String = ""
    //@SerializedName("id")
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




