package pl.edu.uj.ecommerce.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import pl.edu.uj.ecommerce.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val buttonSignUp2: Button = findViewById(R.id.buttonSignUp2)

        buttonSignUp2.setOnClickListener {
            //val returnIntent = Intent()
//            returnIntent.putExtra("result", "Bangla!")
//            setResult(RESULT_OK, returnIntent)
            finish()
        }

        val buttonGoBack: Button = findViewById(R.id.buttonGoBack1)
        buttonGoBack.setOnClickListener {
            finish()
        }
    }
}