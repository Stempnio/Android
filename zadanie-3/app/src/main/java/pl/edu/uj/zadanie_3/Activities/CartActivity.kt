package pl.edu.uj.zadanie_3.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import pl.edu.uj.zadanie_3.Adapters.ProductCartAdapter
import pl.edu.uj.zadanie_3.Cart
import pl.edu.uj.zadanie_3.R


class CartActivity : AppCompatActivity() {

    private val adapter = ProductCartAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val buttonGoBack: Button = findViewById(R.id.buttonGoBack3)
        buttonGoBack.setOnClickListener {
            finish()
        }

        val buttonBuy : Button = findViewById(R.id.buttonBuy)
        buttonBuy.setOnClickListener {
            if(Cart.productsInCart.size == 0) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, BuyActivity::class.java)
                startActivity(intent)
            }
        }

    }



    override fun onResume() {
        super.onResume()

        val recyclerView: RecyclerView = findViewById(R.id.RecycleViewCartList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val textViewTotalPrice : TextView = findViewById(R.id.textViewTotalPriceCart)
        textViewTotalPrice.text= Cart.totalPrice().toString()

        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                textViewTotalPrice.text = Cart.totalPrice().toString()
            }
        })

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