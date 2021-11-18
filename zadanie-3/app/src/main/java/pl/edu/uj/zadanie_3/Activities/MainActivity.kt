package pl.edu.uj.zadanie_3.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.edu.uj.zadanie_3.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, LogInActivity::class.java).apply {
//            putExtra("message",labelka.text)
        }
        startActivity(intent)
    }
}