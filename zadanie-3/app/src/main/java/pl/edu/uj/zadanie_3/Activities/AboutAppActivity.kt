package pl.edu.uj.zadanie_3.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import pl.edu.uj.zadanie_3.R

class AboutAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)

        val buttonGoBack: Button = findViewById(R.id.buttonGoBack2)
        buttonGoBack.setOnClickListener {
            finish()
        }
    }
}