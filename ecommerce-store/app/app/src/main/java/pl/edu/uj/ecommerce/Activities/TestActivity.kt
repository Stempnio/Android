package pl.edu.uj.ecommerce.Activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import pl.edu.uj.ecommerce.Data.CustomerRealm
import pl.edu.uj.ecommerce.ProductRealm
import pl.edu.uj.ecommerce.R

class TestActivity : AppCompatActivity() {
    lateinit var txtView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        txtView = findViewById(R.id.txtViewTest)

        val products = mutableListOf<ProductRealm>()
        Realm.getDefaultInstance().executeTransactionAsync {
            realmTransaction ->
            products.addAll(realmTransaction
                .where(ProductRealm::class.java)
                .findAll())
        }


        Realm.getDefaultInstance().executeTransactionAsync {
            realmTransaction ->
            txtView.text = realmTransaction.where(ProductRealm::class.java)
                .findAll().toString()

        }



    }


//    fun getProducts() {
//        val service = RetrofitService.create()
//        val call = service.getProductsCall()
//        call.enqueue(object : Callback<List<ProductRetrofit>> {
//            override fun onResponse(
//                call: Call<List<ProductRetrofit>>,
//                response: Response<List<ProductRetrofit>>
//            ) {
//                if (response.code() == 200) {
//                    val productResponse = response.body()!!
//
//                    for(prod in productResponse) {
//                        val tmpProduct = ProductRealm().apply {
//                            this.id = prod.id
//                            this.description = prod.description
//                            this.name = prod.name
//                            this.price = prod.price
//                        }
//
//                        Realm.getDefaultInstance().executeTransactionAsync {
//                            it.insertOrUpdate(tmpProduct)
//                        }
//                    }
//
//                    var stringBuilder = ""
//                    for(prod in productResponse) {
//                        stringBuilder += prod.name
//                        stringBuilder += "\n"
//                    }
//                    txtView.text = stringBuilder
//                }
//            }
//
//            override fun onFailure(call: Call<List<ProductRetrofit>>, t: Throwable) {
//                txtView.text = "onFailure " + t.message
//            }
//
//        })
//    }

}