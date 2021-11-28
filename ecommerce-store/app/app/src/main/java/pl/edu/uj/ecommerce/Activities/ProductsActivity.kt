package pl.edu.uj.ecommerce.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uj.ecommerce.Adapters.ProductListAdapter
import pl.edu.uj.ecommerce.R

class ProductsActivity : AppCompatActivity() {

    private val adapter = ProductListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val buttonGoToCart: Button = findViewById(R.id.buttonGoToCart)
        buttonGoToCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
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

    override fun onResume() {
        super.onResume()

        val recyclerView: RecyclerView = findViewById(R.id.RecycleViewProductList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}