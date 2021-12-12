package pl.edu.uj.ecommerce.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.edu.uj.ecommerce.Fragments.ProductsFragment
import pl.edu.uj.ecommerce.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, TestActivity::class.java).apply {
//            putExtra("message",labelka.text)
        }
        startActivity(intent)

        //TODO retrofit
        //TODO realm


        val productsFragment = ProductsFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frContainerViewActivityMain, productsFragment)
            setReorderingAllowed(true)
            commit()
        }
    }
}