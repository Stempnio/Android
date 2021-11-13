package pl.edu.uj.zadanie_3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CartActivity : AppCompatActivity() {

    private val adapter = ProductCartAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val buttonGoBack: Button = findViewById(R.id.buttonGoBack3)
        buttonGoBack.setOnClickListener {
            finish()
        }
    }



    override fun onResume() {
        super.onResume()

        val recyclerView: RecyclerView = findViewById(R.id.RecycleViewCartList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

//    fun displayCart() {
//        val txtViewCartList = findViewById<TextView>(R.id.textViewCartList)
//        val productIterator = Cart.productsInCart.iterator()
//        var listString : String = ""
//
//        while(productIterator.hasNext()) {
//            val product = productIterator.next()
//
//            listString += product.productName
//            listString += " | "
//            listString += product.productPrice
//            listString += "\n"
//        }
//
//        txtViewCartList.text = listString
//    }

}