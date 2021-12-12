package pl.edu.uj.ecommerce

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
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

// TODO rename to Product
class ProductRetrofit {
   // @SerializedName("name")
    var name : String = ""
    //@SerializedName("price")
    var price : Int = -1
    //@SerializedName("description")
    var description : String = ""
    //@SerializedName("id")
    var id : Int = 0
}

fun getProductsIntoDB() {
    val service = RetrofitService.create()
    val call = service.getProductsCall()
    call.enqueue(object : Callback<List<ProductRetrofit>> {
        override fun onResponse(
            call: Call<List<ProductRetrofit>>,
            response: Response<List<ProductRetrofit>>
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

            }
        }

        override fun onFailure(call: Call<List<ProductRetrofit>>, t: Throwable) {
            //TODO onFailture get products
        }

    })
}

//fun logProducts() {
//    //TODO delete
//    val tmpProd = mutableListOf<ProductRealm>()
//    Realm.getDefaultInstance().executeTransactionAsync {
//            realmTransaction -> tmpProd.addAll(
//        realmTransaction.where(ProductRealm::class.java)
//            .findAll()
//    )
//        Log.d("LOAD_DATA", tmpProd.toString())
//    }
//}




data class Product(val productName : String, val productPrice : Double)

object Products {
    var products = ArrayList<Product>()

    init {
        sampleList()
    }

    fun sampleList() {
        products.add(Product("product 1", 19900.9))
        products.add(Product("product 2", 1234.0))
        products.add(Product("product 3", 10.99))
        products.add(Product("product 4", 200.0))
        products.add(Product("product 5", 192.99))
        products.add(Product("product 6", 20.0))
        products.add(Product("product 7", 100000.0))
    }

}

object Cart {
    var productsInCart = ArrayList<Product>()

    fun totalPrice() : Double {
        var price : Double = 0.0
        for(p : Product in productsInCart) {
            price += p.productPrice
        }

        return price
    }

}
