package pl.edu.uj.zadanie_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CartActivity : AppCompatActivity() {

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

        displayCart()
    }

    fun displayCart() {
        val txtViewCartList = findViewById<TextView>(R.id.textViewCartList)
        val productIterator = Cart.productsInCart.iterator()
        var listString : String = ""

        while(productIterator.hasNext()) {
            val product = productIterator.next()

            listString += product.productName
            listString += " | "
            listString += product.productPrice
            listString += "\n"
        }

        txtViewCartList.text = listString
    }

}