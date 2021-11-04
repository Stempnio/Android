package pl.edu.uj.zadanie_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val buttonGoBack: Button = findViewById(R.id.buttonGoBack3)
        buttonGoBack.setOnClickListener {
            finish()
        }
    }
}