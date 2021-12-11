package pl.edu.uj.ecommerce.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.edu.uj.ecommerce.Fragments.ProductsFragment
import pl.edu.uj.ecommerce.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val productsFragment = ProductsFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frContainerViewActivityMain, productsFragment)
            setReorderingAllowed(true)
            commit()
        }




//        val intent = Intent(this, LogInActivity::class.java).apply {
////            putExtra("message",labelka.text)
//        }
//        startActivity(intent)
    }
}