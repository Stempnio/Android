package pl.edu.uj.ecommerce.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pl.edu.uj.ecommerce.Fragments.ProductsFragment
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTmp.setOnClickListener {

            val productsFragment = ProductsFragment()

            if(binding.buttonTmp.text.equals("SHOW")) {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frContainerViewActivityMain, productsFragment)
                    setReorderingAllowed(true)
                    commit()
                }
                binding.buttonTmp.text = "HIDE"
            } else {
                val frag = supportFragmentManager.findFragmentById(R.id.frContainerViewActivityMain)
                if(frag != null) {
                    supportFragmentManager.beginTransaction().apply {
                        remove(frag)
                        setReorderingAllowed(true)
                        commit()
                    }
                }
                binding.buttonTmp.text = "SHOW"
            }
        }

//        setContentView(R.layout.activity_main)

//        val intent = Intent(this, TestActivity::class.java).apply {
////            putExtra("message",labelka.text)
//        }
//        startActivity(intent)

        //TODO retrofit
        //TODO realm

    }
}