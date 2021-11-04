package pl.edu.uj.zadanie_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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