package pl.edu.uj.zadanie_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val buttonCartP1: Button = findViewById(R.id.buttonCartP1)
        buttonCartP1.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            Cart.addProduct("product 1", 1000.0)
            startActivity(intent)
        }

        val buttonCartP2: Button = findViewById(R.id.buttonCartP2)
        buttonCartP2.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            Cart.addProduct("product 2", 1110.0)
            startActivity(intent)
        }

        val buttonCartP3: Button = findViewById(R.id.buttonCartP3)
        buttonCartP3.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            Cart.addProduct("product 3", 55.0)
            startActivity(intent)
        }

        val buttonAboutApp: Button = findViewById(R.id.buttonAboutApp)
        buttonAboutApp.setOnClickListener {
            val intent = Intent(this, AboutAppActivity::class.java)
            startActivity(intent)
        }

        val buttonLogOut: Button = findViewById(R.id.buttonLogOut)
        buttonLogOut.setOnClickListener {
            finish()
        }
    }
}