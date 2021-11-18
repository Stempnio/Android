package pl.edu.uj.zadanie_3.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import pl.edu.uj.zadanie_3.R

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val emailEditText: EditText = findViewById(R.id.editTextTextEmail)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)

        val buttonLogIn: Button = findViewById(R.id.buttonLogIn)
        buttonLogIn.setOnClickListener {
            val intent = Intent(this, ProductsActivity::class.java)
            startActivity(intent)
        }

        val buttonSignUp: Button = findViewById(R.id.buttonSignUp)

        buttonSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}